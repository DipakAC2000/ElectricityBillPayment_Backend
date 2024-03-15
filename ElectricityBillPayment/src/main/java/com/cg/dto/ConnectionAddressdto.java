package com.cg.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.cg.model.Address;
import com.cg.model.ConnectionPhaseType;
import com.cg.model.ConnectionType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ConnectionAddressdto {
     
	@Enumerated(EnumType.ORDINAL)
	private ConnectionType connectionType;
	
	@Enumerated(EnumType.ORDINAL)
	private ConnectionPhaseType phaseType;
	
	private Address address;
	
	public ConnectionAddressdto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConnectionAddressdto(ConnectionType connectionType, ConnectionPhaseType phaseType, Address address) {
		super();
		this.connectionType = connectionType;
		this.phaseType = phaseType;
		this.address = address;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	
}
