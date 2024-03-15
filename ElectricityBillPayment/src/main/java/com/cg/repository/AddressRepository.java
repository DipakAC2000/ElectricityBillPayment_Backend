package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
