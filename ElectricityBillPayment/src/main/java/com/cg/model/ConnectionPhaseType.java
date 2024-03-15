package com.cg.model;

public enum ConnectionPhaseType {
	  SINGLE_PHASE(0), THREE_PHASE(1);

	   private final int value;

	   private ConnectionPhaseType(int value) {
	       this.value = value;
	   }

	   public int getValue() {
	       return value;
	   }

}
