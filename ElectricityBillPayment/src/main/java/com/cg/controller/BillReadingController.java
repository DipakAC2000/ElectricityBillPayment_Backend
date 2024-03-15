package com.cg.controller;

import com.cg.model.BillReading;
import com.cg.repository.ConnectionRepository;
import com.cg.services.BillReadingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billreadings")
public class BillReadingController {
    @Autowired
    BillReadingServiceImpl billReadingService;
    @Autowired
    ConnectionRepository connectionRepository;

    @PostMapping("/createBillReading")
    public BillReading createBillReading( @RequestBody BillReading billReading) {
//        Integer previousReading = billReading.getPreviousReading();
//        Integer previousPayment = billReading.getPreviousPayment();
//        Long custId=billReading.getCustId();
        return billReadingService.createBillReading(billReading);
    }

    @GetMapping("/getAllBillReadings")
    public List<BillReading> getAllBillReadings() {
        return billReadingService.getAllBillReadings();
    }

    @GetMapping("/betBillReadingById/{billReadingId}")
    public BillReading getBillReadingById(@PathVariable Long billReadingId) {
        return billReadingService.getBillReadingById(billReadingId);
    }



}
