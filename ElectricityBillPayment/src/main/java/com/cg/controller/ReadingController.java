package com.cg.controller;

import com.cg.exceptions.ConsumerNumberNotFoundException;
import com.cg.exceptions.IdNotFoundException;
import com.cg.exceptions.ReadingNotFoundException;
import com.cg.exceptions.UnitsNotNegativeException;
import com.cg.model.Connection;
import com.cg.model.Reading;
import com.cg.repository.ConnectionRepository;
import com.cg.repository.ReadingRepository;
import com.cg.services.ReadingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController("readingController")
@RequestMapping("/readings")
public class ReadingController {
    @Autowired
    ReadingServiceImpl readingService;
    @Autowired
    ReadingRepository readingRepository;

    @Autowired
    ConnectionRepository connectionRepository;

    @PostMapping("/SubmitReading/{consumerNumber}")
    public ResponseEntity<Reading> selfSubmitReading(@RequestBody Reading reading, @PathVariable Long consumerNumber) {
        // Fetch the Connection entity associated with the consumerNumber
        Optional<Connection> optionalConnection = connectionRepository.findByConsumerNumber(consumerNumber);

        Connection connection = optionalConnection.orElseThrow(() ->
                new ConsumerNumberNotFoundException("Consumer with number " + consumerNumber + " not found."));

        // Set the fetched connection in the reading
        reading.setConnection(connection);

        // Save reading to database
        try {
            Reading savedReading = readingService.selfSubmitReading(reading, consumerNumber);
            return new ResponseEntity<>(savedReading, HttpStatus.OK);
        } catch (UnitsNotNegativeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    

//     @PostMapping("/SubmitReading/{consumerNumber}")
//    public ResponseEntity<Reading> selfSubmitReading(@RequestBody Reading reading, @PathVariable Long consumerNumber){
//
//         // Fetch the Connection entity associated with the consumerNumber
//         Connection connection = connectionRepository.findByConsumerNumber(consumerNumber);
//
//         // Set the fetched connection in the reading
//         reading.setConnection(connection);
//             // save reading to database
//             Reading savedReading=readingService.selfSubmitReading(reading, consumerNumber);
//             if(savedReading != null) {
//                 return new ResponseEntity<>(savedReading, HttpStatus.OK);
//             } else {
//                 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//         }
//
//     }


     @GetMapping("/getReadingById/{readingId}")
     public Reading getReadingById(@PathVariable Long readingId){
         Optional<Reading> reading= Optional.ofNullable(readingService.getReadingById(readingId));
         if(reading.isPresent()){
             return reading.get();
         }
         else {
             throw new IdNotFoundException("Reading Not found with Id: " +readingId);
         }
     }

     @GetMapping("/getReadingByConsumerNumber/{consumerNumber}")
     public List<Reading> getReadingByConsumerNumber(@PathVariable Long consumerNumber){
         List<Reading> readings=readingService.getReadingByConsumerNumber(consumerNumber);
         if(readings.isEmpty()){
             throw new ConsumerNumberNotFoundException("Reading not found with given consumerNumber: " +consumerNumber);
         }
         else {
             return readings;
         }
     }
     @GetMapping("/getAllReading")
     public List<Reading> getAllReading(){
         List<Reading> readings=readingService.getAllReading();
         return readings;
     }
    @PutMapping("/updateReading/{id}")
     public ResponseEntity<Reading> updateReading(@RequestBody Reading reading,
                                                  @PathVariable Long id){
      try {
          //Fetch the existing Reading by its ID.
          Optional<Reading> existingReading= Optional.ofNullable(readingService.getReadingById(id));

              if(existingReading.isPresent()){
                  Reading existing = existingReading.get();

                  // Update the properties of the existing Reading with the new values
                  existing.setReadingDate(reading.getReadingDate());
                  existing.setUnitsConsumed(reading.getUnitsConsumed());
                  existing.setPricePerUnits(reading.getPricePerUnits());

                  // Save the updated reading to service.
                  Reading updateReading=readingService.updateReading(id,reading);
                  return ResponseEntity.ok(updateReading);
              }
              else {
                  throw new IdNotFoundException("Reading not found with id:" +id);
              }
      }catch(IdNotFoundException e){
          return new  ResponseEntity<> (HttpStatus.NOT_FOUND);
      }
     }
     @DeleteMapping("/deleteReadingByReadingId/{readingId}")
     public ResponseEntity<String> deleteReadingByReadingId(@PathVariable Long readingId){
         Optional<Reading> reading= Optional.ofNullable(readingService.getReadingById(readingId));
         if(reading.isPresent()){
             readingService.deleteReadingByReadingId(readingId);
             return new ResponseEntity<String>("Reading record deleted successfully:",HttpStatus.OK);
         }
         else{
             return new ResponseEntity<String>("Reading not found for given id: ",HttpStatus.NOT_FOUND);
         }
     }

     @DeleteMapping("/deleteAllReadings")
     public ResponseEntity<String> deleteAllReadings(){
         readingService.deleteAllReadings();
         return new ResponseEntity<String>("All reading deleted successfully:",HttpStatus.OK);
     }

     @GetMapping("/findMeterReadingByConsumerNumberAndBillDate/{ConsumerNumber}/{BillDate}")
     public ResponseEntity<Reading> findMeterReadingByConsumerNumberAndBillDate(@RequestParam  Long consumerNumber,
                                                                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate billDate){
         try {
             Optional<Reading> reading = readingService.findMeterReadingByConsumerNumberAndBillDate(consumerNumber, billDate);

             if (reading.isPresent()) {
                 Reading r=reading.get();
                 return new ResponseEntity<>(r,HttpStatus.OK);
             } else {
                 // Return a 404 Not Found response with a custom error message
                 return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
             }
         } catch (IllegalArgumentException e) {
             // Handle IllegalArgumentException and return a bad request response
             return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
         } catch (ReadingNotFoundException e) {
             // Handle ReadingNotFoundException and return a 404 response
             return new ResponseEntity<> (HttpStatus.NOT_FOUND);
         } catch (Exception e) {
             // Handle other exceptions and return an internal server error response
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
         }
     }

     @GetMapping("/findMeterReadingByConsumerNumber/{consumerNumber}")
     public ResponseEntity<List<Reading>> findMeterReadingByConsumerNumber(@PathVariable Long consumerNumber){
         try {
             if (consumerNumber == null || consumerNumber <= 0) {
                 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
             }
             List<Reading> readings=  readingService.getReadingByConsumerNumber(consumerNumber);
             if(readings.isEmpty()){
                 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
             }
             else {
                 return new ResponseEntity<>(readings,HttpStatus.OK);
             }
         }catch (IllegalArgumentException e){

             // Handle IllegalArgumentException and return a bad request response
             return new  ResponseEntity<>(HttpStatus.BAD_REQUEST);

         }catch (ReadingNotFoundException e){
             //  Handle ReadingNotFoundException and return a 404 response.
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
     }

}
