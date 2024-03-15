package com.cg.services;

import java.util.List;
import java.util.Optional;

import com.cg.model.Connection;
import com.cg.model.Customer;

public interface ConnectionService {
   public Connection newConnectionRequest(Connection connection);
   
   public Customer searchCustomerByConsumerNumber(Long consumerNumber);
   
   public Optional<Connection> modifyConnectionAddress(Long consumerNumber, Connection modifiedConnection);
   
   public List<Connection> findActiveConnectionByVillage(String village);
   
   public List<Connection> findActiveConnectionByTaluka(String taluka);
   
   public List<Connection> findActiveConnectionByDistrict(String district);
   
   public List<Connection> findactiveConnectionByPincode(String pincode);
   
   public List<Connection> findInactiveConnectionByVillage(String village);
   
   public List<Connection> findInactiveConnectionByTaluka(String taluka);
   
   public List<Connection> findInactiveConnectionByDistrcit(String district);
   
   public List<Connection> findInactiveConnectionByPincode(String pincode);
}
