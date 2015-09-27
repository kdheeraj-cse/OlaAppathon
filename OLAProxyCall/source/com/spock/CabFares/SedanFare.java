package com.spock.CabFares;

import com.google.gson.Gson;

public class SedanFare {
	private String type;// "flat_rate"
	private String minimum_distance;//: "4"
	private String minimum_time;//: "10"
	private String base_fare;//: "100.0"
	private String cost_per_distance;//: "13"
	private String waiting_cost_per_minute;//: "0"
	private String ride_cost_per_minute;//: "1"
	private String surcharge;//: [0]
	
	public SedanFare(String type, String minimum_distance, String minimum_time ,String base_fare, String cost_per_distance, String waiting_cost_per_minute, String ride_cost_per_minute, String surcharge) {
		this.type = type;
		this.minimum_distance = minimum_distance;
		this.minimum_time = minimum_time;
		this.base_fare = base_fare;
		this.cost_per_distance = cost_per_distance;
		this.waiting_cost_per_minute = waiting_cost_per_minute;
		this.ride_cost_per_minute = ride_cost_per_minute;
		this.surcharge = surcharge;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMinimum_distance() {
		return minimum_distance;
	}
	public void setMinimum_distance(String minimum_distance) {
		this.minimum_distance = minimum_distance;
	}
	public String getMinimum_time() {
		return minimum_time;
	}
	public void setMinimum_time(String minimum_time) {
		this.minimum_time = minimum_time;
	}
	public String getBase_fare() {
		return base_fare;
	}
	public void setBase_fare(String base_fare) {
		this.base_fare = base_fare;
	}
	public String getCost_per_distance() {
		return cost_per_distance;
	}
	public void setCost_per_distance(String cost_per_distance) {
		this.cost_per_distance = cost_per_distance;
	}
	public String getWaiting_cost_per_minute() {
		return waiting_cost_per_minute;
	}
	public void setWaiting_cost_per_minute(String waiting_cost_per_minute) {
		this.waiting_cost_per_minute = waiting_cost_per_minute;
	}
	public String getRide_cost_per_minute() {
		return ride_cost_per_minute;
	}
	public void setRide_cost_per_minute(String ride_cost_per_minute) {
		this.ride_cost_per_minute = ride_cost_per_minute;
	}
	public String getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}
	public String  getSedanFare() {
		return new Gson().toJson(this);
	}
}

