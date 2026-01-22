package com.tecsup.product_service.controller;

import com.tecsup.product_service.dto.*;
import com.tecsup.product_service.exception.DuplicateCodigoException;
import com.tecsup.product_service.exception.NotFoundProduct;
import com.tecsup.product_service.factory.ResponseFactory;
import com.tecsup.product_service.mapper.ProductMapper;
import com.tecsup.product_service.mapper.UpdateProductMapper;
import com.tecsup.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor

@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final UpdateProductMapper updateProductMapper;
    private final ResponseFactory responseFactory;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MONITOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        if (id == null || id.isBlank() || !id.matches("\\d+")) { // Solo números
            MsgResponse badIdInput = responseFactory.badIdProduct(id);
            return ResponseEntity
                    .badRequest()
                    .body(badIdInput);
        }
        try{
            Long idReal = Long.parseLong(id);
            Product product = productService.getProductById(idReal);
            return ResponseEntity.ok(productMapper.toResponse(product));
        }catch (NotFoundProduct e){
            MsgResponse response = responseFactory.notFoundProduct(id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MONITOR')")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok( productService.getAllProducts().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        Product savedProduct=null;
        try{
            if (productRequest == null
                    || productRequest.getCode() == null
                    || productRequest.getName() == null
                    || productRequest.getType() == null
                    || productRequest.getCategory() == null
                    || productRequest.getCurrency() == null
                    || productRequest. getInterestRate() == null
                    || productRequest.getDescription() == null
                    )
            {
                MsgResponse msg =responseFactory.missingFieldRequerid();
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(msg);
            }
            Product newProduct=productMapper.toDomain(productRequest);
            savedProduct = productService.saveProduct(newProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toResponse(savedProduct));
        }catch (DuplicateCodigoException e){
            MsgResponse msg =responseFactory.codigoExistingResponse(productRequest.getCode());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String id,
            @RequestBody UpdateProductRequest updateProductRequest) {

        try{
            if (id == null || id.isBlank() || !id.matches("\\d+")) {
                MsgResponse badResponse = responseFactory.badIdProduct(id);
                return ResponseEntity
                        .badRequest()
                        .body(badResponse);
            }
            Long idReal = Long.parseLong(id);
            Product existingCustomer = productService.getProductById(idReal);
            updateProductMapper.updateEntityFromRequest(updateProductRequest, existingCustomer);
            Product update =productService.updateProduct(existingCustomer);
            return ResponseEntity.ok(productMapper.toResponse(update));
        }catch (NotFoundProduct e){
            MsgResponse response = responseFactory.notFoundProduct(String.valueOf(id));
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MsgResponse> deleteCustomer(@PathVariable String id) {

        if (id == null || id.isBlank() || !id.matches("\\d+")) { // Solo números
            MsgResponse responsebadId = responseFactory.badIdProduct(id);
            return ResponseEntity
                    .badRequest()
                    .body(responsebadId);
        }

        try {
            Long idReal = Long.parseLong(id);
            Product existingProduct = productService.getProductById(idReal);
            productService.deleteProduct(existingProduct);
            MsgResponse response = responseFactory.createDeleteResponse("Product");
            return ResponseEntity.ok(response);
        } catch (NotFoundProduct e) {
            MsgResponse response = responseFactory.notFoundProduct(String.valueOf(id));
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }
    }
}
