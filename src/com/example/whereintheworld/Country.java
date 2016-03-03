package com.example.whereintheworld;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class Country {
	private int continentId;
	private int countryId;
	private String countryName;
	private LatLng latLong;
	
	
	public Country(int continentId, int countryId, String countryName) {
		super();
		this.continentId = continentId;
		this.countryId = countryId;
		this.countryName = countryName;
	}
	
	
	/**
	 * @return the latLong
	 */
	public LatLng getLatLong() {
		return latLong;
	}


	/**
	 * @param latLong the latLong to set
	 */
	public void setLatLong(LatLng latLong) {
		this.latLong = latLong;
	}


	/**
	 * @return the continentId
	 */
	public int getContinentId() {
		return continentId;
	}
	/**
	 * @param continentId the continentId to set
	 */
	public void setContinentId(int continentId) {
		this.continentId = continentId;
	}
	/**
	 * @return the countryId
	 */
	public int getCountryId() {
		return countryId;
	}
	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}
	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getCountryName();
	}
	
	

}
