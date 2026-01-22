package com.tecsup.financial_origination_service.client;


import com.tecsup.financial_origination_service.exception.NotFoundClientId;
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
public class CustomerClient {

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String customerServiceUrl;

    public Customer getCustomerByDocumentNumber(String documentNumber) {
        String Url = customerServiceUrl + "/api/customers/document/" + documentNumber;
        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();

            String token = (String) auth.getCredentials();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Customer> response =
                    restTemplate.exchange(
                            Url,
                            HttpMethod.GET,
                            entity,
                            Customer.class
                    );

            Customer customer = response.getBody();
            log.info("Customer retrieved successfully: {}", customer);
            return customer;
        } catch (Exception e) {
            log.info("Error Calling  Custumer Service :{ }", e.getMessage());
            throw new NotFoundClientId( e.getMessage());
        }
    }

    public Customer getCustomerById(Long id) {
        String Url = customerServiceUrl + "/api/customers/" + id;

        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();

            String token = (String) auth.getCredentials();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Customer> response =
                    restTemplate.exchange(
                            Url,
                            HttpMethod.GET,
                            entity,
                            Customer.class
                    );

            Customer customer = response.getBody();
            log.info("Customer retrieved successfully: {}", customer);
            return customer;


        } catch (Exception e) {
            log.info("Error Calling  Custumer Service :{ }", e.getMessage());
            throw new NotFoundClientId( e.getMessage());
        }
    }
}
