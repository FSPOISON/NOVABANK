package com.novabank.novabank.service;

import com.novabank.novabank.dto.TransactionDTO;
import com.novabank.novabank.entity.Customer;
import com.novabank.novabank.entity.Transaction;
import com.novabank.novabank.mapper.TransactionMapper;
import com.novabank.novabank.repository.CustomerRepository;
import com.novabank.novabank.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock private TransactionRepository transactionRepository;
    @Mock private CustomerRepository customerRepository;
    @Mock private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void transferMoney_sufficientBalance_updatesBothAccounts() {
        Customer sender = new Customer(1L, "111", "Ana", "Gómez", 500000.0);
        Customer receiver = new Customer(2L, "222", "Carlos", "Pérez", 150000.0);
        TransactionDTO request = new TransactionDTO(null, "111", "222", 50000.0, null);

        when(customerRepository.findByAccountNumber("111")).thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumber("222")).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transactionMapper.toDTO(any(Transaction.class))).thenReturn(request);

        transactionService.transferMoney(request);

        assertEquals(450000.0, sender.getBalance());
        assertEquals(200000.0, receiver.getBalance());
        verify(customerRepository).save(sender);
        verify(customerRepository).save(receiver);
    }

    @Test
    void transferMoney_insufficientBalance_throwsException() {
        Customer sender = new Customer(1L, "111", "Ana", "Gómez", 10000.0);
        Customer receiver = new Customer(2L, "222", "Carlos", "Pérez", 150000.0);
        TransactionDTO request = new TransactionDTO(null, "111", "222", 50000.0, null);

        when(customerRepository.findByAccountNumber("111")).thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumber("222")).thenReturn(Optional.of(receiver));

        assertThrows(IllegalArgumentException.class, () -> transactionService.transferMoney(request));
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void transferMoney_senderAccountNotFound_throwsException() {
        TransactionDTO request = new TransactionDTO(null, "999", "222", 50000.0, null);
        when(customerRepository.findByAccountNumber("999")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transactionService.transferMoney(request));
    }
}