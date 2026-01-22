package com.tecsup.financial_origination_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CustomerProduct {
    private Long id;
    private Long customerId;
    private Long productId;
    private String accountNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private BigDecimal balance;
    private String contractNumber;
    private String channelOrigin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
