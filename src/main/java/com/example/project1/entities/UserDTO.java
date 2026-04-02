package com.example.project1.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserDTO {

    private String userName;
    private int balance;

    public UserDTO(User user) {
        this.userName = user.getUsername();
        this.balance = user.getBalance();
    }

}
