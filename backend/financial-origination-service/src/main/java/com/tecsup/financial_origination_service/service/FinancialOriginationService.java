package com.tecsup.financial_origination_service.service;


import ch.qos.logback.classic.spi.IThrowableProxy;
import com.tecsup.financial_origination_service.dto.CustomerProduct;
import com.tecsup.financial_origination_service.entity.CustomerProductEntity;
import com.tecsup.financial_origination_service.exception.DuplicateAccountNumber;
import com.tecsup.financial_origination_service.exception.NotFoundCustomerProductAccountNumber;
import com.tecsup.financial_origination_service.exception.NotFoundCustomerProductId;
import com.tecsup.financial_origination_service.exception.NotFoundProductId;
import com.tecsup.financial_origination_service.mapper.CostumerProductMapper;
import com.tecsup.financial_origination_service.repository.FinancialOriginationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class FinancialOriginationService {

    private final FinancialOriginationRepository financialOriginationRepository;
    private final CostumerProductMapper costumerProductMapper;

    @Transactional(readOnly = true)
    public List<CustomerProduct> getCustomerProductById(Long id) {
        List<CustomerProduct> customerProduct =
                financialOriginationRepository.findByCustomerId(id)
                        .stream()
                        .map(this.costumerProductMapper::toDomain)
                        .collect(Collectors.toList());
        if(customerProduct.isEmpty()){
            throw new NotFoundCustomerProductId(id);
        }
        return customerProduct;
    }

    @Transactional(readOnly = true)
    public CustomerProduct getCustomerProductByAccountNumber(String accountNumber) {
        return financialOriginationRepository.findByAccountNumber(accountNumber)
                        .map(costumerProductMapper::toDomain)
                        .orElseThrow(
                                ()->new NotFoundCustomerProductAccountNumber(accountNumber));
    }

    @Transactional(readOnly = true)
    public List<CustomerProduct> getAllCustomerProduct() {
        return  financialOriginationRepository.findAll()
                .stream()
                .map(this.costumerProductMapper::toDomain)
                .collect(Collectors.toList());
    }


    public CustomerProduct saveCustomerProduct(CustomerProduct customerProduct) {
        CustomerProductEntity customerProductEntity = costumerProductMapper.toEntity(customerProduct);
        if (financialOriginationRepository.existsByAccountNumber(customerProductEntity.getAccountNumber())) {
            throw new DuplicateAccountNumber(customerProductEntity.getAccountNumber());
        }
        return costumerProductMapper.toDomain(financialOriginationRepository.save(customerProductEntity));
    }

    public CustomerProduct updateCustomerProduct(CustomerProduct customerProduct) {

        if (!financialOriginationRepository.existsByAccountNumber(customerProduct.getAccountNumber())) {
            throw new NotFoundCustomerProductAccountNumber(customerProduct.getAccountNumber());
        }
        CustomerProductEntity customerProductEntity=costumerProductMapper.toEntity(customerProduct);
        return costumerProductMapper.toDomain(financialOriginationRepository.save(customerProductEntity));
    }

    public void deleteCustomerProduct(CustomerProduct customerProduct) {
        financialOriginationRepository.delete(costumerProductMapper.toEntity(customerProduct));
    }
}
