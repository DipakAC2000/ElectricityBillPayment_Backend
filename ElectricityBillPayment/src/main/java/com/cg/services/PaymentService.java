package com.cg.services;

import java.util.List;

import com.cg.model.Payment;
import com.cg.model.PaymentStatus;

public interface PaymentService {
	
	public PaymentStatus payBill(Payment payment);
	
	public void sendEmailOnPaymentCompletion(Long paymentId ,String email);
	
	public List<Payment> viewHistoricalPayment(Long userId);  
}
