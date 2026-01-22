package com.tecsup.financial_origination_service.repository;

import com.tecsup.financial_origination_service.entity.CustomerProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinancialOriginationRepository  extends JpaRepository<CustomerProductEntity,Long> {
    List<CustomerProductEntity> findByCustomerId(Long customerId);
    Optional<CustomerProductEntity> findByAccountNumber(String accountNumber);
    boolean existsByAccountNumber(String accountNumber);
}
