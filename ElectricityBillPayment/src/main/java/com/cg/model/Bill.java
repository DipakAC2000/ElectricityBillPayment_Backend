package com.cg.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Bill")
public class Bill {
	@Column( columnDefinition = "TINYINT(1)")
	public Boolean isPaid;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long billId;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private LocalDate billDate;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private LocalDate billDueDate;

	@Column(nullable = false)
	private Integer unitsConsumed;


	@Column(nullable = false)
	private Integer billAmount;

	@Column(nullable = false)
	private long billDuration;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "reading_id")
	@JsonIgnore
	private Reading reading;
	@ManyToOne
	@JoinColumn(name = "consumer_number") // Adjust column name as per your schema
	private Connection connection;
	@OneToOne(mappedBy = "bill", cascade = CascadeType.ALL)
	private Payment payment;




	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bill(Long billId, LocalDate billDate, LocalDate billDueDate, Integer unitsConsumed, Integer billAmount,long billDuration,Boolean isPaid, Reading reading, Connection connection) {
		this.billId = billId;
		this.billDate = billDate;
		this.billDueDate = billDueDate;
		this.unitsConsumed = unitsConsumed;
		this.billAmount = billAmount;
		this.billDuration=billDuration;
		this.isPaid=isPaid;
		this.reading = reading;
		this.connection = connection;
	}

	public Boolean getPaid() {
		return isPaid;
	}
	public void setPaid(Boolean paid) {
		isPaid = paid;
	}

	public Long getBillId() {
		return billId;
	}

	public LocalDate getBillDate() {
		return billDate;
	}

	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}

	public LocalDate getBillDueDate() {
		return billDueDate;
	}

	public Integer getUnitsConsumed() {
		return unitsConsumed;
	}

	public void setUnitsConsumed(Integer unitsConsumed) {
		this.unitsConsumed = unitsConsumed;
	}

	public void setBillDueDate(LocalDate billDueDate) {
		this.billDueDate = billDueDate;
	}

	public Integer getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Integer billAmount) {
		this.billAmount = billAmount;
	}

	public long getBillDuration() {
		return billDuration;
	}

	public void setBillDuration(long billDuration) {
		this.billDuration = billDuration;
	}

	public Reading getReading() {
		return reading;
	}

	public void setReading(Reading reading) {
		this.reading = reading;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}