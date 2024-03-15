package com.cg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.exceptions.UserAlreadyExistsException;
import com.cg.model.User;
import com.cg.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User newUser) {
        Optional<User> existingUser =userRepository.findByUserName(newUser.getUserName());
        if(!existingUser.isPresent()) {
        	return userRepository.save(newUser);
        }
        else {
        	throw new UserAlreadyExistsException("Username already present so please try with New");
        }
    }

    @Override
    public User loginUser(String userName, String password) {
    	return userRepository.findByUserName(userName)
                .filter(user -> user.getPassword().equals(password))
                .orElse(null); // Return null or handle authentication failure as needed
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
    }
    

//    @Override
//    public void forgotPassword(String userName) {
//        
//    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> viewUser() {
        return userRepository.findAll();
    }

    @Override
    public User viewUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User viewUserByUserName(String userName) {
    	 return userRepository.findByUserName(userName)
    	            .orElseThrow(() -> new RuntimeException("User not found with username: " + userName));

    }

//    @Override
//    public void validateUser(User user) {
//        // Implement custom user validation logic as needed
//    }
    
    //password encoder 
}
