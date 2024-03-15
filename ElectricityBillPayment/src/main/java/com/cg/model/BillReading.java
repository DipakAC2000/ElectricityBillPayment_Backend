package com.cg.model;

import jakarta.persistence.*;

@Entity
@Table(name="BillReading")
public class BillReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BillReadingId;
    @Column(nullable = false)
    private Long custId;
    @Column(nullable = false)
    private Integer previousReading;
    @Column(nullable = false)
    private Integer previousPayment;

    public BillReading(){
           super();
    }

//    public BillReading(Integer previousReading, Integer previousPayment, Long custId) {
//    }

    public Long getBillReadingId() {
        return BillReadingId;
    }

    public BillReading(Long billReadingId, Long custId, Integer previousReading, Integer previousPayment) {
        BillReadingId = billReadingId;
        this.custId = custId;
        this.previousReading = previousReading;
        this.previousPayment = previousPayment;
    }

    public void setBillReadingId(Long billReadingId) {
        BillReadingId = billReadingId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Integer getPreviousReading() {
        return previousReading;
    }

    public void setPreviousReading(Integer previousReading) {
        this.previousReading = previousReading;
    }

    public Integer getPreviousPayment() {
        return previousPayment;
    }

    public void setPreviousPayment(Integer previousPayment) {
        this.previousPayment = previousPayment;
    }

    @Override
    public String toString() {
        return "BillReading{" +
                "BillReadingId=" + BillReadingId +
                ", custId=" + custId +
                ", previousReading=" + previousReading +
                ", previousPayment=" + previousPayment +
                '}';
    }
}
