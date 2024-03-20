package com.cg.services;

import com.cg.exceptions.*;
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

            // Fetch the latest reading for the given consumerNumber from the repository
            List<Reading> latestReading = readingRepository.findLatestReadingByConsumerNumber(consumerNumber);

            // System.out.println("List of Reading is :" +latestReading);

            Reading savedReading = null;
            if (latestReading.isEmpty()) {
                // Handle the case when there are no readings for the consumer
                int currentReading = reading.getUnitsConsumed();

                // Link the connection to the reading
                reading.setConnection(connection);
                // Save the reading to the database
                savedReading = readingRepository.save(reading);
            }

            if(latestReading.size()==1){
                Reading previousReading = latestReading.get(0);
                Reading currentReading = latestReading.get(0);
                int currentMonthConsumption = currentReading.getUnitsConsumed();

                // Check if the units consumed is greater than or equal to consumption of current month
                if (reading.getUnitsConsumed() != null) {
                    if (reading.getUnitsConsumed() < currentMonthConsumption) {
                        throw new InvalidUnitsConsumedException("Units consumed cannot be less than the consumption of the current month: ");
                    }
                }

                // Check if the reading date is in the future
                if (reading.getReadingDate() != null && reading.getReadingDate().isAfter(LocalDate.now())) {
                    throw new InvalidReadingDateException("Reading date cannot be in the future: ");
                }

                // Check if the reading date is before or equal to the previous reading date
                if (previousReading != null && reading.getReadingDate() != null &&
                        reading.getReadingDate().isBefore(currentReading.getReadingDate())) {
                    throw new InvalidReadingDateException("Reading date must be after the previous reading date: ");
                }
                // Link the connection to the reading
                reading.setConnection(connection);
                // Save the reading to the database
                savedReading = readingRepository.save(reading);
            }
            if (latestReading.size() >1) {
                Reading previousReading = latestReading.get(0);
                Reading currentReading = latestReading.get(1);
//                System.out.println("Previous Reading is:"+ previousReading);
//                System.out.println("Current Reading is:"+ currentReading);
//                System.out.println("Latest reading of consumer number:" + latestReading);
                int currentMonthConsumption = currentReading.getUnitsConsumed();


                // Check if the units consumed is greater than or equal to consumption of current month
                if (reading.getUnitsConsumed() != null && reading.getUnitsConsumed() < currentMonthConsumption ) {
                    throw new InvalidUnitsConsumedException("Units consumed cannot be less than the consumption of the current month: ");
                }


                // Check if the reading date is in the future
                if (reading.getReadingDate() != null && reading.getReadingDate().isAfter(LocalDate.now())) {
                    throw new InvalidReadingDateException("Reading date cannot be in the future: ");
                }

                // Check if the reading date is before or equal to the previous reading date
                if (previousReading != null && reading.getReadingDate() != null &&
                        reading.getReadingDate().isBefore(currentReading.getReadingDate())) {
                    throw new InvalidReadingDateException("Reading date must be after the previous reading date: ");
                }
                // Link the connection to the reading
                reading.setConnection(connection);
                // Save the reading to the database
                savedReading = readingRepository.save(reading);
            }
            return savedReading;
        }
    }
    @Override
    public Optional<Reading> findMeterReadingByConsumerNumberAndBillDate (Long consumerNumber, LocalDate billDate){
        if (consumerNumber == null || billDate == null) {
            throw new IllegalArgumentException("consumerNumber and billDate must not be null");
        }

        //Retrieve the reading from reading repository.
        Optional<Reading> reading = Optional.ofNullable(readingRepository.findMeterReadingByConsumerNumberAndBillDate(consumerNumber, billDate));
        if (reading != null)
            return Optional.of(reading.get());
        else
            throw new ReadingNotFoundException("Reading not found for consumer number  + consumerNumber +  and bill date  + billDate");
    }

    @Override
    public List<Reading> findMeterReadingByConsumerNumber (Long consumerNumber){
        // Validate the input parameter
        if (consumerNumber == null || consumerNumber <= 0) {
            throw new IllegalArgumentException("Consumer number must be a positive non-null value.");
        }

        //Use repo to find readingby consumer number
        List<Reading> reading = readingRepository.findMeterReadingByConsumerNumber(consumerNumber);
        if (reading.isEmpty()) {
            throw new ReadingNotFoundException("No Reading not found with consumerNumber: " + consumerNumber);
        }
        return reading;
    }

    public Reading getReadingById (Long id){ //bill Id
        Optional<Reading> reading = readingRepository.findById(id);
        if (reading.isPresent()) {
            return reading.get();
        }
        throw new IdNotFoundException("Reading not found with id:" + id);
    }

    public List<Reading> getReadingByConsumerNumber (Long consumerNumber){
        List<Reading> reading = readingRepository.findLatestReadingByConsumerNumber(consumerNumber);
        System.out.println("READING :" + reading);
        if (reading != null) {
            return reading;
        }
        throw new ConsumerNumberNotFoundException("Reading not found with consumer number:" + consumerNumber);
    }
    public List<Reading> getAllReading () {
        List<Reading> readings = readingRepository.findAll();
        return readings;
    }

    public Reading updateReading (Long id, Reading reading){
        //Fetch the existing Reading by its ID.
        Optional<Reading> existingReading = readingRepository.findById(id);
        if (existingReading.isPresent()) {
            Reading existing = existingReading.get();

            // Update the properties of the existing Reading with the new values
            existing.setReadingDate(reading.getReadingDate());
            existing.setUnitsConsumed(reading.getUnitsConsumed());
            existing.setPricePerUnits(reading.getPricePerUnits());

            // Save the updated reading to repository.
            Reading updateReading = readingRepository.save(existing);
            return updateReading;
        } else {
            throw new IdNotFoundException("Reading not found with id:" + id);
        }
    }

    public void deleteReadingByReadingId (Long readingId){
        Optional<Reading> r = readingRepository.findById(readingId);
        if (r.isPresent()) {
            readingRepository.deleteById(readingId);
            System.out.println("Reading deleted successfully.");
        } else {
            throw new IdNotFoundException("Reading not found with id:" + readingId);
        }
    }

    public void deleteAllReadings () {
        readingRepository.deleteAll();
    }
}

