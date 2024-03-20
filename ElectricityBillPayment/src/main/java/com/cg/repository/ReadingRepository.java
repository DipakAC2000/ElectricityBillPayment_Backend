package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.model.Reading;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long> {
    //r.bill.connection.consumerNumber = :consumerNumber: This part of the query is specifying that you want to find a Reading entity (r) where:
    //r.bill refers to the associated Bill entity of the Reading.
    //r.bill.connection refers to the associated Connection entity of the Bill.
    //r.bill.connection.consumerNumber refers to the consumerNumber property within the associated Connection.

    // r.bill.billDate = :billDate: This part of the query is specifying another condition where:
    //  r.bill.billDate refers to the billDate property within the associated Bill.
    @Query("SELECT r FROM Reading r " +
            "WHERE r.bill.connection.consumerNumber = :consumerNumber " +
            "AND r.bill.billDate = :billDate")
    Reading findMeterReadingByConsumerNumberAndBillDate(@Param("consumerNumber") Long consumerNumber,
                                                        @Param("billDate") LocalDate billDate);
    @Query("SELECT r FROM Reading r " +
            "JOIN r.connection c " +
            "WHERE c.consumerNumber = :consumerNumber ")
    List<Reading> findMeterReadingByConsumerNumber(@Param("consumerNumber") Long consumerNumber);
    @Query("SELECT r FROM Reading r " +
            "JOIN r.connection c " +
            "WHERE c.consumerNumber = :consumerNumber " +
            "ORDER BY r.readingDate DESC")
    List<Reading> findLatestReadingByConsumerNumber(@Param("consumerNumber") Long consumerNumber);

//    @Query("SELECT r FROM Reading r " +
//            "JOIN r.connection c " +
//            "WHERE c.consumerNumber = :consumerNumber " +
//            "AND r.readingDate < (SELECT MAX(r2.readingDate) FROM Reading r2 JOIN r2.connection c2 WHERE c2.consumerNumber = :consumerNumber) " +
//            "ORDER BY r.readingDate DESC")
//    List<Reading> findPreviousReadingByConsumerNumber(Long consumerNumber);

}
