package com.spock.Cabs;

import com.google.gson.Gson;
import com.spock.CabFares.SedanFare;

public class Sedan {
    private String id;//: "mini"
	private String display_name;//: "Mini"
	private String currency;//: "INR"
	private String distance_unit;//: "kilometre"
	private String time_unit;//: "minute"
	private String eta;//: -1
	private String distance;//: -1
	private SedanFare fare_breakup;
	public Sedan(String id, String display_name, String currency, String distance_unit, String time_unit, String eta, String distance, SedanFare fare) {
		this.id = id;
		this.display_name = display_name;
		this.currency = currency;
		this.distance_unit = distance_unit;
		this.time_unit = time_unit;
		this.eta = eta;
		this.distance = distance;
		this.fare_breakup = fare;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDistance_unit() {
		return distance_unit;
	}
	public void setDistance_unit(String distance_unit) {
		this.distance_unit = distance_unit;
	}
	public String getTime_unit() {
		return time_unit;
	}
	public void setTime_unit(String time_unit) {
		this.time_unit = time_unit;
	}
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}

	public SedanFare getFare() {
		return fare_breakup;
	}

	public void setFare(SedanFare fare) {
		this.fare_breakup = fare;
	}
	
	public String getSedanDetails(){
		return new Gson().toJson(this);
	}
}
