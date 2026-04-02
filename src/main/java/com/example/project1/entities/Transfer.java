package com.example.project1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_transfers")
@Getter
@Setter
@NoArgsConstructor

public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "user_to")
    private User userTo;

    @ManyToOne
    @JoinColumn(name = "user_from")
    private User userFrom;

    public Transfer(User userFrom, User userTo, int amount) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }
}
