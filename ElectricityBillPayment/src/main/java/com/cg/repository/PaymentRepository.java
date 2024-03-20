package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.model.Payment;

import java.util.List;

@Repository("paymentRepository")
public interface PaymentRepository extends JpaRepository<Payment, String>{

    @Query("SELECT p FROM Payment p WHERE p.bill.id = :billId")
    Payment getPaymentByBillId(Long billId);

    @Query("SELECT p FROM Payment p " +
            "JOIN p.bill b " +
            "JOIN b.reading r " +
            "JOIN r.connection c " +
            "JOIN c.customer cu " +
            "JOIN cu.user u " +
            "WHERE u.id = :userId")
    List<Payment> findPaymentByUserId(@Param("userId") Long userId);
}
