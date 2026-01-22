package com.tecsup.financial_origination_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FinancialOriginationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialOriginationServiceApplication.class, args);
	}

}
