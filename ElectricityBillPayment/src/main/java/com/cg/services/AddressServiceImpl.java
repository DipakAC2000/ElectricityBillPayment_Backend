package com.cg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cg.model.Address;
import com.cg.repository.AddressRepository;

@Service("addressService")
public class AddressServiceImpl {

	@Autowired
	private AddressRepository addressRepository;
	
	
	
	public Address addingaddress(Address address) {
		return addressRepository.save(address);
	}
}
