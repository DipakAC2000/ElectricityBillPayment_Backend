package com.cg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.model.Connection;

@Repository("connectionRepository")
public interface ConnectionRepository extends JpaRepository<Connection, Long>{

	Optional<Connection> findByConsumerNumber(long consumerNumber);

	@Query("SELECT c.* FROM Connection c JOIN c.address a WHERE a.village = :village AND c.connectionStatus = :b")
	List<Connection> findByAddressVillageAndConnectionStatus(String village, boolean b);

	@Query("SELECT c.* FROM Connection c JOIN c.address a WHERE a.village = :village AND c.connectionStatus = :b")
	List<Connection> findByAddressTalukaAndConnectionStatus(String taluka, boolean b);

	@Query("SELECT c.* FROM Connection c JOIN c.address a WHERE a.village = :village AND c.connectionStatus = :b")
	List<Connection> findByAddressPincodeAndConnectionStatus(String pincode, boolean b);

	@Query("SELECT c.* FROM Connection c JOIN c.address a WHERE a.village = :village AND c.connectionStatus = :b")
	List<Connection> findByAddressDistrictAndConnectionStatus(String district, boolean b);

	@Query("SELECT c.* FROM Connection c WHERE c.consumerNumber =:consumerNumber AND c.connectionStatus =:b")
	List<Connection> findByConsumerNumberAndConnectionStatus(Long consumerNumber, boolean b);

	@Query("SELECT c.* FROM Connection c WHERE c.connectionStstus =:b")
	List<Connection> findByConnectionStatus(boolean b);
	
	
	 
}
