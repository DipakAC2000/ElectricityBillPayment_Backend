package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.model.Address;

import com.cg.services.AddressServiceImpl;

@RestController("addressController")
@RequestMapping("/address")
public class AddressController {
	
	
	@Autowired
	private AddressServiceImpl addressService;
	
	@PostMapping
	public ResponseEntity<Address> addAddress (@RequestBody Address address) {
		Address newAddress = addressService.addingaddress(address);
		return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
	}

}
