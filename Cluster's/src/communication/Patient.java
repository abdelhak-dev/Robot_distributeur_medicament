package communication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

import org.json.JSONObject;

public class Patient {

	private String  patientID;
    private int room;
    private String week;
    private Statut condition;
    private MedicamentV2 med;
    
     

	public String getPatientID() {
		return patientID;
	}
	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Statut getCondition() {
		return condition;
	}

	public void setCondition(Statut condition) {
		this.condition = condition;
	}

	public MedicamentV2 getMed() {
		return med;
	}

	public void setMed(MedicamentV2 med) {
		this.med = med;
	}
	public Patient(String patientID, int room, String week, Statut condition, MedicamentV2 med) {
		super();
		this.patientID = patientID;
		this.room = room;
		this.week = week;
		this.condition = condition;
		this.med = med;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}
	
	public boolean addPatient() throws IOException {
		// Create a neat value object to hold the URL
		String url1="http://172.20.10.14:8000/addPatient/"+this.room+"/"+this.patientID+"/"+this.week;  //127.0.0.2/addPatient/4/1254/
		URL url = new URL(url1);
		// Open a connection(?) on the URL(??) and cast the response(???)
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("accept", "application/json");
		// This line makes the request
		BufferedReader in = new BufferedReader(
	            new InputStreamReader(connection.getInputStream()));
	    String inputLine;
	    StringBuffer response = new StringBuffer();
	    while ((inputLine = in.readLine()) != null) {
	    	response.append(inputLine);
	    }
	    in.close();
	    JSONObject myResponse = new JSONObject(response.toString());
	    int Code = myResponse.getInt("code");
	    System.out.println("statusCode- "+myResponse.getInt("code"));
	    if (Code == 200)
    	{
    			return true;
    	}
	    else return false;
	
		
	}

	public Statut getPatientCondition() throws IOException {
		String url1="http://172.20.10.14:8000/getPatientCondition/"+this.patientID;
		URL url = new URL(url1);
		// Open a connection(?) on the URL(??) and cast the response(???)
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("accept", "application/json");
		// This line makes the request
		BufferedReader in = new BufferedReader(
	            new InputStreamReader(connection.getInputStream()));
	    String inputLine;
	    StringBuffer response = new StringBuffer();
	    while ((inputLine = in.readLine()) != null) {
	    	response.append(inputLine);
	    }
	    in.close();
	    JSONObject myResponse = new JSONObject(response.toString());
	    String ValueCondition = myResponse.getString("condition");
		switch (ValueCondition)
		{
		case "Cured" : 
			condition=Statut.Cured;
			return condition;
		case "Stable" :
			condition=Statut.Stable;
			return condition;
		case "Dead":
		default : 
			return null;
		}
	}

	 public boolean setPatientCondition() throws IOException{
		String url1="http://172.20.10.14:8000/setPatientCondition/"+this.patientID+"/"+this.condition;
		URL url = new URL(url1);
		// Open a connection(?) on the URL(??) and cast the response(???)
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("accept", "application/json");
		// This line makes the request
		BufferedReader in = new BufferedReader(
	            new InputStreamReader(connection.getInputStream()));
	    String inputLine;
	    StringBuffer response = new StringBuffer();
	    while ((inputLine = in.readLine()) != null) {
	    	response.append(inputLine);
	    }
	    in.close();
	    JSONObject myResponse = new JSONObject(response.toString());
	    int Code = myResponse.getInt("code");
	    System.out.println("statusCode- "+myResponse.getInt("code"));
	    if (Code == 200)
    	{
    			return true;
    	}
	    else return false;
	
	}
	 
	 public boolean setRoomMedecine() throws IOException{
			String url1="http://172.20.10.14:8000/setPatientCondition/"+this.room+"/"+this.med.getMedID()+"/"+this.week;
			URL url = new URL(url1);
			// Open a connection(?) on the URL(??) and cast the response(???)
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("accept", "application/json");
			// This line makes the request
			BufferedReader in = new BufferedReader(
		            new InputStreamReader(connection.getInputStream()));
		    String inputLine;
		    StringBuffer response = new StringBuffer();
		    while ((inputLine = in.readLine()) != null) {
		    	response.append(inputLine);
		    }
		    in.close();
		    JSONObject myResponse = new JSONObject(response.toString());
		    int Code = myResponse.getInt("code");
		    System.out.println("statusCode- "+myResponse.getInt("code"));
		    if (Code == 200)
	    	{
	    			return true;
	    	}
		    else return false;
		
		}
	 
	 public Object[] getRoomMedecine() throws IOException{
			String url1="http://172.20.10.14:8000/getPatientCondition/"+this.room+"/"+this.med.getMedID();
			URL url = new URL(url1);
			// Open a connection(?) on the URL(??) and cast the response(???)
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("accept", "application/json");
			// This line makes the request
			BufferedReader in = new BufferedReader(
		            new InputStreamReader(connection.getInputStream()));
		    String inputLine;
		    StringBuffer response = new StringBuffer();
		    while ((inputLine = in.readLine()) != null) {
		    	response.append(inputLine);
		    }
		    in.close();
		    JSONObject myResponse = new JSONObject(response.toString());
		    this.room = myResponse.getInt("room");
		    this.week = myResponse.getString("week");
		    this.med.setMedID(myResponse.getInt("medecine"));
		    return new Object[] {this.room,this.week,this.med.getMedID()};
		    
	//int [] ints = getRoomMedecine()
		    //syso (Arrays.toString(ints));
		}
	
	
	
}