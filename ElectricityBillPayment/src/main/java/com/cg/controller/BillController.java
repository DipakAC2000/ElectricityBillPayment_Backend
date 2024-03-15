package com.cg.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cg.exceptions.BillNotFoundException;
import com.cg.exceptions.BillServiceException;
import com.cg.exceptions.ConsumerNumberNotFoundException;
import com.cg.repository.BillRepository;
import com.cg.repository.ConnectionRepository;
import com.cg.repository.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cg.model.Bill;
import com.cg.services.BillServiceImpl;


@RestController("billController")
@RequestMapping("/bill")
public class BillController {
	
	@Autowired
	private BillServiceImpl billService;

	@Autowired
	private ConnectionRepository connectionRepository;
	@Autowired
	private ReadingRepository readingRepository;
	@Autowired
	private BillRepository billRepository;
	/*
			* The @RequestBody annotation in Spring is used to indicate that a method parameter should be
			bound to the body of the HTTP request.
			* It tells spring that take Http request body and convert it into java object of a specified type,
			int this case it is a type of 'Bill' object.
			* When you send a POST request to this endpoint with a JSON body representing a Bill, Spring will
			automatically convert that JSON into a Bill object and make it available for you to work with in your method.
			 */

	        /*
			  * The @RequestParam annotation in Spring is used to extract values from the query parameters or
			     from data in an HTTP request.
			  * It allows you to retrieve specific parameters from the request URL or the request body.
			 */

	@GetMapping("/generateBill/{consumerNumber}")
	public ResponseEntity<Bill> generateBill(@PathVariable("consumerNumber") Long consumerNumber) {
		try {
			Bill generatedBill = billService.generateBill(consumerNumber);

			return new ResponseEntity<>(generatedBill, HttpStatus.OK);
		} catch (ConsumerNumberNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}



	@GetMapping("/getBillById/{id}")
   public ResponseEntity<Bill> getBillById(@PathVariable Long id){

	   Optional<Bill> b= billService.getBillById(id);
	   if(b.isPresent()){
		   return new ResponseEntity(b,HttpStatus.OK);
	   }
	   else {
		   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	   }
   }
     @GetMapping("/getBillByReadingId/{readingId}")
   public ResponseEntity<Bill> getBillByReadingId(@PathVariable long readingId){
	   Optional<Bill> b= Optional.ofNullable(billService.getBillByReadingId(readingId));
	   if(b.isPresent()){
		   return new ResponseEntity(b,HttpStatus.OK);
	   }
	   else {
		   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	   }
   }
     @GetMapping("/getAllBill")
   public List<Bill> getAllBills(){
	   List<Bill>b= billService.getAllBills();
	   return b;
	 }

	 /*
    @PutMapping("/updateBill")
   public ResponseEntity<Bill> updateBill(@RequestBody Bill bill,@RequestParam Long billId){
	   Bill updatedBill=billService.updateBill(bill,billId);
	   return new ResponseEntity(updatedBill,HttpStatus.OK);
   }
	  */

     @DeleteMapping("/deleteBillById/{id}")
	public ResponseEntity<String> deleteBillById(@PathVariable Long billId){
	  Optional<Bill> bill=billService.getBillById(billId);
	  if(bill.isPresent()) {
		  billService.deleteById(billId);
		  return new ResponseEntity<String>("Bill record deleted successfully:" + billId, HttpStatus.OK);
	  }
	  else {
		  return new ResponseEntity<String>("Bill Not found with Id:",HttpStatus.NOT_FOUND);
	  }
   }
    @DeleteMapping("/deleteAllBills")
   public ResponseEntity<String> deleteAllBills(){
	   billService.deleteAllBill();
	   return new ResponseEntity<String>("All bill record deleted successfully",HttpStatus.OK);
   }
    @GetMapping("/viewBillByConsumerNumber/{consumerNumber}")
   public ResponseEntity <Bill> viewBillByConsumerNumber(@PathVariable Long consumerNumber){
	   try {
		   //Call the service method to rertieve bill by consumer number.
	   Bill bill=billService.viewBillByConsumerNumber(consumerNumber);
	   if(bill!=null){
		   return new ResponseEntity<>(bill,HttpStatus.FOUND);
	   }
	   else {
		   throw new ConsumerNumberNotFoundException("No bill found for consumerNumber:" +consumerNumber);
	   }
	   }catch (BillServiceException e){
		   return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	   }
   }
    @GetMapping("/viewBillByMobileNumber/{mobNo}")
   public ResponseEntity<Bill> viewBillByMobileNumber(@PathVariable String mobNo){
	   Bill bill=billService.viewBillByMobileNumber(mobNo);
	   if(bill!=null){
		   return new ResponseEntity<>(bill,HttpStatus.FOUND);
	   }
	   else {
		   throw new BillNotFoundException("No bill found for this mobile no:" +mobNo);
	   }
   }
    @GetMapping("/viewBillByEmail/{email}")
   public ResponseEntity<Bill> viewBillByEmail(@PathVariable String email){
	   Bill bill=billService.viewBillByEmail(email);
	   if(bill!=null){
		   return new ResponseEntity<>(bill,HttpStatus.FOUND);
	   }
	   else {
		   throw new BillNotFoundException("No bill found for this email:" +email);
	   }
   }
    @GetMapping("/viewBillForDateRange/{startDate}/{endDate}")
   public ResponseEntity<List<Bill>> viewBillForDateRange(
		   @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		   @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){

	   List<Bill> bills=billService.viewBillForDateRange(startDate, endDate);
	   if (bills!=null){
		   System.out.println("Bill found");
		   return new ResponseEntity<>(bills,HttpStatus.OK);
	   }
	   else {
		   System.out.println("Bill not found");
		   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	   }
   }

}
	




