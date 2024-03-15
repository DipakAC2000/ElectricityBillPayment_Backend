package com.cg.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT * FROM User where userName=?1 ")
	Optional<User> findByUserName(String userName);
}
