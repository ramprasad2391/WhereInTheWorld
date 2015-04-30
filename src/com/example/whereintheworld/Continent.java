package com.example.whereintheworld;

public class Continent {
	private int continentId;
	private String continentName;
	

	public Continent(int continentId, String continentName) {
		super();
		this.continentId = continentId;
		this.continentName = continentName;
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
