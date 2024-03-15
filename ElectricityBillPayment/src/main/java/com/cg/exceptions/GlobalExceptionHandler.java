package com.cg.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
