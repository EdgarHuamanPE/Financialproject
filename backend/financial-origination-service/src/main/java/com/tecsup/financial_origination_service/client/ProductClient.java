package com.tecsup.financial_origination_service.client;

import com.tecsup.financial_origination_service.exception.NotFoundProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;



    public Product getProductById(Long id) {
        String Url = productServiceUrl + "/api/products/" + id;

        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();

            String token = (String) auth.getCredentials();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Product> response =
                    restTemplate.exchange(
                            Url,
                            HttpMethod.GET,
                            entity,
                            Product.class
                    );

            Product product = response.getBody();
            log.info("Product retrieved successfully: {}", product);
            return product;

        } catch (Exception e) {
            //log.info("Error Calling  Custumer Service :{ }", e.getMessage());
            throw new NotFoundProductId(e.getMessage());
        }
    }

}
