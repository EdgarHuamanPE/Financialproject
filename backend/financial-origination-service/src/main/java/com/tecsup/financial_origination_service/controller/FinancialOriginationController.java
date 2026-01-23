package com.tecsup.financial_origination_service.controller;

import com.tecsup.financial_origination_service.client.Customer;
import com.tecsup.financial_origination_service.client.CustomerClient;
import com.tecsup.financial_origination_service.client.Product;
import com.tecsup.financial_origination_service.client.ProductClient;
import com.tecsup.financial_origination_service.dto.*;
import com.tecsup.financial_origination_service.exception.*;
import com.tecsup.financial_origination_service.mapper.CostumerProductMapper;
import com.tecsup.financial_origination_service.mapper.UpdateCustomerProductMapper;
import com.tecsup.financial_origination_service.service.FinancialOriginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class FinancialOriginationController {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final FinancialOriginationService financialOriginationService;
    private final CostumerProductMapper costumerProductMapper;
    private final UpdateCustomerProductMapper updateCustomerProductMapper;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MONITOR')")
    @GetMapping("/{documentNumber}/products")
    public ResponseEntity<?> getCustomerProductByDocument(@PathVariable String documentNumber) {

        try{
            Customer customer =customerClient.getCustomerByDocumentNumber(documentNumber);
            List<CustomerProduct> customerProducts = financialOriginationService
                    .getCustomerProductById(customer.getId());

            List<ProductItemResponse> productItemResponse =
                    customerProducts.stream()
                            .map(customerProduct -> {
                                Product product =
                                        productClient
                                                .getProductById(customerProduct.getProductId());
                                return ProductItemResponse.builder()
                                        .code(product.getCode())
                                        .type(product.getType())
                                        .name(product.getName())
                                        .balance(customerProduct.getBalance())
                                        .build();
                            })
                            .collect(Collectors.toList());

            CustomerProductByDniResponse customerProductByDniResponse = CustomerProductByDniResponse.builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .documentType(customer.getDocumentType())
                    .documentNumber(customer.getDocumentNumber())
                    .products(productItemResponse)
                    .build();
            return ResponseEntity.ok(customerProductByDniResponse);

        }catch (NotFoundClientId e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        }catch (NotFoundCustomerProductId e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        }catch (NotFoundProductId e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCustomerProduct(@RequestBody CustomerProductRequest customerProductRequest) {
        CustomerProduct savedCustomerProduct=null;
        try{
            if (customerProductRequest == null
                    || customerProductRequest.getCustomerId() == null
                    || customerProductRequest.getProductId() == null
                    || customerProductRequest.getAccountNumber() == null
                    || customerProductRequest.getBalance() == null
                    || customerProductRequest.getContractNumber() == null
                    || customerProductRequest.getChannelOrigin() == null
            )
            {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Customerproductrequest empty");
            }

            Customer customer =customerClient.getCustomerById(customerProductRequest.getCustomerId());
            Product product = productClient.getProductById(customerProductRequest.getProductId());

            CustomerProduct newCustomerProduct= costumerProductMapper.toDomain(customerProductRequest);
            savedCustomerProduct = financialOriginationService.saveCustomerProduct(newCustomerProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(costumerProductMapper.toResponse(savedCustomerProduct));
        }catch (DuplicateAccountNumber e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (NotFoundClientId e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (NotFoundProductId e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateCustomerProduct(
            @PathVariable String accountNumber,
            @RequestBody UpdateCustomerProductRequest updateCustomerProductRequest) {

        try{
            if (accountNumber == null || accountNumber.isBlank()) {
                return ResponseEntity
                        .badRequest()
                        .body("accountNumber is bad");
            }
            CustomerProduct existingCustomerProduct = financialOriginationService.getCustomerProductByAccountNumber(accountNumber);
            updateCustomerProductMapper.updateEntityFromRequest(updateCustomerProductRequest, existingCustomerProduct);
            CustomerProduct update =financialOriginationService.updateCustomerProduct(existingCustomerProduct);
            return ResponseEntity.ok(costumerProductMapper.toResponse(update));
        }catch (NotFoundCustomerProductAccountNumber e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> deleteCustomerProduct(@PathVariable String accountNumber) {

        if (accountNumber == null || accountNumber.isBlank() ) { // Solo números
            return ResponseEntity
                    .badRequest()
                    .body("accountNumber is bad");
        }

        try {
            CustomerProduct existingCustomerProduct = financialOriginationService.getCustomerProductByAccountNumber(accountNumber);
            financialOriginationService.deleteCustomerProduct(existingCustomerProduct);
            return ResponseEntity.ok("CustomerProduct Deleted with accountNumber: "+accountNumber);
        } catch (NotFoundCustomerProductAccountNumber e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Not found CustomerProductByAccountNumber");
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MONITOR')")
    @GetMapping
    public ResponseEntity<Page<CustomerProductResponse>> getAllCustomerProduct(Pageable pageable) {
        Page<CustomerProductResponse> response = financialOriginationService.getAllCustomerProduct(pageable)
                .map(costumerProductMapper::toResponse);
        return ResponseEntity.ok( response);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MONITOR')")
    @GetMapping("/dashboard/mes/activacion")
    public Map<String, Integer> getLastMonthProducts() {
        int total = financialOriginationService.getLastMonthTotalProducts();
        return Map.of("count", total);
    }
}
