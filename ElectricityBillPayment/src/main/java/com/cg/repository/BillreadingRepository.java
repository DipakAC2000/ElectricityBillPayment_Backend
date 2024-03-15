package com.cg.repository;


import com.cg.model.BillReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillreadingRepository extends JpaRepository<BillReading,Long> {
}
