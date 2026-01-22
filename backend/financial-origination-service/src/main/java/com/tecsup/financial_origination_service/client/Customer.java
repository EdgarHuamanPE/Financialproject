package com.tecsup.financial_origination_service.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String documentType;
    private String documentNumber;
}
