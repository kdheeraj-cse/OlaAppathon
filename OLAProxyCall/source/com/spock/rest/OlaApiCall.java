package com.spock.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.spock.CabFares.MiniFare;
import com.spock.CabFares.SedanFare;
import com.spock.Cabs.Mini;
import com.spock.Cabs.Sedan;
import com.spock.cabService.CabBooking;

@Path("/api")
public class OlaApiCall {
	
	String XAPPToken = "c85fe5e8beca4ee3b2ec16826e4f2d22";
	
	@GET
	@Path("/Rides")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRidesAvailabilty(@QueryParam("lat") String lat, @QueryParam("lng") String lng, @DefaultValue("notPassed") @QueryParam("cat") String cat){
		StringBuffer responseString = new StringBuffer();
		HttpURLConnection conn = null;
		  try {
			  	System.out.println("lattitude "+lat+" longitude "+lng+" cat "+cat);
			  	String _url = "";
			  	if(cat.equals("notPassed")){
			  		_url = "http://sandbox-t.olacabs.com/v1/products?pickup_lat="+lat+"&pickup_lng="+lng;
			  	}else{
			  		_url = "http://sandbox-t.olacabs.com/v1/products?pickup_lat="+lat+"&pickup_lng="+lng+"&category="+cat;
			  		}
			  	URL url = new URL(_url);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("X-APP-Token", XAPPToken);
				if (conn.getResponseCode() != 200) {
					System.out.println("Issue "+conn.getResponseCode());
					return Response.status(conn.getResponseCode()).entity("").build();
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
				String output;
				while ((output = br.readLine()) != null) {
					responseString.append(output);
				}
				String interString = responseString.toString();
				JSONParser parser = new JSONParser();
				JSONObject objJSON = (JSONObject)parser.parse(interString);
				JSONArray categoryArray = (JSONArray)objJSON.get("categories");
				JSONObject objJSON_1 = null;
				if(cat.equals("mini") || cat.equals("notPassed")){
					MiniFare fareMini = new MiniFare("flat_rate", "4", "5", "80", "10", "5", "1", "0");
					Mini objMINI = new Mini("mini", "Mini", "INR", "kilometre", "minute", "3", "2", fareMini);
					objJSON_1 = (JSONObject)parser.parse(objMINI.getMiniDetails());
					categoryArray.add(objJSON_1);
					objMINI = new Mini("mini", "Mini", "INR", "kilometre", "minute", "4", "5", fareMini);
					objJSON_1 = (JSONObject)parser.parse(objMINI.getMiniDetails());
					categoryArray.add(objJSON_1);
				}if(cat.equals("sedan") || cat.equals("notPassed")) {
					SedanFare fareSedan = new SedanFare("flat_rate", "4", "10", "100", "13", "0", "1", "0");
					Sedan objSedan = new Sedan("sedan", "Sedan", "INR", "kilometre", "minute", "3", "1", fareSedan);
					objJSON_1 = (JSONObject)parser.parse(objSedan.getSedanDetails());
					categoryArray.add(objJSON_1);
				}
				objJSON.put("categories", categoryArray);
				conn.disconnect();
				return Response.status(conn.getResponseCode()).entity(new Gson().toJson(objJSON)).build();
			  } catch (MalformedURLException e) {
				e.printStackTrace();
			  } catch (IOException e) {
				e.printStackTrace();
			  } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return Response.status(200).entity("").build();
		}
	@GET
	@Path("/bookRides")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bookRides(@QueryParam("lat") String pickup_lat, @QueryParam("lng") String pickup_lng, @QueryParam("countMini") Integer cabCountMini, @QueryParam("countsedan") Integer cabCountSedan){
		System.out.println("lat "+pickup_lat+" lng "+pickup_lng+" cab count MINI "+cabCountMini+" can count sedan "+cabCountSedan);
		HttpURLConnection conn = null;
		StringBuffer responseString = new StringBuffer();
		URL url;
		try {
			for (int i = 0; i < cabCountMini; i++) {
				url = new URL("http://sandbox-t.olacabs.com/v1/bookings/create?pickup_lat="+pickup_lat+"&pickup_lng="+pickup_lng+"&pickup_mode=NOW&category=mini");
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("X-APP-Token", XAPPToken);
				conn.setRequestProperty("Authorization", "Bearer 66dd55d917b74e18846713e2fadcc571");
				if (conn.getResponseCode() != 200) {
					System.out.println("Issue "+conn.getResponseCode());
					return Response.status(conn.getResponseCode()).entity("").build();
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
				String output;
				while ((output = br.readLine()) != null) {
					JSONParser parser = new JSONParser();
					JSONObject objJSON = (JSONObject)parser.parse(output);
					if(objJSON.get("status")==null){
						responseString.append(output);
					}else{
						CabBooking objBook = new CabBooking();
						responseString.append(objBook.bookingDetails());
					}
					if(i!=cabCountMini-1){
						responseString.append(",");
					}
			}
			}
			if(cabCountSedan!=0 && cabCountMini!=0){
				responseString.append(",");
			}
			for (int i = 0; i < cabCountSedan; i++) {
				url = new URL("http://sandbox-t.olacabs.com/v1/bookings/create?pickup_lat="+pickup_lat+"&pickup_lng="+pickup_lng+"&pickup_mode=NOW&category=sedan");
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("X-APP-Token", XAPPToken);
				conn.setRequestProperty("Authorization", "Bearer 66dd55d917b74e18846713e2fadcc571");
				if (conn.getResponseCode() != 200) {
					//System.out.println("Issue "+conn.getResponseCode());
					return Response.status(conn.getResponseCode()).entity("").build();
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
				String output;
				while ((output = br.readLine()) != null) {
					responseString.append(output);
			}
				if(i!=cabCountSedan-1){
					responseString.append(",");
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonToProcess = "{\"bookingResponse\":["+responseString.toString()+"]}";
		return Response.status(200).entity(jsonToProcess).build();
	}
	
}