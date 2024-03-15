package com.cg.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cg.model.Reading;

public interface ReadingService {


    Reading selfSubmitReading(Reading reading,Long consumerNumber);

    public Optional<Reading> findMeterReadingByConsumerNumberAndBillDate(Long consumerNumber, LocalDate billDate);
	
	public List<Reading> findMeterReadingByConsumerNumber(Long consumerNumber);
	
}
