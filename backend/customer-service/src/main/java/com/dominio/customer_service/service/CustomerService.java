package com.dominio.customer_service.service;


import com.dominio.customer_service.dto.Customer;
import com.dominio.customer_service.entity.CustomerEntity;
import com.dominio.customer_service.exception.DuplicateDniException;
import com.dominio.customer_service.exception.NotFoundCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.dominio.customer_service.mapper.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dominio.customer_service.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Transactional(readOnly = true)
    public Customer getCustomerByDocumentNumber(String documentNumber){
        return  customerRepository.findByDocumentNumber(documentNumber)
                .map(this.customerMapper::toDomain)
                .orElseThrow(()->new NotFoundCustomer(documentNumber));
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return  customerRepository.findById(id)
                .map(this.customerMapper::toDomain)
                .orElseThrow(()->new NotFoundCustomer(id));
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
       return  customerRepository.findAll()
                .stream()
                .map(this.customerMapper::toDomain)
                .collect(Collectors.toList());
    }


    public Customer saveCustomer(Customer customer) {

        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        if (customerRepository.existsByDocumentNumber(customerEntity.getDocumentNumber())) {
            throw new DuplicateDniException(customerEntity.getDocumentNumber());
        }

        return customerMapper.toDomain(customerRepository.save(customerEntity));
    }

    public Customer updateCustomer(Customer customer) {
        if (!customerRepository.existsByDocumentNumber(customer.getDocumentNumber())) {
            throw new DuplicateDniException(customer.getDocumentNumber());
        }
        CustomerEntity customerEntity=customerMapper.toEntity(customer);
        return customerMapper.toDomain(customerRepository.save(customerEntity));
    }


    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customerMapper.toEntity(customer));
    }
}
