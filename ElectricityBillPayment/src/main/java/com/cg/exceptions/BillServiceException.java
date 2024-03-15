package com.cg.exceptions;

public class BillServiceException extends RuntimeException{
    public BillServiceException(String message) {
        super(message);
    }
}
