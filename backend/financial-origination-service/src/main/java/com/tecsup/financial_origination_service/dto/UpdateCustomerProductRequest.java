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
public class UpdateCustomerProductRequest {
    private BigDecimal balance;
    private String channelOrigin;
    private String status;


}
