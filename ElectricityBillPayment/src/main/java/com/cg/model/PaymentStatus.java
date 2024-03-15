package com.cg.model;

public enum PaymentStatus {
   SUCCESS(0), FAILURE(1);
   private final int value;

	   private PaymentStatus(int value) {
	       this.value = value;
	   }

	   public int getValue() {
	       return value;
	   }
	
}
