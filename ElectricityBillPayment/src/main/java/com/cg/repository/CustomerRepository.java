package com.cg.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.model.Customer;

@Repository("customerRepository")
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	

	@Query("SELECT * from Customer where email=?1")
	Optional<Customer> findByEmail(String email);

	@Query("SELECT * from Customer where mobileNumber=?1")
	Optional<Customer> findByMobileNumber(String mobileNumber);

	@Query("SELECT * from Customer where aadhaarNumber=?1")
	Optional<Customer> findByAadhaarNumber(Long aadhaarNumber);

	@Query("SELECT * from Customer where firstName=?1")
	Optional<Customer> findByFirstName(String firstName);

	@Query("SELECT * from Customer where userId=?1")
	Optional<Customer> findByUserId(Long userId);

}
