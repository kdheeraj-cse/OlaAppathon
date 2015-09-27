package com.spock.cabService;

import com.spock.cabBooking.MiniBooking;

public class CabBooking {

	private String []_crn = {"1488","1489","1490"};
	private String []_driver_name = {"Chonenix D248","Chonenix D249","Chonenix D250"};
	private String []_driver_number = {"2567894248","2567894249","2567894250"};
	private String []_cab_number = {"KA 14  8","KA 14  9","KA 14  10"};
	private Integer []_eta = {3,4,5};
	private String []_lat = {"12.950074","12.9562593","12.9609857"};
	private String []_lng = {"77.641727","77.6534629","77.6387316"};
	public static Integer cabCount = 0;
	
	
	//"crn":"1588","driver_name":"Phonenix D248","driver_number":"4567894248","cab_type":"sedan","cab_number":"KA 24  8","car_model":"Toyota Corolla","eta":3,"driver_lat":12.950074,"driver_lng":77.641727}
	
	public String bookingDetails() {
		MiniBooking obj = new MiniBooking(_crn[cabCount], _driver_name[cabCount], _driver_number[cabCount], "mini", _cab_number[cabCount], "tata Indica", _eta[cabCount], _lat[cabCount], _lng[cabCount]);
		cabCount++;
		if(cabCount==3){
			cabCount = 0;
		}
		return obj.getBookingDetails();
	}
}
