package com.novabank.novabank.service;

import com.novabank.novabank.dto.CustomerDTO;
import com.novabank.novabank.entity.Customer;
import com.novabank.novabank.mapper.CustomerMapper;
import com.novabank.novabank.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAllCustomers_returnsMappedList() {
        Customer customer = new Customer(1L, "111", "Ana", "Gómez", 500000.0);
        CustomerDTO dto = new CustomerDTO(1L, "Ana", "Gómez", "111", 500000.0);

        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(dto);

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals("Ana", result.get(0).getFirstName());
    }

    @Test
    void createCustomer_savesAndReturnsDTO() {
        CustomerDTO dto = new CustomerDTO(null, "Carlos", "Pérez", "222", 150000.0);
        Customer entity = new Customer(null, "222", "Carlos", "Pérez", 150000.0);
        Customer saved = new Customer(2L, "222", "Carlos", "Pérez", 150000.0);
        CustomerDTO savedDto = new CustomerDTO(2L, "Carlos", "Pérez", "222", 150000.0);

        when(customerMapper.toEntity(dto)).thenReturn(entity);
        when(customerRepository.save(entity)).thenReturn(saved);
        when(customerMapper.toDTO(saved)).thenReturn(savedDto);

        CustomerDTO result = customerService.createCustomer(dto);

        assertEquals(2L, result.getId());
        verify(customerRepository, times(1)).save(entity);
    }

    @Test
    void getCustomerById_notFound_throwsException() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.getCustomerById(99L));
    }

    @Test
    void deleteCustomer_callsRepositoryDelete() {
        customerService.deleteCustomer(5L);
        verify(customerRepository, times(1)).deleteById(5L);
    }
}