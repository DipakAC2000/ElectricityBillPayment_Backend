package com.cg.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Connection {
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGen")
    @SequenceGenerator(name = "mySeqGen", sequenceName = "mySeq", initialValue = 1000000000, allocationSize = 1)
    private Long consumerNumber;

	@DateTimeFormat(pattern = "yyy-MM-dd")
	private LocalDate applicationDate = LocalDate.now();
	
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private LocalDate connectionDate;
	
	private Boolean connectionStatus=false;
	
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private ConnectionType connectionType;
	
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private ConnectionPhaseType phaseType; 
	
	@OneToOne(cascade = CascadeType.ALL)
	private Customer customer;
	
	@OneToOne(cascade = CascadeType.ALL)
    private Address address;

	@OneToMany(mappedBy = "connection", cascade = CascadeType.ALL) // Refers to the field name in Reading that maps this relationship
	@JsonManagedReference
	private List<Reading> readings;
	
	public Connection() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Connection(Long consumerNumber, LocalDate applicationDate, LocalDate connectionDate,
			Boolean connectionStatus, ConnectionType connectionType, ConnectionPhaseType phaseType, Customer customer,
			Address address, List<Reading> readings) {
		super();
		this.consumerNumber = consumerNumber;
		this.applicationDate = applicationDate;
		this.connectionDate = connectionDate;
		this.connectionStatus = connectionStatus;
		this.connectionType = connectionType;
		this.phaseType = phaseType;
		this.customer = customer;
		this.address = address;
		this.readings = readings;
	}


	public Long getConsumerNumber() {
		return consumerNumber;
	}

	public LocalDate getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(LocalDate applicationDate) {
		this.applicationDate = applicationDate;
	}

	public LocalDate getConnectionDate() {
		return connectionDate;
	}

	public void setConnectionDate(LocalDate connectionDate) {
		this.connectionDate = connectionDate;
	}

	public boolean isConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(boolean connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public ConnectionType getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}
	
	public ConnectionPhaseType getPhaseType() {
		return phaseType;
	}

	public void setPhaseType(ConnectionPhaseType phaseType) {
		this.phaseType = phaseType;
	}

	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


	public List<Reading> getReadings() {
		return readings;
	}


	public void setReadings(List<Reading> readings) {
		this.readings = readings;
	}

}
