package communication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;




public class MedicamentV2 {
	public int MedID;
	public String MedName;
	
	public MedicamentV2(int medID, String medName) {
		super();
		MedID = medID;
		MedName = medName;
	}
	public int getMedID() {
		return MedID;
	}
	public void setMedID(int medID) {
		MedID = medID;
	}
	public String getMedName() {
		return MedName;
	}
	public void setMedName(String medName) {
		MedName = medName;
	}
	/**
	 * set Medicine
	 * @return booleen
	 * @throws IOException
	 */
	public boolean setMedicine() throws IOException {
		// Create a neat value object to hold the URL
		String url1="http://172.20.10.14:8000/setMedicine/"+this.MedID+"/"+this.MedName;
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
	    JSONObject myResponse; 
	    myResponse = new JSONObject(response.toString());
	    int Code = myResponse.getInt("code");
	    System.out.println("statusCode- "+myResponse.getInt("code"));
	   
	 
	    if (Code == 200)
	    	{
	    	System.out.println("test");
	    			return true;
	    	}
	    else return false;
		
	}
	
	
	
	
	
	
	
}

