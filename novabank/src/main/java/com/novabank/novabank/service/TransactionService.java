package com.novabank.novabank.service;

import com.novabank.novabank.dto.TransactionDTO;
import com.novabank.novabank.entity.Customer;
import com.novabank.novabank.entity.Transaction;
import com.novabank.novabank.mapper.TransactionMapper;
import com.novabank.novabank.repository.CustomerRepository;
import com.novabank.novabank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private TransactionMapper transactionMapper;

    public TransactionDTO transferMoney(TransactionDTO dto) {
        if (dto.getSenderAccountNumber() == null || dto.getReceiverAccountNumber() == null) {
            throw new IllegalArgumentException("Las cuentas no pueden ser nulas");
        }
        Customer sender = customerRepository.findByAccountNumber(dto.getSenderAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));
        Customer receiver = customerRepository.findByAccountNumber(dto.getReceiverAccountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta destino no encontrada"));

        if (sender.getBalance() < dto.getAmount()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        sender.setBalance(sender.getBalance() - dto.getAmount());
        receiver.setBalance(receiver.getBalance() + dto.getAmount());
        customerRepository.save(sender);
        customerRepository.save(receiver);

        Transaction t = new Transaction();
        t.setSenderAccountNumber(sender.getAccountNumber());
        t.setReceiverAccountNumber(receiver.getAccountNumber());
        t.setAmount(dto.getAmount());
        t.setTimestamp(LocalDateTime.now());
        t = transactionRepository.save(t);

        return transactionMapper.toDTO(t);
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream().map(transactionMapper::toDTO).toList();
    }

    public List<TransactionDTO> getTransactionsForAccount(String accountNumber) {
        return transactionRepository
                .findBySenderAccountNumberOrReceiverAccountNumber(accountNumber, accountNumber)
                .stream().map(transactionMapper::toDTO).toList();
    }
}