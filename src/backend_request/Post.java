package backend_request;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Post {

	
	public static void getData() {
		String url="http://10.130.216.101/TP/Blogg/";
		
		try {
			
			BufferedReader reader=new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			String line="";
			while((line=reader.readLine())!=null) {
				System.out.println(reader.readLine());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public static String send(String postValues) throws Exception {
		
		// url is missing?
        //String url = "https://selfsolve.apple.com/wcResults.do";
        String url = "http://10.130.216.101/TP/api.php";

        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //postValues = "nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=JSON";

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(postValues);
            wr.flush();
        }
        
        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postValues);
        System.out.println("Response Code : " + responseCode);
        
        
        
        if(responseCode==500) {
        	
        	try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpClient.getErrorStream()))) {

                String line;
                StringBuilder response = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                //print result
                System.out.println(response.toString());

            }
        	
        	return "";
        }
        
        

        try (BufferedReader in = new BufferedReader( new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //System.out.println(response.toString());
            return response.toString();

        }
        
        

    }
	
	
	
	
public static String send(String location, String postValues) throws Exception {
		
		// url is missing?
        //String url = "https://selfsolve.apple.com/wcResults.do";
        String url = "http://10.130.216.101/TP/"+location;

        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //postValues = "nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=JSON";

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(postValues);
            wr.flush();
        }
        
        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postValues);
        System.out.println("Response Code : " + responseCode);
        
        
        
        if(responseCode==500) {
        	
        	try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpClient.getErrorStream()))) {

                String line;
                StringBuilder response = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                //print result
                System.out.println(response.toString());

            }
        	
        	return "";
        }
        
        

        try (BufferedReader in = new BufferedReader( new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //System.out.println(response.toString());
            return response.toString();

        }
        
        

    }
	
	
	
	

}
