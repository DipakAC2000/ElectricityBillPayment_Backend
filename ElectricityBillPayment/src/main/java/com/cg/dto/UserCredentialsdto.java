package com.cg.dto;

public class UserCredentialsdto {
	
	private String userName;
	
	private String password;
	
	private String userType;

	public UserCredentialsdto() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public UserCredentialsdto(String userName, String password, String userType) {
		super();
		this.userName = userName;
		this.password = password;
		this.userType = userType;
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
	
}
