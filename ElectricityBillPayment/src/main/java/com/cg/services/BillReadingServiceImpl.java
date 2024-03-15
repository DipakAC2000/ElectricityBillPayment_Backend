package com.cg.services;

import com.cg.model.BillReading;
import com.cg.repository.BillreadingRepository;
import com.cg.repository.ConnectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class BillReadingServiceImpl {
    @Autowired
    ConnectionRepository connectionRepository;
   @Autowired
    BillreadingRepository billreadingRepository;
    public BillReading createBillReading(BillReading billReading) {
        return billreadingRepository.save(billReading);
    }
    public List<BillReading> getAllBillReadings() {
        return billreadingRepository.findAll();
    }

    public BillReading getBillReadingById(Long billReadingId) {
        return billreadingRepository.findById(billReadingId)
                .orElseThrow(() -> new EntityNotFoundException("Bill Reading not found"));
    }
}
