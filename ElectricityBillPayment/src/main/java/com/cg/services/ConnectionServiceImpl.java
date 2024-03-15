package com.cg.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.exceptions.ConsumerNumberNotFoundException;
import com.cg.exceptions.CustomerNotFoundException;
import com.cg.model.Address;
import com.cg.model.Connection;
import com.cg.model.ConnectionPhaseType;
import com.cg.model.ConnectionType;
import com.cg.model.Customer;
import com.cg.model.User;
import com.cg.repository.ConnectionRepository;

@Service
public class ConnectionServiceImpl implements ConnectionService {

	@Autowired
	private ConnectionRepository connectionRepository;
	
	//ADDING THE CONNECTION
	@Override
	 public Connection newConnectionRequest(Connection connection) {
		// Determine the phase type based on connection type
//        if (connection.getConnectionType().ordinal() == ConnectionType.INDUSTRIAL.ordinal() ||
//                connection.getConnectionType().ordinal() == ConnectionType.AGRICULTURAL.ordinal()) {
//            connection.setPhaseType(ConnectionPhaseType.THREE_PHASE);
//        } else {
//            connection.setPhaseType(ConnectionPhaseType.SINGLE_PHASE);
//        }
		 return connectionRepository.save(connection);
	}
	
	//searching customer by consumer number
	@Override
	public Customer searchCustomerByConsumerNumber(Long consumerNumber) {
		 Optional<Connection> optionalConnection = connectionRepository.findByConsumerNumber(consumerNumber);
		 if (optionalConnection.isPresent()) {
			 Connection connection = optionalConnection.get();
			 Customer customer =connection.getCustomer();
			 //checking customer with this consumer id is present or not
			 if(customer!= null) {
			 return customer;
			 }
			 else {
				 throw new CustomerNotFoundException("Customer with Consumer Number "+ consumerNumber + " not found.");
			 }
			 
			 //here in this please add CustomerNotFoundException
		 }
		 else {
			throw new ConsumerNumberNotFoundException("Connection with Consumer Number " + consumerNumber + " not found.");
		}
		 
	}
	 
	
	//POINT NO 4 //###############
	//modifying the status of the connection and the connectionDate
	@Override
    public Optional<Connection> modifyConnectionAddress(Long consumerNumber, Connection modifiedConnection) {
        
        Optional<Connection> existingConnection = connectionRepository.findByConsumerNumber(consumerNumber);

        if (existingConnection.isPresent()){
        	Connection connectionToUpdate = existingConnection.get();
            // Updating  the connection details
        	connectionToUpdate.setConnectionStatus(modifiedConnection.isConnectionStatus());
        	connectionToUpdate.setConnectionDate(LocalDate.now());
        	
         	Connection updatedConnection = connectionRepository.save(connectionToUpdate);
            return Optional.of(updatedConnection);
        }
        return Optional.empty();
    }

	 
	 
	 //now getting the active connection by various entities now by village
	 @Override
	 public List<Connection> findActiveConnectionByVillage(String village){
		 return connectionRepository.findByAddressVillageAndConnectionStatus(village, true);
	 }
	 
	 //by taluka
	 @Override
	 public List<Connection> findActiveConnectionByTaluka(String taluka){
		 return connectionRepository.findByAddressTalukaAndConnectionStatus(taluka, true);
	 }
	   
	 //by district
	 @Override
	 public List<Connection> findActiveConnectionByDistrict(String district){
		   return connectionRepository.findByAddressDistrictAndConnectionStatus(district, true);
	   }
	   
	   @Override
	   public List<Connection> findactiveConnectionByPincode(String pincode){
		   return connectionRepository.findByAddressPincodeAndConnectionStatus(pincode, true);
	   }
	   
	   @Override
	   public List<Connection> findInactiveConnectionByVillage(String village){
		   return connectionRepository.findByAddressVillageAndConnectionStatus(village, false);
	   }
	   
	   @Override
	   public List<Connection> findInactiveConnectionByTaluka(String taluka){
		   return connectionRepository.findByAddressTalukaAndConnectionStatus(taluka, false);
	   }
	   
	   @Override
	   public List<Connection> findInactiveConnectionByDistrcit(String district){
		   return connectionRepository.findByAddressDistrictAndConnectionStatus(district, false);
	   }
	  
	   @Override
	   public List<Connection> findInactiveConnectionByPincode(String pincode){
		   return connectionRepository.findByAddressPincodeAndConnectionStatus(pincode, false);
	   }

	public List<Connection> getInactiveConnections() {
		return connectionRepository.findByConnectionStatus(false);
	}

	   
}
