package com.example.project1.services;

import com.example.project1.entities.Transfer;
import com.example.project1.repositories.TransferRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private final TransferRepository transferRepository;

    public TransferService (TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public void addTransfer(Transfer transfer) {
        transferRepository.save(transfer);
    }

}
