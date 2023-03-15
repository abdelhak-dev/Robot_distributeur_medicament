package superFx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;


import communication.Statut;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;


public class SuperStats {
	private String week;
	private Statut condition;
	private int robot;
	
	
	public Object[] getStats() throws IOException {
		String url1="172.20.10.14:8000/getStats/"+this.week;
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
	    Object[] valueStats = (Object[]) myResponse.get("week");
		
			return valueStats;
		}
		
		
	public String[] getHistory() throws IOException {
		String url1="http://172.20.10.14:8000/getHistory/";
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
	    System.out.println(myResponse);
	    
	    Object valueHistory = myResponse.get("history");
	    System.out.println(valueHistory);
	    String[] test1 = valueHistory.toString().split("]"); //TODO PROPER REGEX
	    System.out.println(test1[1]);
	    
	    //System.out.println("statusCode- "+ myResponse.getInt("code"));
	    
		return test1;
			
	}
		
		public Object[][] getStatDoli() {
			Object[][] Doly = new Object[][] {{"01/01",25},{"01/01",25}};
			
			return Doly;
			 
		}


		public SuperStats(String week, Statut condition, int robot) {
			super();
			this.week = week;
			this.condition = condition;
			this.robot = robot;
		}
}
