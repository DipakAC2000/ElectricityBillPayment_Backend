package com.cg.model;

public enum PaymentMode {
   CREDIT(0), DEBIT(1), WALLET(2), NETBANKING(3);
   
   private final int value;

	   private PaymentMode(int value) {
	       this.value = value;
	   }

	   public int getValue() {
	       return value;
	   }
	
}
