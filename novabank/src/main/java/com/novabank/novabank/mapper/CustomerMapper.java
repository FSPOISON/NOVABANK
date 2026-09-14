package com.novabank.novabank.mapper;

import com.novabank.novabank.dto.CustomerDTO;
import com.novabank.novabank.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDTO toDTO(Customer c) {
        // Orden DTO: id, firstName, lastName, accountNumber, balance
        return new CustomerDTO(c.getId(), c.getFirstName(), c.getLastName(), c.getAccountNumber(), c.getBalance());
    }

    public Customer toEntity(CustomerDTO dto) {
        // Orden Entidad: id, accountNumber, firstName, lastName, balance
        return new Customer(dto.getId(), dto.getAccountNumber(), dto.getFirstName(), dto.getLastName(), dto.getBalance());
    }
}