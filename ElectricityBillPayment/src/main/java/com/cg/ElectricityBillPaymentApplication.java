package com.cg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cg")
public class ElectricityBillPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectricityBillPaymentApplication.class, args);
	}

}
