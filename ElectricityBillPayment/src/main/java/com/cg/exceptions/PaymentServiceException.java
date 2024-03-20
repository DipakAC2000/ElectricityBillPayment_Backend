package com.cg.exceptions;

public class PaymentServiceException extends RuntimeException{
    public PaymentServiceException(String message){
        super(message);
    }
}
