package com.cg.controller;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.ConnectionAddressdto;
import com.cg.model.Address;
import com.cg.model.Connection;
import com.cg.model.Customer;
import com.cg.model.User;
import com.cg.repository.AddressRepository;
import com.cg.repository.CustomerRepository;
import com.cg.services.AddressServiceImpl;
//import com.cg.repository.ConnectionRepository;
import com.cg.services.ConnectionServiceImpl;
import com.cg.services.UserServiceImpl;

@RestController("connectionController")
@RequestMapping("/application")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ConnectionController {
	
	@Autowired
	private ConnectionServiceImpl connectionService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressServiceImpl addressService;
	
//	@Autowired
//	private ConnectionRepository connectionRepositroy;
	
	//POINT NO 3 //###############
	//Adding the connection
	@PostMapping("/{customerId}")
	public ResponseEntity<Connection> addConnection (@RequestBody ConnectionAddressdto connectionaddress,@PathVariable("customerId")Long customerId) {
		Connection connection = new Connection(); //adding the connection
		connection.setConnectionType(connectionaddress.getConnectionType()); //adding connection details from requestbody
		connection.setPhaseType(connectionaddress.getPhaseType());
		
		//adding the address from the request body
		Address newAddress = addressService.addingaddress(connectionaddress.getAddress());
		connection.setAddress(newAddress);
		
				
		Optional<Customer> customer = customerRepository.findById(customerId);
		if(!customer.isEmpty()) {
		     connection.setCustomer(customer.get());
		Connection newConnection = connectionService.newConnectionRequest(connection);
		return new ResponseEntity<>(newConnection, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//SEARCHING CUSTOMER BY CONSUMER NUMBER 
	@GetMapping("/Customer/{consumerNumber}")
	public ResponseEntity<Customer> findCustomer(@PathVariable("consumerNumber") Long consumerNumber) {
		Customer customer = connectionService.searchCustomerByConsumerNumber(consumerNumber);
		return new ResponseEntity<>(customer, HttpStatus.OK); 
	}
	
	
	//updating the connection details by consumer number
	//POINT NO 4 //###############
	@PutMapping("/{consumerNumber}")
    public ResponseEntity<?> modifyConnectionAddress(
            @PathVariable Long consumerNumber,
            @RequestParam String username,
            @RequestParam String password,
            @RequestBody Connection modifiedConnection) {

        // Authenticating  the user based on the provided userName and password.
        User authenticatedUser = userService.loginUser(username, password);

        if (authenticatedUser != null) {
            
            if ("ADMIN".equals(authenticatedUser.getUserType())) {
                // User is an ADMIN & allowing ADMIN to modify the connection.
                Optional<Connection> updatedConnection = connectionService.modifyConnectionAddress(consumerNumber, modifiedConnection);

                if (updatedConnection.isPresent()) {
                    // Connection updated successfully.
                    return new ResponseEntity<>(updatedConnection.get(), HttpStatus.OK);
                } else {
                    // Connection not found or couldn't be updated.
                    return new ResponseEntity<>("Connection not found or couldn't be updated.", HttpStatus.NOT_FOUND);
                }
            } else {
                // User is not an ADMIN, it returns an error. // userType is customer
                return new ResponseEntity<>("Access forbidden.Unauthorized access, Only admin can update", HttpStatus.FORBIDDEN);
            }
        } else {
            // Authentication failed, return an unauthorized error. //details not present in the database(userName)
            return new ResponseEntity<>("Authentication failed.", HttpStatus.UNAUTHORIZED);
        }
    }

	
	
	@GetMapping("/active/by-village/{village}")
    public ResponseEntity<List<Connection>> findActiveConnectionByVillage(@PathVariable String village) {
        List<Connection> activeConnections = connectionService.findActiveConnectionByVillage(village);
        return new ResponseEntity<>(activeConnections, HttpStatus.OK);
    }
	
	@GetMapping("/active/by-taluka/{taluka}")
    public ResponseEntity<List<Connection>> findActiveConnectionByTaluka(@PathVariable String taluka) {
        List<Connection> activeConnections = connectionService.findActiveConnectionByTaluka(taluka);
        return new ResponseEntity<>(activeConnections, HttpStatus.OK);
    }
	
	@GetMapping("/active/by-district/{district}")
    public ResponseEntity<List<Connection>> findActiveConnectionByDistrict(@PathVariable String district) {
        List<Connection> activeConnections = connectionService.findActiveConnectionByDistrict(district);
        return new ResponseEntity<>(activeConnections, HttpStatus.OK);
    }
	
	@GetMapping("/active/by-pincode/{pincode}")
    public ResponseEntity<List<Connection>> findActiveConnectionByPincode(@PathVariable String pincode) {
        List<Connection> activeConnections = connectionService.findactiveConnectionByPincode(pincode);
        return new ResponseEntity<>(activeConnections, HttpStatus.OK);
    }
	
	@GetMapping("/inactive/by-village/{village}")
    public ResponseEntity<List<Connection>> findInactiveConnectionByVillage(@PathVariable String village) {
        List<Connection> inActiveConnections = connectionService.findInactiveConnectionByVillage(village);
        return new ResponseEntity<>(inActiveConnections, HttpStatus.OK);
    }
	
	@GetMapping("/inactive/by-taluka/{taluka}")
    public ResponseEntity<List<Connection>> findInactiveConnectionByTaluka(@PathVariable String taluka) {
        List<Connection> inActiveConnections = connectionService.findInactiveConnectionByTaluka(taluka);
        return new ResponseEntity<>(inActiveConnections, HttpStatus.OK);
    }
	
	@GetMapping("/inactive/by-district/{district}")
    public ResponseEntity<List<Connection>> findInactiveConnectionByDistrict(@PathVariable String district) {
        List<Connection> inActiveConnections = connectionService.findInactiveConnectionByDistrcit(district);
        return new ResponseEntity<>(inActiveConnections, HttpStatus.OK);
    }
	
	@GetMapping("/inactive/by-pincode/{pincode}")
    public ResponseEntity<List<Connection>> findInactiveConnectionByPincode(@PathVariable String pincode) {
        List<Connection> inActiveConnections = connectionService.findInactiveConnectionByPincode(pincode);
        return new ResponseEntity<>(inActiveConnections, HttpStatus.OK);
    }
	
	@GetMapping("/inactiveConnections")
	public ResponseEntity<List<Connection>> getAllInactiveConnections() {
		List<Connection> inActiveConnections = connectionService.getInactiveConnections();
		return new ResponseEntity<>(inActiveConnections, HttpStatus.OK); 
    }
	
	

}
