package com.cg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Customer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    
    @NotNull(message = "Aadhaar number must not be null")
    @Digits(integer = 12, fraction = 0, message = "Aadhaar number should be 12 digits")
    //@Size(min = 12, max = 12, message = "Aadhaar number should be exactly 12 digits")
    @Column(name = "aadhaar_number", unique = true)
    private Long aadhaarNumber;
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    private String middleName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    

    @Column(unique = true)
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]*$", message = "Mobile number should only contain digits")
   // @Size(min = 10, max = 10, message = "Mobile number should be 10 digits")
    private String mobileNumber;
    
    
    @Column(unique = true)
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")
    private String email;
    

    @NotBlank(message = "Gender is required")
    private String gender;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
	
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Customer(Long customerId,
			@NotNull(message = "Aadhaar number must not be null") @Digits(integer = 12, fraction = 0, message = "Aadhaar number should be 12 digits") Long aadhaarNumber,
			@NotBlank(message = "First name is required") String firstName, String middleName,
			@NotBlank(message = "Last name is required") String lastName,
			@NotBlank(message = "Mobile number is required") @Pattern(regexp = "^[0-9]*$", message = "Mobile number should only contain digits") String mobileNumber,
			@NotBlank(message = "Email is required") @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format") String email,
			@NotBlank(message = "Gender is required") String gender, User user) {
		super();
		this.customerId = customerId;
		this.aadhaarNumber = aadhaarNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.gender = gender;
		this.user = user;
	}


	public Long getCustomerId() {
		return customerId;
	}
	
	public Long getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(Long aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    
	
}
