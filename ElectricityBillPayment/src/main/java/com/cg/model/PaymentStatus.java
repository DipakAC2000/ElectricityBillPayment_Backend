package com.cg.model;

public enum PaymentStatus {
	FAILURE(0),SUCCESS(1),PAID(2),BILL_NOT_FOUND(3),INVALID_AMOUNT(4);
	private final int value;

	private PaymentStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
