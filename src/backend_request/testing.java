package backend_request;
import org.json.JSONArray;
import org.json.JSONObject;

import backend_request.Json;
import backend_request.Post;

public class testing {

	public static void main(String[] args) {
		String str;
		try {
			str = Post.send("nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=JSON&blogg=1&inlagg=3");
		
		System.out.println(str);
		JSONObject json=Json.toJSONObject(str);
		
		System.out.println("");
		System.out.println("name: "+json.getString("anamn"));
		System.out.println("");
		System.out.println("email: "+json.getString("email"));
		System.out.println("");
		System.out.println("");
		
		JSONArray array=json.getJSONArray("bloggar");
		for(int i=0;i<array.length();i++) {
			System.out.println(array.getJSONObject(i).getString("id"));
			System.out.println(array.getJSONObject(i).getString("titel"));
			System.out.println("");
		}
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
