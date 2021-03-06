import org.json.JSONArray;
import org.json.JSONObject;

import backend_request.HttpRequest;
import backend_request.Json;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Login implements EventHandler<ActionEvent> {
	private Scene scene;
	private BorderPane mainLayout;
	
	private TextField nameInput;
	private TextField passInput;
	private Button login;
	private Button skip;
	
	private Button createAccount;
	
	private String userId;
	
	private String username;
	private String bloggId;
	private int likes=0;
	private boolean loggedIn=false;
	
	
	public Login() {
		username="Guest";
		bloggId="null";
		
		scene();
	}
	
	public Button getSkip() {
		return skip;
	}

	public void setSkip(Button skip) {
		this.skip = skip;
	}

	public void scene() {
		mainLayout= new BorderPane();
		scene =new Scene(mainLayout,  1280, 720);
		scene.getStylesheets().add("main.css");
		loginDesign();
		
		
		
	}
	
	public void loginDesign() {
		
		Label loginText= new Label("Login:");
		loginText.setFont(new Font("Arial", 30));
		
		
		VBox top= new VBox(20);
		top.getChildren().add(loginText);
		top.getStyleClass().add("loginTop");
		top.setMargin(top, new Insets(50, 0, 0, 0));
		
		GridPane loginBox = new GridPane();
		loginBox.setPadding(new Insets(10,10,10,10));
		loginBox.setVgap(8);
		loginBox.setHgap(10);
		
		
		Label username= new Label("Username: ");
		loginBox.add(username, 1, 1);
		
		nameInput= new TextField();
		nameInput.setPromptText("username");
		loginBox.add(nameInput, 2, 1);
		
		Label password= new Label("Password: ");
		loginBox.add(password, 1, 2);
		
		 passInput= new PasswordField();
		passInput.setPromptText("password");
		loginBox.add(passInput, 2, 2);
		
		login= new Button("Login");
		login.setOnAction(this);
		loginBox.add(login,  2, 3);
		
		createAccount=new Button("Create account");
		
		
		VBox middle= new VBox(20);
		middle.getChildren().addAll(loginBox,createAccount);
		middle.getStyleClass().add("loginCenter");
		middle.setMargin(middle, new Insets(50, 0, 0, 0));
		
		skip= new Button("Skip -------->");
		skip.setOnAction(this);
	
		
		VBox bottom= new VBox(20);
		bottom.getChildren().add(skip);
		bottom.getStyleClass().add("loginBottom");
		bottom.setMargin(bottom, new Insets(50, 0, 0, 0));
		
		VBox center= new VBox();
		center.getChildren().addAll(top, middle, bottom);
		center.getStyleClass().add("center");
		center.setMaxSize(400, 800);
		
		
		
		mainLayout.setCenter(center);
		
	}

	
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==login) {
			System.out.println(loggedIn);
			
			String name=nameInput.getText();
			String password=passInput.getText();
			
			try {
				String shit=HttpRequest.send("Login/login.php","&tjanst=blogg&anamn="+name+"&password="+password);//anvandare1: kalle, 123
				JSONObject object=Json.toJSONObject(shit);
				System.out.println(shit);
				userId=object.getString("anvandarId");
				username=object.getString("anamn");
				bloggId=object.getString("bloggId");
				
				Main.menus.getUsername().setText(username);
				Main.window.setScene(Main.scene);
				if(Main.page==2) {
					Main.blogg.refresh();
				}
				
				Main.menus.getLogin().setText("Log out");
				
				nameInput.setText("");
				passInput.setText("");
				
				loggedIn=true;
				
			} catch (Exception e) {
				//e.printStackTrace();
			}
			
			
			
			
			
			
			
			
			
			likes=0;
			try {
				String blogg = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+getBloggId());
			
				JSONObject json=new JSONObject(blogg);
				System.out.println("blogg");
				bloggId=json.getString("bloggId");

				JSONArray inlagg=json.getJSONArray("bloggInlagg");
				for(int i=inlagg.length()-1;i>=0;i--) {
					String inlaggStr = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+getBloggId()+"&inlagg="+inlagg.getJSONObject(i).getString("id"));
					

					JSONObject inlaggJson=Json.toJSONObject(inlaggStr);
					
					String title=inlaggJson.getString("titel");
					
					String postId=inlaggJson.getString("id");
					
					String text=inlaggJson.getString("innehall");
					
					JSONArray array=inlaggJson.getJSONArray("gillningar");
					
					int likesAmount=array.length();
					
					likes+=likesAmount;
					
				}
			} catch (Exception ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
			
			
			
			Main.menus.getLikes().setText("Likes:"+getLikes());
			
			
		}
	}
	
	
	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBloggId() {
		return bloggId;
	}

	public void setBloggId(String bloggId) {
		this.bloggId = bloggId;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public Button getCreateAccount() {
		return createAccount;
	}

	public void setCreateAccount(Button createAccount) {
		this.createAccount = createAccount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
