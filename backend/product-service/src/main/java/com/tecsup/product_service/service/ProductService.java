package com.tecsup.product_service.service;


import com.tecsup.product_service.dto.Product;
import com.tecsup.product_service.entity.ProductEntity;
import com.tecsup.product_service.exception.DuplicateCodigoException;
import com.tecsup.product_service.exception.NotFoundProduct;
import com.tecsup.product_service.mapper.ProductMapper;
import com.tecsup.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return  productRepository.findById(id)
                .map(productMapper::toDomain)
                .orElseThrow(()->new NotFoundProduct(id));
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return  productRepository.findAll()
                .stream()
                .map(this.productMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Product saveProduct(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        if (productRepository.existsByCode(productEntity.getCode())) {
            throw new DuplicateCodigoException(productEntity.getCode());
        }
        return productMapper.toDomain(productRepository.save(productEntity));
    }

    public Product updateProduct(Product product) {
        if (!productRepository.existsByCode(product.getCode())) {
            throw new DuplicateCodigoException(product.getCode());
        }
        ProductEntity productEntity=productMapper.toEntity(product);
        return productMapper.toDomain(productRepository.save(productEntity));
    }

    public void deleteProduct(Product product) {
        productRepository.delete(productMapper.toEntity(product));
    }
}
