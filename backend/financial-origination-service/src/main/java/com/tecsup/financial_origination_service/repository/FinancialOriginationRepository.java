package com.tecsup.financial_origination_service.repository;

import com.tecsup.financial_origination_service.entity.CustomerProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FinancialOriginationRepository  extends JpaRepository<CustomerProductEntity,Long> {
    List<CustomerProductEntity> findByCustomerId(Long customerId);
    Optional<CustomerProductEntity> findByAccountNumber(String accountNumber);
    boolean existsByAccountNumber(String accountNumber);

    @Query("SELECT COUNT(cp) FROM CustomerProductEntity cp WHERE cp.startDate >= :start AND cp.startDate <= :end")
    int countProductsInMonth(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
