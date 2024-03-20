package com.cg.services;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cg.exceptions.*;
import com.cg.model.Connection;
import com.cg.model.Reading;
import com.cg.repository.ConnectionRepository;
import com.cg.repository.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.cg.model.Bill;
import com.cg.repository.BillRepository;

@Service("billService")
public class BillServiceImpl {

	@Autowired // Inject the billRepository using @Autowired
	private BillRepository billRepository;

	@Autowired
	private ReadingRepository readingRepository;
	@Autowired
	private ReadingServiceImpl readingService;
	@Autowired
	private ConnectionRepository connectionRepository;

	public Bill viewBillByConsumerNumber(Long consumerNumber) {
		try{
			// Call the repository method to retrieve the bill by consumer number
			Bill bill=billRepository.readBillByConsumerNumber(consumerNumber);

			//If bill found for given id
			if(bill!=null){
				return bill; //Return bill
			}
			else {
				// Handle the case where no bill was found for the provided consumer number
				throw new ConsumerNumberNotFoundException("No bill found for given Consumer number " +consumerNumber);
			}

		}catch(DataAccessException e){
			// Handle any data access-related exceptions
			throw new BillServiceException("Error while fetching bill by consumer number" +e);
		}
	}

	public Bill viewBillByMobileNumber(String mobNo) {
		Bill bill = billRepository.readBillByMobileNumber(mobNo);
		if (bill != null) {
			return bill;
		}
		else {
			throw new BillNotFoundException("No Bill found with given mobile no. " +mobNo);
		}
	}


	public Bill viewBillByEmail(String email){
		Bill bill= billRepository.readBillByEmail(email);
		if(bill!= null){
			return bill;
		}
		else {
			throw new BillNotFoundException("No bill found for given email." +email);
		}
	}

	public List<Bill> viewBillForDateRange(LocalDate startDate,LocalDate endDate){
		List<Bill> b= billRepository.readBillForDateRange(startDate,endDate);
		if(!b.isEmpty()) {
			return b;
		}else {
			throw new BillNotFoundException("No bill found for given date of range:" +startDate +"--" +endDate);
		}
	}



	public Bill generateBill(Long consumerNumber) {
		// Fetch the associated Connection entity by consumerNumber
		Optional<Connection> optionalConnection = connectionRepository.findByConsumerNumber(consumerNumber);

		Connection connection = optionalConnection.orElseThrow(() ->
				new ConsumerNumberNotFoundException("Connection not found for consumer number: " + consumerNumber));

		// Fetch the latest reading for the given consumerNumber from the repository
		List<Reading> latestReading = readingRepository.findLatestReadingByConsumerNumber(consumerNumber);
		//System.out.println("...." + latestReading.get(0));

		Duration duration=Duration.ZERO;
		long days;

		if (!latestReading.isEmpty()) {
			// Calculate the bill based on the latest and previous readings
			Bill bill = calculateBill(latestReading);

			if (latestReading.size() > 1) {
				int unitsConsumed = latestReading.get(0).getUnitsConsumed() - latestReading.get(1).getUnitsConsumed();
				bill.setUnitsConsumed(unitsConsumed);
				// Set the Connection entity with the correct consumerNumber
				bill.setConnection(connection);

				//Calculate and set BillDuration
				duration = Duration.between(latestReading.get(1).getReadingDate().atStartOfDay(), latestReading.get(0).getReadingDate().atStartOfDay());
				days=duration.toDays();
				bill.setBillDuration(days);


			} else {
				// If there is only one latest reading, set consumption to the units consumed in that reading
				int unitsConsumed = latestReading.get(0).getUnitsConsumed();
				bill.setUnitsConsumed(unitsConsumed);
				// Set the Connection entity with the correct consumerNumber
				bill.setConnection(connection);

				Connection connection1=bill.getConnection();
				//System.out.println("Inside else if");
				if (connection1 != null) {
					// Handle the case where there are not enough readings
					LocalDate connectionDate = connection.getConnectionDate();
					duration = Duration.between(connectionDate.atStartOfDay(), latestReading.get(0).getReadingDate().atStartOfDay());
					days= duration.toDays();
					bill.setBillDuration(days);
				}
				else{
					System.out.println("Connection is null. can't calculate duration :");
					duration=Duration.ZERO;
				}
			}
			// Save the generated bill to the repository
			return billRepository.save(bill);
		} else {
			throw new ReadingNotFoundException(" Reading not found for consumer number :");
		}
	}
	private Bill calculateBill(List<Reading> latestReading) {
		int consumption=0;
		if(latestReading.size()>1) {
			consumption = latestReading.get(0).getUnitsConsumed() - latestReading.get(1).getUnitsConsumed();
		}
		else {
			consumption=latestReading.get(0).getUnitsConsumed();
		}

		if (consumption < 0) {
			throw new UnitsNotNegativeException("Units consumed must be greater than zero");
		}

		// Calculate charges based on consumption (adjust the rate as needed)
		double chargesPerUnit = 10;

		double totalCharges = consumption * chargesPerUnit;

		// Calculate taxes (e.g., tax rate 8%)
		double taxRate = 0.08;
		double tax = totalCharges * taxRate;

		// Calculate the total bill amount
		double totalAmount = totalCharges + tax;

		Bill bill = new Bill();
		bill.setBillDate(LocalDate.now());
		bill.setBillDueDate(bill.getBillDate().plusDays(30));
		bill.setBillAmount((int) totalAmount); // Convert total amount to an integer if needed
		bill.setPaid(false);

		// Set the latest reading for the bill
		bill.setReading(latestReading.get(0));

		// Calculate the duration between the latest and previous reading dates
		Duration duration = Duration.ZERO;
		long days;

		if (latestReading.size() > 1) {
			duration = Duration.between(latestReading.get(0).getReadingDate().atStartOfDay(), latestReading.get(1).getReadingDate().atStartOfDay());
			System.out.println("LatestReading"+latestReading);

		}

		else if(latestReading.size()==1){
			Connection connection=bill.getConnection();
			System.out.println("Inside else if");
			if (connection != null) {
				// Handle the case where there are not enough readings
				LocalDate connectionDate = connection.getConnectionDate();
				System.out.println("Connection Date: " + connectionDate);
				System.out.println("Latest Reading Date: " + latestReading.get(0).getReadingDate());
				duration = Duration.between(connectionDate.atStartOfDay(), latestReading.get(0).getReadingDate().atStartOfDay());
			}
			else{
				System.out.println("Connection is null. can't calculate duration :");
				duration=Duration.ZERO;
			}
		}

		days = duration.toDays();
		bill.setBillDuration(days );
		System.out.println("Calculated Bill Duration: " + days);
		return bill;
	}


	public Optional<Bill> getBillById(Long id){
		Optional<Bill> b= billRepository.findById(id);
		if(b.isPresent()){
			return b;
		}
		else {
			throw new IdNotFoundException("Bill not found with given id:" +id);
		}

	}

	public Bill getBillByReadingId(Long readingId){
		Bill b= billRepository.getBillByReadingId(readingId);
		if(b!=null){
			return b;
		}
		else {
			throw new IdNotFoundException("No bill associated with given reading id");
		}
	}

	public List<Bill> getAllBills(){
		return billRepository.findAll();
	}

	/* Has access to admin only.
	public Bill updateBill(Bill bill,Long billId){
		// Check if the Bill with the provided ID exists in the database
       Optional<Bill> existingBill=billRepository.findById(billId);
       if(existingBill.isPresent()){
		   // Update the properties of the existing Bill with the provided data
		   Bill updatedBill= existingBill.get();
          updatedBill.setBillDate(existingBill.get().getBillDate());
		  updatedBill.setBillDueDate(existingBill.get().getBillDueDate());
		//  updatedBill.setUnitsConsumed(existingBill.get().getUnitsConsumed());
		  updatedBill.setBillAmount(existingBill.get().getBillAmount());

		  return billRepository.save(updatedBill);
	   }
	   else {
		   throw new BillNotFoundException("Bill with ID " + billId + " not found.");
	   }

	}
	 */

	public void  deleteById(Long id){
		Optional<Bill> b=billRepository.findById(id);
		if(b.isPresent()){
			billRepository.deleteById(id);
			System.out.println("Bill deleted successfully.");
		}
		else {
			throw new BillNotFoundException("No bill found for given id: " +id);
		}


	}

	public void deleteAllBill(){
		billRepository.deleteAll();
	}

}
