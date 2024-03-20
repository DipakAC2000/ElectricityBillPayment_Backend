package com.cg.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidUnitsConsumedException.class)
    public String invalidUnitsConsumedException(InvalidUnitsConsumedException e){
        return "Units consumed cannot be less than the consumption of the current month: ";
    }
    @ExceptionHandler(InvalidReadingDateException.class)
    public String invalidReadingDateException(InvalidReadingDateException e){
        return "Invalid reading date Exception:" +e.getMessage();
    }
    @ExceptionHandler(PaymentNotFoundException.class)
    public String paymentNotFoundException(PaymentNotFoundException e){
        return "Payment Not Found Exception:" +e.getMessage();
    }
    @ExceptionHandler(PaymentServiceException.class)
    public String paymentServiceException(PaymentServiceException e){
        return null;
    }

    @ExceptionHandler(BillNotFoundException.class)
    public String billNotFoundException(BillNotFoundException e)
    {
        return "Bill Not found Exception: "+e.getMessage();
    }

    @ExceptionHandler(BillAlreadyPaidException.class)
    public String billAlreadyPaidException(BillAlreadyPaidException e){
        return "Bill is already paid:"+e.getMessage();
    }
    @ExceptionHandler(ConsumerNumberNotFoundException.class)
    public String handlerForConsumerNumberNotFoundException(ConsumerNumberNotFoundException e)
    {
        return "Consumer_Number Not found Exception: "+e.getMessage();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public String handlerForCustomerNotFoundException(CustomerNotFoundException e)
    {
        return "Customer Not found Exception: "+e.getMessage();
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public String handlerForCustomerAlreadyExistsException(CustomerAlreadyExistsException e)
    {
        return "Customer records is already there : "+e.getMessage() +" so try with new";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handlerForUserAlreadyExistsException(UserAlreadyExistsException e)
    {
        return "User records is already there : "+ e.getMessage() ;
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public String handlerForConstraintViolationException(ConstraintViolationException e)
    {
        return "null";
    }

}
