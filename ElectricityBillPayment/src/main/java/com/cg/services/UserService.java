package com.cg.services;

import java.util.List;

import com.cg.model.User;

public interface UserService {

	public User registerUser(User newUser);
	
	// login user method
	public User loginUser(String username, String password);
	
	//change password(User):void
	public void changePassword(User user, String newPassword);
	
	// forgotPassword(String):void
	//public void forgotPassword(String userName);
	
	//emailPassword(String):void

    public User updateUser(User users);

    public void deleteUser(Long id);

    public List<User> viewUser();

    public User viewUser(Long id);
    
    public User viewUserByUserName(String userName);

   // public void validateUser(User users);
}
