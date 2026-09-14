package com.novabank.novabank.mapper;

import com.novabank.novabank.dto.TransactionDTO;
import com.novabank.novabank.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public TransactionDTO toDTO(Transaction t) {
        return new TransactionDTO(t.getId(), t.getSenderAccountNumber(), t.getReceiverAccountNumber(), t.getAmount(), t.getTimestamp());
    }
}