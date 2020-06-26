package io.javabrains.coronavirustracker.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/*Step 2:
 * Create LocationStatistics object and define class members and methods
 *  Will use 'ToStringBuilder' method from 'org.apache.commons.lang3.builder.' package
 *  to help display object value rather than hex location value
 */
public class LocationStatistics {

	private String state;
	private String country;
	private int latestTotalCases;
	private int diffFromPrevDay;
	
	
	public int getDiffFromPrevDay() {
		return diffFromPrevDay;
	}

	public void setDiffFromPrevDay(int diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
	}

	//@Overrride .toString method to return object value rather than hex digit associated with memory location
	@Override 
	public String toString() {
	     return new ToStringBuilder(this).
	       append("state", state).
	       append("country",country).
	       append(0).
	       toString();
	   }
	 
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the latestTotalCases
	 */
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	/**
	 * @param latestTotalCases the latestTotalCases to set
	 */
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	
	
}
