package backend_request;
import org.json.JSONArray;
import org.json.JSONObject;

import backend_request.Json;

public class testing {

	public static void main(String[] args) {
		
		
		/*String str;
		try {
			str = HttpRequest.send("Admin/funktioner/skapa.php","funktion=skapaAKonto&anamn=Niles ahmad&rollid=4&tjanst=1");
			//str = Post.send("","nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=function&handling=skapa2&funktion=skapaAKonto&anamn=NilesTest&rollid=4&tjanst=blogg");
			System.out.println(str);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		/*String str;
		try {
			str = HttpRequest.send("Admin/funktioner/redigera.php","funktion=redigeraKonto&anvandarid=13&losenord=ahmad");
			//str = Post.send("","nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=function&handling=skapa2&funktion=skapaAKonto&anamn=NilesTest&rollid=4&tjanst=blogg");
			System.out.println(str);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
		String str;
		try {
			str = HttpRequest.send("Blogg/funktioner/skapa.php","funktion=skapaBlogg2&anvandarId=37&Titel=niles blogg&bloggAnvandarId=37");
			//str = Post.send("nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=function&handling=skapa&funktion=skapaBlogg&anvandarId=1&Titel=titelpåblogg&bloggAnvandarId=7");
			System.out.println(str);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		/*String str;
		try {
			str = HttpRequest.send("Blogg/funktioner/skapa.php","funktion=skapaInlagg&bloggId=13&Title=this is a post title&innehall=this is a lot of text or something");
			//str = Post.send("nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=function&handling=skapa&funktion=skapaBlogg&anvandarId=1&Titel=titelpåblogg&bloggAnvandarId=7");
			System.out.println(str);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*String str;
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
		}*/
		
		
		
		
		
		/*String str;
		try {
			str = HttpRequest.send("Admin/funktioner/redigera.php","funktion=redigeraKonto&anvandarid=34&losenord=123");
			//str = Post.send("","nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=function&handling=skapa2&funktion=skapaAKonto&anamn=NilesTest&rollid=4&tjanst=blogg");
			System.out.println(str);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
		
	}

}
