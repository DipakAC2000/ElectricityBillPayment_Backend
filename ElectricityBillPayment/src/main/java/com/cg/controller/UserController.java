package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.UserCredentialsdto;
import com.cg.model.User;
import com.cg.services.UserService;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User newUser) {
        User user = userService.registerUser(newUser);
        if(user!=null ) {
        	return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        else {
        	return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
        }
        
    }
    
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody UserCredentialsdto existingUser ) {
    	String username = existingUser.getUserName();
    	String password = existingUser.getPassword();
    	String userType = existingUser.getUserType();
        // Authenticate the user based on the provided username and password.
    	
    	User authenticatedUser = userService.loginUser(username, password);

        if (authenticatedUser != null) {
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
        } else {
            // Authentication failed, return an error.
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
//    @PostMapping("/login")
//    public ResponseEntity<User> loginUser(@RequestParam String username, @RequestParam String password) {
//        // Authenticate the user based on the provided username and password.
//        User authenticatedUser = userService.loginUser(username, password);
//
//        if (authenticatedUser != null) {
//            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
//        } else {
//            // Authentication failed, return an error.
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//    }



    @PutMapping("/{id}/changepassword")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestParam String newPassword) {
        User user = userService.viewUser(id);
        if (user != null) {
            userService.changePassword(user, newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
//    @PostMapping("/forgotPassword")
//    public void forgotPassword(@RequestParam String userName) {
//        userService.forgotPassword(userName);
//    }

    
    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/all")
    public List<User> viewUser() {
        return userService.viewUser();
    }

    @GetMapping("/{id}")
    public User viewUser(@PathVariable Long id) {
        return userService.viewUser(id);
    }

    @GetMapping("/byUserName/{userName}")
    public User viewUserByUserName(@PathVariable String userName) {
        return userService.viewUserByUserName(userName);
    }

//    @PostMapping("/validateUser")
//    public void validateUser(@RequestBody User user) {
//        userService.validateUser(user);
//    }
}
