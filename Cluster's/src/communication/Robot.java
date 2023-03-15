package communication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Robot {

	   private String path;
	   private int room;
	   private int robotID;
	   private int node;
	   
	public Robot(String path, int room, int robotID, int node) {
		super();
		this.path = path;
		this.room = room;
		this.robotID = robotID;
		this.node = node;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

	public int getRobotID() {
		return robotID;
	}

	public void setRobotID(int robotID) {
		this.robotID = robotID;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}
	   
	   
	public boolean setpath() throws IOException {
		// Create a neat value object to hold the URL
		String url1="http://172.20.10.14:8000/setpath/"+this.room+"/"+this.path;
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
	    	System.out.println("test");
	    			return true;
	    	}
	    else return false;
	}
	
	
	public boolean addOrder(int Sroom,int medId) throws IOException {
		// Create a neat value object to hold the URL
		String url1="http://172.20.10.14:8000/addOrder/"+ Sroom +"/" + medId ;
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
	    return true;
	    	
	}   
	   
	    
	    
}
