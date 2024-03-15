package com.cg.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.exceptions.CustomerAlreadyExistsException;
import com.cg.exceptions.CustomerNotFoundException;
import com.cg.model.Customer;
import com.cg.model.User;
import com.cg.repository.CustomerRepository;
import com.cg.repository.UserRepository;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	

	@Autowired
	private UserRepository userRepository;
	
	//POINT NO 2 //###############
	//Registering that customer
	@Override
	public Customer registerCustomer(Customer customer) {
		//adding validation of AadhaarNumber
		Optional<Customer> oldCustomerByAadhaarNumber = customerRepository.findByAadhaarNumber(customer.getAadhaarNumber());
		Optional<User> oldUserbyUserName = userRepository.findByUserName(customer.getUser().getUserName());
		//CHECK FOR THE USERNAME AND WRITE CODES AND ALSO WRITE CODE FOR EXCEPTION USERALREADYEXISTS
		if(oldCustomerByAadhaarNumber.isPresent()) {
			throw new CustomerAlreadyExistsException("Customer with this Aadhaar Number already exists.");
		}
		//Optional<Customer> oldCustomerByUsername = customerRepository.find
		return customerRepository.save(customer);
	}
	
	
	
	
	//view customer details by customer id
	@Override
	public Customer viewCustomerProfile(Long customerId ) {
		Optional<Customer> customerprofile = customerRepository.findById(customerId);
		if(customerprofile.isPresent()) {
			return customerprofile.get();
		}
		else {
			throw new CustomerNotFoundException("Customer with Id " + customerId + " is not present.Please register the new customer.");
		}
	}
	

	//view customer details by user id
		
		public Customer viewCustomerProfileByUserId(Long userId ) {
			Optional<Customer> customerprofile = customerRepository.findByUserId(userId);
			if(customerprofile.isPresent()) {
				return customerprofile.get();
			}
			else {
				throw new CustomerNotFoundException("Customer with this userId " + userId + " is not present.Please register the new customer.");
			}
		}


	//editing the customer details
	@Override
	public Customer editCustomerProfile(Long customerId, Customer newCustomer) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
	    if (optionalCustomer.isPresent()) {
	        Customer customer = optionalCustomer.get();

	        // Update fields that should be updated
	        //by using this we can update single entity means the few entities.
	        //no need to enter the full details only by customer id we can update the required entities
	        
	        if (newCustomer.getFirstName() != null) {
	            customer.setFirstName(newCustomer.getFirstName());
	        }
	        if (newCustomer.getMiddleName() != null) {
	            customer.setMiddleName(newCustomer.getMiddleName());
	        }
	        if (newCustomer.getLastName() != null) {
	            customer.setLastName(newCustomer.getLastName());
	        }
	        if (newCustomer.getAadhaarNumber() != null) {
	            customer.setAadhaarNumber(newCustomer.getAadhaarNumber());
	        }
	        if (newCustomer.getMobileNumber() != null) {
	            customer.setMobileNumber(newCustomer.getMobileNumber());
	        }
	        if (newCustomer.getEmail() != null) {
	            customer.setEmail(newCustomer.getEmail());
	        }
	        if (newCustomer.getGender() != null) {
	            customer.setGender(newCustomer.getGender());
	        }

	        return customerRepository.save(customer);
	    } else {
	        throw new CustomerNotFoundException("Customer with Id " + customerId + " is not present. Please register the new customer.");
	    }
	}
	
	
	
	//searching customer by customerId
	
	
	
	//SEARCHING CUSTOMER BY VARIOUS ENTITIES
	@Override
    public Customer searchCustomerByEmail(String email) {
    		Optional<Customer> customerprofile = customerRepository.findByEmail(email);
    		if(customerprofile.isPresent()) {
    			return customerprofile.get();
    		}
    		else {
    			throw new CustomerNotFoundException("Customer with email " + email + " is not present.Please register the new customer.");
    		}
    }
	
	@Override
	public Customer searchCustomerByAadhaar(Long aadhaarNumber) {
		Optional<Customer> customerprofile = customerRepository.findByAadhaarNumber(aadhaarNumber);
		if(customerprofile.isPresent()) {
			return customerprofile.get();
		}
		else {
			throw new CustomerNotFoundException("Customer with Aadhaar Number " + aadhaarNumber + " is not present.Please register the new customer.");
		}
	}
	
	@Override
	public Customer searchCustomerByMobile(String mobileNumber) {
		Optional<Customer> customerprofile = customerRepository.findByMobileNumber(mobileNumber);
		if(customerprofile.isPresent()) {
			return customerprofile.get();
		}
		else {
			throw new CustomerNotFoundException("Customer with Mobile Number " + mobileNumber + " is not present.Please register the new customer.");
		}
	}
	
	@Override
	public Customer searchCustomerByName(String firstName) {
		Optional<Customer> customerprofile = customerRepository.findByFirstName(firstName);
		if(customerprofile.isPresent()) {
			return customerprofile.get();
		}
		else {
			throw new CustomerNotFoundException("Customer with First Name " + firstName + " is not present.Please register the new customer.");
		}
	}

}
