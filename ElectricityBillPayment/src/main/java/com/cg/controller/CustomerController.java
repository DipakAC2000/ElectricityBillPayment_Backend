package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.UserCustomerdto;
import com.cg.model.Customer;
import com.cg.model.User;
import com.cg.services.CustomerServiceImpl;
import com.cg.services.UserServiceImpl;

@RestController("customerController")
@RequestMapping("/customer")
@CrossOrigin(origins = {"http://localhost:4200"}) 
public class CustomerController {
	
	@Autowired
	private CustomerServiceImpl customerService;
	
	@Autowired
	private UserServiceImpl userService;
	
	//POINT NO 1 and 2 //###############
	@PostMapping
	public ResponseEntity<Customer> addCustomer (@RequestBody UserCustomerdto userCustomer) {
		Customer customer = new Customer();
		customer.setAadhaarNumber(userCustomer.getAadhaarNumber());
		customer.setFirstName(userCustomer.getFirstName());
		customer.setMiddleName(userCustomer.getMiddleName());;
		customer.setLastName(userCustomer.getLastName());;
		customer.setMobileNumber(userCustomer.getMobileNumber());;
		customer.setEmail(userCustomer.getEmail());
		customer.setGender(userCustomer.getGender());
		
		User newUser = userService.registerUser(userCustomer.getUser());
		customer.setUser(newUser);
		
		Customer newCustomer = customerService.registerCustomer(customer);
		return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
	}
	
	//by customer id
	@GetMapping("/customerProfile/{customerId}")
	public ResponseEntity<Customer> getCustomerDetails(@PathVariable("customerId") Long CustomerId){
		Customer customerProfile = customerService.viewCustomerProfile(CustomerId);
		return new ResponseEntity<>(customerProfile,HttpStatus.OK);
	}
	
	//by user id
	@GetMapping("/customerProfile1/{userId}")
	public ResponseEntity<Customer> getCustomerDetailsByUserId(@PathVariable("userId") Long UserId){
		Customer customerProfile = customerService.viewCustomerProfileByUserId(UserId);
		return new ResponseEntity<>(customerProfile,HttpStatus.OK);
	}
	
	@PutMapping("/{customerId}")
	public ResponseEntity<Customer> modifyCsutomerDetails(
	        @PathVariable Long customerId,
	        @RequestBody Customer newCustomer) {
	    Customer customer = customerService.editCustomerProfile(customerId, newCustomer);
	    if(customer != null) {
	    return new ResponseEntity<>(customer, HttpStatus.OK);
	    }
	    else {
	    	return new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST); 
	    }
	}
	
	@GetMapping("/customerProfile/email/{email}")
	public ResponseEntity<Customer> getCustomerDetailsByEmail(@PathVariable("email") String email){
		Customer customerProfile = customerService.searchCustomerByEmail(email);
		return new ResponseEntity<>(customerProfile,HttpStatus.OK);
	}
	
	@GetMapping("/customerProfile/aadhaar/{aadhaarNumber}")
	public ResponseEntity<Customer> getCustomerDetailsByAadhaarNumber(@PathVariable("aadhaarNumber") Long aadhaarNumber){
		Customer customerProfile = customerService.searchCustomerByAadhaar(aadhaarNumber);
		return new ResponseEntity<>(customerProfile,HttpStatus.OK);
	}
	
	@GetMapping("/customerProfile/mobile/{mobileNumber}")
	public ResponseEntity<Customer> getCustomerDetailsByMobileNumber(@PathVariable("mobileNumber") String mobileNumber){
		Customer customerProfile = customerService.searchCustomerByMobile(mobileNumber);
		return new ResponseEntity<>(customerProfile,HttpStatus.OK);
	}
	
	@GetMapping("/customerProfile/name/{firstName}")
	public ResponseEntity<Customer> getCustomerDetailsByFirstName(@PathVariable("firstName") String firstName){
		Customer customerProfile = customerService.searchCustomerByName(firstName);
		return new ResponseEntity<>(customerProfile,HttpStatus.OK);
	}

}
