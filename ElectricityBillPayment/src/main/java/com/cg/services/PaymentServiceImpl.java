package com.cg.services;

import com.cg.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.model.Payment;
import com.cg.model.PaymentStatus;
import com.cg.repository.PaymentRepository;

import java.util.List;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private BillRepository billRepository;


	@Override
	public PaymentStatus payBill(Payment payment) {
		return null;
	}

	@Override
	public void sendEmailOnPaymentCompletion(Long paymentId, String email) {

	}

	@Override
	public List<Payment> viewHistoricalPayment(Long userId) {
		return null;
	}
}
