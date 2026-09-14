package com.novabank.novabank.service;

import com.novabank.novabank.dto.CustomerDTO;
import com.novabank.novabank.entity.Customer;
import com.novabank.novabank.mapper.CustomerMapper;
import com.novabank.novabank.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::toDTO).toList();
    }

    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id).map(customerMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public CustomerDTO createCustomer(CustomerDTO dto) {
        Customer c = customerMapper.toEntity(dto);
        return customerMapper.toDTO(customerRepository.save(c));
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        c.setFirstName(dto.getFirstName());
        c.setLastName(dto.getLastName());
        c.setAccountNumber(dto.getAccountNumber());
        c.setBalance(dto.getBalance());
        return customerMapper.toDTO(customerRepository.save(c));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}