package com.cg.model;

public enum ConnectionType {
	
		   NON_INDUSTRIAL(0), INDUSTRIAL(1), AGRICULTURAL(2);

		   private final int value;

		   private ConnectionType(int value) {
		       this.value = value;
		   }

		   public int getValue() {
		       return value;
		   }
		
}
