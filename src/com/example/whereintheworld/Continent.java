package com.example.whereintheworld;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class Continent {
	private int continentId;
	private String continentName;
	private LatLng latLong;
	

	
	
	
	public Continent(int continentId, String continentName, LatLng latLong) {
		super();
		this.continentId = continentId;
		this.continentName = continentName;
		this.latLong = latLong;
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
	 * @return the continentName
	 */
	public String getContinentName() {
		return continentName;
	}
	/**
	 * @param continentName the continentName to set
	 */
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getContinentName();
	}
	
	
}
