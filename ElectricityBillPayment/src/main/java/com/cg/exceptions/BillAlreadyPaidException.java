package com.cg.exceptions;

public class BillAlreadyPaidException extends RuntimeException{
    public BillAlreadyPaidException(String message){
        super(message);
    }

}
