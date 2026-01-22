package com.tecsup.product_service.repository;

import com.tecsup.product_service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    boolean existsByCode(String code);

    @Query("SELECT COUNT(c) FROM ProductEntity c WHERE c.status = 'ACTIVO'")
    long countActiveProducts();
}
