package com.dominio.customer_service.repository;

import com.dominio.customer_service.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    boolean existsByDocumentNumber(String documentNumber);
    Optional<CustomerEntity> findByDocumentNumber(String documentNumber);

}
