package com.example.project1.services;

import com.example.project1.entities.Transfer;
import com.example.project1.entities.User;
import com.example.project1.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TransferService transferService;
    private final PasswordEncoder passwordEncoder;
    private final Key jwtSigningKey;

    public UserService(UserRepository userRepository, TransferService transferService, PasswordEncoder passwordEncoder, Key jwtSigningKey) {
        this.userRepository = userRepository;
        this.transferService = transferService;
        this.passwordEncoder = passwordEncoder;
        this.jwtSigningKey = jwtSigningKey;
    }

    public void register(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setBalance(0);
        userRepository.save(user);
    }

    public String login(String userName, String password) {
        User user = this.findUserByUserName(userName);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(jwtSigningKey)   // вот сюда Key
                .compact();

        return token;
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof String) {
            return (String) auth.getPrincipal();
        }
        return null;
    }

    public User getCurrentUser() {
        String username = getCurrentUsername();
        return findUserByUserName(username);
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }

    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @Transactional
    public void transferMoney(Long fromId, Long toId, int amount) {

        User userFrom = this.findUserById(fromId);
        User userTo = this.findUserById(toId);

        userFrom.setBalance(userFrom.getBalance() - amount);
        userTo.setBalance(userTo.getBalance() + amount);

        transferService.addTransfer(new Transfer(userFrom, userTo, amount));

    }

}
