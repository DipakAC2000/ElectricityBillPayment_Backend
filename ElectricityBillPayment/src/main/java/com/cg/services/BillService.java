package com.cg.services;

import java.time.LocalDate;
import java.util.List;

import com.cg.model.Bill;
public interface BillService {

	public abstract Bill viewBillByConsumerNumber(Long consumerNumber);
	public abstract Bill viewBillByMobileNumber(String mobileNumber);
	public abstract Bill viewBillByEmail(String email);

	public abstract List<Bill> viewBillForDateRange(LocalDate startDate, LocalDate endDate);

	//public abstract double energyBillCalculator(connectionType connectionT, Int unitsConsumed,int pricePerUnit );
	//needs association mapping with connection module.

	public abstract void emailBillToCustomer(String email,Long consumerNumber);

}
