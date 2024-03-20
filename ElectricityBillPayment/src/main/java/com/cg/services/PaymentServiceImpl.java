package com.cg.services;

import com.cg.exceptions.IdNotFoundException;
import com.cg.exceptions.PaymentNotFoundException;
import com.cg.exceptions.PaymentServiceException;
import com.cg.model.Bill;
import com.cg.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.model.Payment;
import com.cg.model.PaymentStatus;
import com.cg.repository.PaymentRepository;

import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private BillRepository billRepository;
	@Autowired
	private BillServiceImpl billService;
	@Enumerated
	private PaymentStatus paymentStatus;

	@Override
	public PaymentStatus payBill(Long billId) {
		try {
			// Check if the Bill with the given billId exists
			Optional<Bill> optionalBill = billService.getBillById(billId);
			if (optionalBill.isEmpty()) {
				System.out.println("Bill not found for billId: " + billId);
				return PaymentStatus.BILL_NOT_FOUND;
			}

			Bill bill = optionalBill.get();

			// Check if payment amount is positive
			if (bill.getBillAmount() <= 0) {
				System.out.println("Invalid payment amount: " + bill.getBillAmount());
				return PaymentStatus.INVALID_AMOUNT;
			}

			// For demonstration, we're assuming a dummy payment gateway with a fixed success rate
			boolean paymentSuccessful = paymentGateway();

			if (paymentSuccessful) {
				// Pass the actual total paid amount to createPayment method
				Payment payment = createPayment(bill, bill.getBillAmount());
				// Save the payment details
				paymentRepository.save(payment);
				updateBillAmountAfterPayment(billId);
				System.out.println("Payment successful!");
				return PaymentStatus.SUCCESS;
			} else {
				System.out.println("Payment failed!");
				return PaymentStatus.FAILURE;
			}
		} catch (Exception e) {
			System.out.println("Exception during payment: " + e.getMessage());
			// Handle exceptions specific to the payment processing
			return PaymentStatus.FAILURE;
		}
	}

	private Payment createPayment(Bill bill, double totalPaid) {
		// Create a new Payment object based on the provided bill
		Payment payment = new Payment();
		payment.setPaymentDate(LocalDate.now());
		payment.setTotalPaid(totalPaid); // Assuming total amount of the bill is paid
		payment.setPaymentStatus(PaymentStatus.SUCCESS); // Assuming payment is always successful in the dummy gateway
		payment.setBill(bill);

		return payment;
	}

	public void updateBillAmountAfterPayment(Long billId) {
		Optional<Bill> bill = billService.getBillById(billId);

		if (bill.isPresent()) {
			Bill bill1 = bill.get();

			// Fetch the associated payment for the given bill
			Payment payment = paymentRepository.getPaymentByBillId(billId);
			if (payment != null) {
				// Update only the bill amount
				double remainingAmount = bill1.getBillAmount() - payment.getTotalPaid();
				bill1.setBillAmount((int) remainingAmount);
				bill1.setPaid(true);
				billRepository.save(bill1);

			}
		}
	}
	private boolean paymentGateway() {
		// For demonstration purposes, simulate a successful payment with a fixed success rate
		//double successRate = 0.8;
		//return Math.random() < successRate;
		return true;
	}

	@Override
	public void sendEmailOnPaymentCompletion(Long paymentId, String email) {

	}

	@Override
	public List<Payment> viewHistoricalPayment(Long userId) {
		try{
			List<Payment> historicalPayments=paymentRepository.findPaymentByUserId(userId);
			if(historicalPayments.isEmpty()){
				throw new PaymentNotFoundException("Payment not found with user ID: "+userId);
			}
			else {
				return historicalPayments;
			}
		}catch (Exception e){
			throw  new PaymentServiceException("Error while fetching payment"+ e);
		}
	}
	public Payment getPaymentByBillId(Long billId){
		Payment p= paymentRepository.getPaymentByBillId(billId);
		if(p!=null){
			return p;
		}
		else {
			throw new IdNotFoundException("No payment associated with given bill id");
		}
	}
}
