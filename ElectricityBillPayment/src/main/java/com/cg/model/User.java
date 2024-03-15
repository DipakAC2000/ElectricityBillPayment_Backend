package com.cg.model;

import jakarta.validation.constraints.NotEmpty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(unique = true)
	@NotNull(message = "username must not be null")
	private String userName;
	
	@Size(min=5, message = "Password should be minimum of length 5.")
	private String password;
	
	@NotEmpty
    private String userType="CUSTOMER";

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public User(Long id, @NotNull(message = "username must not be null") String userName,
			@Size(min = 5, message = "Password should be minimum of length 5.") String password,
			@NotEmpty String userType) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.userType = userType;
	}

	public Long getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", userType=" + userType + "]";
	}	
	
}
