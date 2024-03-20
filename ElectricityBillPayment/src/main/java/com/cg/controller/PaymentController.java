package com.cg.controller;

import com.cg.exceptions.BillNotFoundException;
import com.cg.exceptions.ConsumerNumberNotFoundException;
import com.cg.exceptions.PaymentNotFoundException;
import com.cg.exceptions.PaymentServiceException;
import com.cg.model.Bill;
import com.cg.model.Payment;
import com.cg.model.PaymentStatus;
import com.cg.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.services.PaymentServiceImpl;

import java.util.List;

@RestController("paymentController")
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private PaymentServiceImpl paymentService;
	@Autowired
	private PaymentRepository paymentRepository;
	@GetMapping("/payBill/{billId}")
	public ResponseEntity<PaymentStatus> payBill(@PathVariable Long billId){
		PaymentStatus paymentStatus = paymentService.payBill(billId);

		if (paymentStatus == PaymentStatus.SUCCESS) {
			return new ResponseEntity<>(paymentStatus, HttpStatus.OK);
		} else if (paymentStatus == PaymentStatus.BILL_NOT_FOUND) {
			return new ResponseEntity<>(paymentStatus, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(paymentStatus, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/historical/{userId}")
	public ResponseEntity<List<Payment>> viewHistoricalPayments(@PathVariable Long userId) {
		try {
			List<Payment> historicalPayments = paymentService.viewHistoricalPayment(userId);
			return new ResponseEntity<>(historicalPayments, HttpStatus.OK);
		} catch (PaymentNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (PaymentServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
