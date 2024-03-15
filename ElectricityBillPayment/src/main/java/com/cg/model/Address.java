package com.cg.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {

      @Id	
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long addressId;
	  
	  private Integer flatOrHouseNumber;
	  
	  private String buildingName;
	  
	  private String landmark;
	  
	  private String village;
	  
	  private String taluka;
	  
	  private String district;
	  
	  private String state;
	  
	  private String pincode;
	  
	  
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	 public Address(Long addressId, Integer flatOrHouseNumber, String buildingName, String landmark, String village,
				String taluka, String district, String state, String pincode) {
			super();
			this.addressId = addressId;
			this.flatOrHouseNumber = flatOrHouseNumber;
			this.buildingName = buildingName;
			this.landmark = landmark;
			this.village = village;
			this.taluka = taluka;
			this.district = district;
			this.state = state;
			this.pincode = pincode;
			
		}


	public Long getAddressId() {
		return addressId;
	}

	public Integer getFlatOrHouseNumber() {
		return flatOrHouseNumber;
	}

	public void setFlatOrHouseNumber(Integer flatOrHouseNumber) {
		this.flatOrHouseNumber = flatOrHouseNumber;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getTaluka() {
		return taluka;
	}

	public void setTaluka(String taluka) {
		this.taluka = taluka;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
  
}
