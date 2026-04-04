package com.example.project1.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferDTO {

    private int amount;
    private String userFrom;
    private String userTo;

    public TransferDTO(Transfer transfer) {
        this.amount = transfer.getAmount();
        this.userFrom = transfer.getUserFrom().getUsername();
        this.userTo = transfer.getUserTo().getUsername();
    }

}
