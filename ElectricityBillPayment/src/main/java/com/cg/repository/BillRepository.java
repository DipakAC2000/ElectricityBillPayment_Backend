package com.cg.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.model.Bill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface BillRepository extends JpaRepository<Bill, Long>{

    /* This query retrieves a Bill entity from the database.
       It joins the Bill entity with the Reading, Connection, and Customer entities using inner joins.
       The selection condition is based on the mobileNumber attribute of the Customer entity.
    */
    @Query("SELECT b FROM Bill b " +
            "JOIN b.reading r " +
            "JOIN r.connection c " +
            "JOIN c.customer cu " +
            "WHERE cu.mobileNumber = :mobileNumber")
    Bill readBillByMobileNumber(@Param("mobileNumber") String mobileNumber);


   /* This query is results based on the email attribute of the Customer entity.
    It retrieves a Bill entity from the database.
    It joins the Bill entity with the Reading, Connection, and Customer entities using inner joins.
     */
   @Query("SELECT b FROM Bill b " +
         "JOIN b.reading r " +
         "JOIN r.connection c " +
        "JOIN c.customer cu " +
        "WHERE cu.email = :email")
    Bill readBillByEmail(@Param("email") String email);


     /*
     This query retrieves a list of Bill entities from the database.
     It selects Bill entities where the billDate attribute falls within a specified date range.
      The :start and :end parameters are used to define the start and end dates of the date range.
      */
     @Query("SELECT b FROM Bill b WHERE b.billDate BETWEEN :start AND :end")
    List<Bill> readBillForDateRange(@Param("start") LocalDate startDate,
            @Param("end") LocalDate endDate);



    /*
       This query retrieves a Bill entity from the database.
       It joins the Bill entity with the Reading and Connection entities using inner joins.
       The selection condition is based on the consumerNumber attribute of the Connection entity.
    */
    @Query("SELECT b FROM Bill b " +
    "JOIN b.reading r " +
    "JOIN r.connection c " +
    "WHERE c.consumerNumber = :consumerNumber")
    Bill readBillByConsumerNumber(@Param("consumerNumber") Long consumerNumber);

    @Query("SELECT b FROM Bill b WHERE b.reading.id = :readingId")
    Bill getBillByReadingId(Long readingId);
}
