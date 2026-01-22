package com.tecsup.financial_origination_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerProductRequest {
    private Long customerId;
    private Long productId;
    private String accountNumber;
    private BigDecimal balance;
    private String contractNumber;
    private String channelOrigin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
