package backend_request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json {
	
	
	public static JSONObject toJSONObject(String json) {
		try {
	        JSONObject obj = new JSONObject(json);
	        return obj;
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
        
	}
	
	/*
	 public static void ParseJSON(String json) {
		try {
	        JSONObject obj = new JSONObject(json);
	        String pageName = obj.getJSONObject("pageInfo").getString("pageName");
	
	        System.out.println(pageName);
	
	        JSONArray arr = obj.getJSONArray("posts");
	        for (int i = 0; i < arr.length(); i++) {
	            String post_id = arr.getJSONObject(i).getString("post_id");
	            System.out.println(post_id);
	        }
	        
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
        
	}
	 * */
	

}
