package com.cg.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="Reading")
public class Reading {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long readingId;

	@Column(nullable = false)
	private Integer unitsConsumed;

	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate readingDate;

	@Column(nullable = false)
	private Integer pricePerUnits;

	@OneToOne(mappedBy = "reading")
	@JsonIgnore
	private Bill bill;

	@ManyToOne// No mappedBy here, as this entity is the "owning" side of the relationship.
	@JsonBackReference
	@JoinColumn(name = "consumerNumber") // Name of the foreign key column
	private Connection connection;


	public Reading() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reading(Long readingId, Integer unitsConsumed, LocalDate readingDate, Integer pricePerUnits, Bill bill, Connection connection) {
		this.readingId = readingId;
		this.unitsConsumed = unitsConsumed;
		this.readingDate = readingDate;
		this.pricePerUnits = pricePerUnits;
		this.bill = bill;
		this.connection = connection;
	}

	public Long getReadingId() {
		return readingId;
	}

	public Integer getUnitsConsumed() {
		return unitsConsumed;
	}

	public void setUnitsConsumed(Integer unitsConsumed) {
		this.unitsConsumed = unitsConsumed;
	}

	public LocalDate getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(LocalDate readingDate) {
		this.readingDate = readingDate;
	}

	public Integer getPricePerUnits() {
		return pricePerUnits;
	}

	public void setPricePerUnits(Integer pricePerUnits) {
		this.pricePerUnits = pricePerUnits;
	}


	public Connection getConnection() {
		return connection;
	}

	public void setConnection (Connection connection) {
		this.connection = connection;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	@Override
	public String toString() {
		return "Reading{" +
				"readingId=" + readingId +
				", unitsConsumed=" + unitsConsumed +
				", readingDate=" + readingDate +
				", pricePerUnits=" + pricePerUnits +
				//", bill=" + bill +
				//", connection=" + connection +
				'}';
	}
}
