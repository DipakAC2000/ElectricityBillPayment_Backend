package com.cg.services;

import com.cg.model.Customer;

public interface CustomerService {
	
	public Customer registerCustomer(Customer customer);
	
	public Customer viewCustomerProfile(Long customerId );
	
	public Customer editCustomerProfile(Long customerId, Customer customer);
	
	//public Customer searchCustomerByCustomerId(Long customerId);
	
	public Customer searchCustomerByEmail(String email);
	
	public Customer searchCustomerByAadhaar(Long aadhaarNumber);
	
	public Customer searchCustomerByMobile(String mobileNumber);
	
	public Customer searchCustomerByName(String name);

}
