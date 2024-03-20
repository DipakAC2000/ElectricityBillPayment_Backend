package com.cg.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;

	@DateTimeFormat(pattern = "yyy-MM-dd")
	private LocalDate paymentDate;

//	private double latePaymentCharge;

	private double totalPaid;

//	@Column(nullable = false)
//	@Enumerated(EnumType.ORDINAL)
//	private PaymentMode paymentMode;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private PaymentStatus paymentStatus;

	@OneToOne
	@JoinColumn(name = "billId") // Specify the appropriate column name
	private Bill bill;

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payment(Long paymentId, LocalDate paymentDate,  double totalPaid,
				   PaymentStatus paymentStatus, Bill bill) {
		super();
		this.paymentId = paymentId;
		this.paymentDate = paymentDate;
//		this.latePaymentCharge = latePaymentCharge;
		this.totalPaid = totalPaid;
		this.paymentStatus = paymentStatus;
		this.bill = bill;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

//	public double getLatePaymentCharge() {
//		return latePaymentCharge;
//	}
//
//	public void setLatePaymentCharge(double latePaymentCharge) {
//		this.latePaymentCharge = latePaymentCharge;
//	}

	public double getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}

//	public PaymentMode getPaymentMode() {
//		return paymentMode;
//	}
//	public void setPaymentMode(PaymentMode paymentMode) {
//		this.paymentMode = paymentMode;
//	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
}
