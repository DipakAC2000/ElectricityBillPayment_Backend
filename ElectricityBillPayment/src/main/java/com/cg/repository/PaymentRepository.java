package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.model.Payment;

@Repository("paymentRepository")
public interface PaymentRepository extends JpaRepository<Payment, String>{

}
