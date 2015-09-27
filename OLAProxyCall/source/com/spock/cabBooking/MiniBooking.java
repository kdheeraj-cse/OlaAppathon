package com.spock.cabBooking;

import com.google.gson.Gson;

public class MiniBooking {
	private String crn;//":"1588",
	private String driver_name;//:"Phonenix D248",
	private String driver_number;//:"4567894248",
	private String cab_type;//"sedan","
	private String cab_number;//":"KA 24  8",
	private String car_model;//:"Toyota Corolla","
	private Integer eta;//":3,"
	private String driver_lat;//":12.950074,"
	private String driver_lng;//":77.641727}
	
	public MiniBooking(String crn, String driver_name,String driver_number ,String cab_type,String cab_number,String car_model,Integer eta,String driver_lat,String driver_lng) {
		this.crn = crn;
		this.driver_name = driver_name;
		this.driver_number = driver_number;
		this.cab_type = cab_type;
		this.cab_number = cab_number;
		this.car_model = car_model;
		this.eta = eta;
		this.driver_lat = driver_lat;
		this.driver_lng = driver_lng;
	}
	
	public String getCrn() {
		return crn;
	}
	public void setCrn(String crn) {
		this.crn = crn;
	}
	public String getDriver_name() {
		return driver_name;
	}
	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}
	public String getDriver_number() {
		return driver_number;
	}
	public void setDriver_number(String driver_number) {
		this.driver_number = driver_number;
	}
	public String getCab_type() {
		return cab_type;
	}
	public void setCab_type(String cab_type) {
		this.cab_type = cab_type;
	}
	public String getCab_number() {
		return cab_number;
	}
	public void setCab_number(String cab_number) {
		this.cab_number = cab_number;
	}
	public String getCar_model() {
		return car_model;
	}
	public void setCar_model(String car_model) {
		this.car_model = car_model;
	}
	public Integer getEta() {
		return eta;
	}
	public void setEta(Integer eta) {
		this.eta = eta;
	}
	public String getDriver_lat() {
		return driver_lat;
	}
	public void setDriver_lat(String driver_lat) {
		this.driver_lat = driver_lat;
	}
	public String getDriver_lng() {
		return driver_lng;
	}
	public void setDriver_lng(String driver_lng) {
		this.driver_lng = driver_lng;
	}
	public String getBookingDetails(){
		return new Gson().toJson(this);
	} 
}
