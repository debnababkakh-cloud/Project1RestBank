package com.example.project1.repositories;

import com.example.project1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserById(Long id);

    boolean existsByUserName(String userName);

}
