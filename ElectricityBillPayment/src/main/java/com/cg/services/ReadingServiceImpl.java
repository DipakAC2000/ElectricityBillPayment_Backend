package com.cg.services;

import com.cg.exceptions.ConsumerNumberNotFoundException;
import com.cg.exceptions.IdNotFoundException;
import com.cg.exceptions.ReadingNotFoundException;
import com.cg.exceptions.UnitsNotNegativeException;
import com.cg.model.Connection;
import com.cg.model.Customer;
import com.cg.model.Reading;
import com.cg.repository.ConnectionRepository;
import com.cg.repository.ReadingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ReadingServiceImpl implements ReadingService {
    @Autowired
    ReadingRepository readingRepository;

    @Autowired
    BillServiceImpl billService;

    @Autowired
    ConnectionRepository connectionRepository;
    
    @Override
    public Reading selfSubmitReading(Reading reading, Long consumerNumber) {
        // If user has entered negative unitsConsumed then throw an exception.
        if (reading.getUnitsConsumed() != null && reading.getUnitsConsumed() <= 0) {
            throw new UnitsNotNegativeException("Units consumed must be greater than zero: ");
        } else {
            // Fetch the associated Connection entity by consumerNumber
            Optional<Connection> optionalConnection = connectionRepository.findByConsumerNumber(consumerNumber);

            Connection connection = optionalConnection.orElseThrow(() ->
                    new ConsumerNumberNotFoundException("Consumer with number " + consumerNumber + " not found."));

            // Link the connection to the reading
            reading.setConnection(connection);

            // Save the reading to the database
            Reading savedReading = readingRepository.save(reading);

            // You can call your existing method to generate the bill i.e., addBill method from billService here.
            // Bill generatedBill = billService.addBill(savedReading.getConnection(), savedReading, new Bill());
            return savedReading;
        }
    }


//    @Override
//    public Reading selfSubmitReading(Reading reading, Long consumerNumber) {
//
//            // If user has entered negative unitsConsumed then throw an exception.
//            if (reading.getUnitsConsumed() != null && reading.getUnitsConsumed() <= 0) {
//                throw new UnitsNotNegativeException("Units consumed must be greater than zero: ");
//            } else {
//                // Set the connection based on the consumer number
//                Connection connection = connectionRepository.findByConsumerNumber(consumerNumber);
//                if (connection == null) {
//                    throw new ConsumerNumberNotFoundException("Consumer with number " + consumerNumber + " not found.");
//                }
//
//                // Link the connection to the reading
//                reading.setConnection(connection);
//
//                // Save the reading to the database
//                // Save the submitted reading to the database
//                Reading savedReading = readingRepository.save(reading);
//
//                // You can call your existing method to generate the bill i.e. addBill method from billService here.
//                // Bill generatedBill = billService.addBill(savedReading.getConnection(), savedReading, new Bill());
//                return savedReading;
//            }
//
//    }


    @Override
    public Optional<Reading> findMeterReadingByConsumerNumberAndBillDate(Long consumerNumber, LocalDate billDate) {
     if(consumerNumber==null || billDate==null){
         throw new IllegalArgumentException("consumerNumber and billDate must not be null");
     }

     //Retrieve the reading from reading repository.
      Optional<Reading>  reading= Optional.ofNullable(readingRepository.findMeterReadingByConsumerNumberAndBillDate(consumerNumber, billDate));
        if(reading!=null)
            return Optional.of(reading.get());
        else
            throw new ReadingNotFoundException("Reading not found for consumer number  + consumerNumber +  and bill date  + billDate");
    }

    @Override
    public List<Reading> findMeterReadingByConsumerNumber(Long consumerNumber) {
        // Validate the input parameter
        if (consumerNumber == null || consumerNumber <= 0) {
            throw new IllegalArgumentException("Consumer number must be a positive non-null value.");
        }

        //Use repo to find readingby consumer number
        List<Reading> reading= readingRepository.findMeterReadingByConsumerNumber(consumerNumber);
        if(reading.isEmpty()){
            throw new ReadingNotFoundException("No Reading not found with consumerNumber: "+consumerNumber);
        }
        return reading;
    }

    public Reading getReadingById(Long id){ //bill Id
        Optional<Reading> reading=readingRepository.findById(id);
        if(reading.isPresent()){
            return reading.get();
        }
        throw new IdNotFoundException("Reading not found with id:" +id);
    }

    public List<Reading> getReadingByConsumerNumber(Long consumerNumber){
        List<Reading> reading= readingRepository.findMeterReadingByConsumerNumber(consumerNumber);
        if(reading!=null){
            return reading;
        }
        throw new ConsumerNumberNotFoundException("Reading not found with consumer number:" +consumerNumber);
    }
    public List<Reading> getAllReading(){
        List<Reading> readings=readingRepository.findAll();
        return readings;
    }

    public Reading updateReading(Long id, Reading reading){
        //Fetch the existing Reading by its ID.
        Optional<Reading> existingReading=readingRepository.findById(id);
        if(existingReading.isPresent()){
            Reading existing = existingReading.get();

           // Update the properties of the existing Reading with the new values
            existing.setReadingDate(reading.getReadingDate());
            existing.setUnitsConsumed(reading.getUnitsConsumed());
            existing.setPricePerUnits(reading.getPricePerUnits());

            // Save the updated reading to repository.
           Reading updateReading=readingRepository.save(existing);
            return updateReading;
        }
        else {
            throw new IdNotFoundException("Reading not found with id:" +id);
        }
    }

   public void deleteReadingByReadingId(Long readingId){
        Optional<Reading> r=readingRepository.findById(readingId);
        if(r.isPresent()){
        readingRepository.deleteById(readingId);
        System.out.println("Reading deleted successfully.");
         }
        else {
            throw new IdNotFoundException("Reading not found with id:" +readingId);
        }
    }

    public void deleteAllReadings(){
        readingRepository.deleteAll();
    }
}
