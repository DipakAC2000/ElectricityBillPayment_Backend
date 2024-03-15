package com.cg.dto;

import com.cg.model.User;


public class UserCustomerdto {

	private Long aadhaarNumber;
    
    private String firstName;
    
    private String middleName;
    
    private String lastName;
    
    private String mobileNumber;
    
    private String email;
    
    private String gender;
    
    private User user;

	public UserCustomerdto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserCustomerdto(Long aadhaarNumber, String firstName, String middleName, String lastName,
			String mobileNumber, String email, String gender, User user) {
		super();
		this.aadhaarNumber = aadhaarNumber;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.gender = gender;
		this.user = user;
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
