package com.example.project1.controller;

import com.example.project1.entities.Transfer;
import com.example.project1.entities.User;
import com.example.project1.entities.UserDTO;
import com.example.project1.repositories.UserRepository;
import com.example.project1.services.TransferService;
import com.example.project1.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {

    private final UserService userService;
    private final TransferService transferService;


    public MainController(UserService userService, TransferService transferService) {
        this.transferService = transferService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String userName, @RequestParam String password){
        userService.register(userName, password);
        return "ok";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String password) {
        return userService.login(userName, password);
    }

    @PostMapping("/hello")
    public UserDTO hello(@RequestParam Long idTo, @RequestParam int amount) {
        User user = userService.getCurrentUser();
        userService.transferMoney(user.getId(), idTo, amount);
        return new UserDTO(userService.findUserById(user.getId()));
    }

    @GetMapping("/test")
    public UserDTO test() {
        return new UserDTO(userService.getCurrentUser());
    }
}
