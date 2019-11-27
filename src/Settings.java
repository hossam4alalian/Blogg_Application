import backend_request.Post;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Settings implements EventHandler<ActionEvent> {
	private Scene scene;
	private BorderPane mainLayout;
	
	private TextField nameInput;
	private TextField passInput;
	private Button login;
	private Button skip;
	
	public Settings() {
		scene();
	}
	
	public void scene() {
		mainLayout= new BorderPane();
		scene =new Scene(mainLayout, 800, 600);
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
		
		 passInput= new TextField();
		passInput.setPromptText("password");
		loginBox.add(passInput, 2, 2);
		
		login= new Button("Login");
		login.setOnAction(this);
		loginBox.add(login,  2, 3);
		
		VBox middle= new VBox(20);
		middle.getChildren().add(loginBox);
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
		center.setMaxSize(300, 800);
		
		
		
		mainLayout.setCenter(center);
		
	}

	
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==login) {
			String name=nameInput.getText();
			String password=passInput.getText();
			
			try {
				//måste fixas. fungerar inte!!!!!!!!
				String shit=Post.send("nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=function&handling=login&anamn="+name+"&losenord="+password+"&rollid=4");
				System.out.println(shit);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	
}
