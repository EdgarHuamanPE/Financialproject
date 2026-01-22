package com.dominio.customer_service.controller;

import com.dominio.customer_service.dto.*;
import com.dominio.customer_service.entity.CustomerEntity;
import com.dominio.customer_service.exception.DuplicateDniException;
import com.dominio.customer_service.exception.NotFoundCustomer;
import com.dominio.customer_service.factory.ResponseFactory;
import com.dominio.customer_service.mapper.CustomerMapper;
import com.dominio.customer_service.mapper.UpdateCustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.dominio.customer_service.service.CustomerService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor

public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final UpdateCustomerMapper updateCustomerMapper;
    private final ResponseFactory responseFactory;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MONITOR')")
    @GetMapping({"/document/{ducumentNumber}","/document"})
    public ResponseEntity<?> getCustomerByDocumentNumber(
            @PathVariable(required = false) String  ducumentNumber) {
        try{
            if (ducumentNumber == null || ducumentNumber.isBlank()) {
                return ResponseEntity.badRequest().build();
            }
            Customer customer = customerService.getCustomerByDocumentNumber(ducumentNumber);
            return ResponseEntity.ok(customerMapper.toResponse(customer));
        }catch (NotFoundCustomer e){
            MsgResponse response = responseFactory.notFoundCustomerByDocNumber(ducumentNumber);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MONITOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        if (id == null || id.isBlank() || !id.matches("\\d+")) { // Solo números
            MsgResponse responsebadIdInput = responseFactory.badIdCostumer(id);
            return ResponseEntity
                    .badRequest()
                    .body(responsebadIdInput);
        }
        try{
            Long idReal = Long.parseLong(id);
            Customer customer = customerService.getCustomerById(idReal);
            return ResponseEntity.ok(customerMapper.toResponse(customer));
        }catch (NotFoundCustomer e){
            MsgResponse response = responseFactory.notFoundCustomerById(id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MONITOR')")
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok( customerService.getAllCustomers().stream()
                .map(this.customerMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCustomer( @RequestBody CustomerRequest createCustomerRequest) {
        Customer savedEntity=null;
        try {
            if (createCustomerRequest == null
                    || createCustomerRequest.getFirstName() == null
                    || createCustomerRequest.getLastName() == null
                    || createCustomerRequest.getDocumentNumber() == null
                    || createCustomerRequest.getDocumentType() == null)
                {
                     MsgResponse msg =responseFactory.missingFieldRequerid();
                        return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(msg);
                }
            Customer newCustomer=customerMapper.toDomain(createCustomerRequest);
            savedEntity = customerService.saveCustomer(newCustomer);
        }catch (DuplicateDniException exception){
            MsgResponse msg =responseFactory.dniExistingResponse(createCustomerRequest.getDocumentNumber());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error make the customer");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.toResponse(savedEntity));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable String id,
            @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        try{
            if (id == null || id.isBlank() || !id.matches("\\d+")) { // Solo números
                MsgResponse responsebadIdInput = responseFactory.badIdCostumer(id);
                return ResponseEntity
                        .badRequest()
                        .body(responsebadIdInput);
            }
            Long idReal = Long.parseLong(id);
            Customer existingCustomer = customerService.getCustomerById(idReal);
            updateCustomerMapper.updateEntityFromRequest(updateCustomerRequest, existingCustomer);
            Customer update =customerService.updateCustomer(existingCustomer);
            return ResponseEntity.ok(customerMapper.toResponse(update));
        }catch (NotFoundCustomer e){
            MsgResponse response = responseFactory.notFoundCustomerById(String.valueOf(id));
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MsgResponse> deleteCustomer(@PathVariable String id) {
        if (id == null || id.isBlank() || !id.matches("\\d+")) { // Solo números
            MsgResponse responsebadIdInput = responseFactory.badIdCostumer(id);
            return ResponseEntity
                    .badRequest()
                    .body(responsebadIdInput);
        }
        try{
            Long idReal = Long.parseLong(id);
            Customer existingCustomer = customerService.getCustomerById(idReal);
            customerService.deleteCustomer(existingCustomer);
            MsgResponse response = responseFactory.createDeleteResponse("Customer");
            return ResponseEntity.ok(response);
        }catch (NotFoundCustomer e){
            MsgResponse response = responseFactory.notFoundCustomerById(String.valueOf(id));
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MONITOR')")
    @GetMapping("/dashboard/customer/count")
    public Map<String, Long> countActiveCustomers() {
        long total = customerService.countActiveCustomers();
        return Map.of("count", total);
    }
}
