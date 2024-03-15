package com.cg.services;

import com.cg.model.BillReading;

public interface BillReadingService {

	public abstract BillReading createBillReading(Integer previousReading, Integer previousPayment, Long custId);

}

