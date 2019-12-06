import java.util.Optional;

import org.json.JSONObject;

import backend_request.HttpRequest;
import backend_request.Json;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CreateAccount implements EventHandler<ActionEvent> {
	private Scene scene;
	private BorderPane mainLayout;
	
	private TextField nameInput;
	private PasswordField passInput;
	private PasswordField passInput2;
	private Button create;
	private Button back;
	
	private TextField bloggTitle;
	
	
	public CreateAccount() {
		scene();
	}
	

	public void scene() {
		mainLayout= new BorderPane();
		scene =new Scene(mainLayout, 1000, 800);
		scene.getStylesheets().add("main.css");
		loginDesign();
		
		
	}
	
	public void loginDesign() {
		
		Label header= new Label("Create account:");
		header.setFont(new Font("Arial", 30));
		
		
		VBox top= new VBox(20);
		top.getChildren().add(header);
		top.getStyleClass().add("loginTop");
		top.setMargin(top, new Insets(0, 0, 0,0));
		
		
		VBox inputs=new VBox(15);
		inputs.setPadding(new Insets(0, 50, 0, 50));
		nameInput=new TextField("");
		nameInput.setPromptText("username");
		
		passInput=new PasswordField();
		passInput.setPromptText("password");
		
		passInput2=new PasswordField();
		passInput2.setPromptText("confirm password");
		
		bloggTitle=new TextField("");
		bloggTitle.setPromptText("blogg title");
		
		inputs.getChildren().addAll(nameInput,passInput,passInput2,bloggTitle);
		
		create= new Button("Create");
		create.setOnAction(this);
		top.getChildren().addAll(inputs);
		
		
		HBox middle= new HBox(10);
		middle.setAlignment(Pos.CENTER_RIGHT);
		middle.setMargin(top, new Insets(0, 0, 0, 0));
		

		back= new Button("Back");
		back.setOnAction(this);
		
		middle.getChildren().addAll(create,back);
		
		
	
		VBox center= new VBox(20);
		center.setPadding(new Insets(0, 20, 0, 20));
		center.getChildren().addAll(top,middle);
		center.getStyleClass().add("center");
		center.setMaxSize(400, 800);
		
		
		
		mainLayout.setCenter(center);
		
	}

	
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==create) {
			if(!passInput.getText().equals(passInput2.getText())) {
				System.out.println("password not maching");
				return;
			}
			
			
			
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("");
			alert.setHeaderText("your bloggs title will be "+bloggTitle.getText());
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    // ... user chose OK
			} else {
			    return;
			}
			
			
			String str;
			try {
				str = HttpRequest.send("Admin/funktioner/skapa.php","funktion=skapaAKonto&anamn="+nameInput.getText()+"&rollid=4&tjanst=1");
				//str = Post.send("","nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=function&handling=skapa2&funktion=skapaAKonto&anamn=NilesTest&rollid=4&tjanst=blogg");
				System.out.println(str);
				
				
				JSONObject user=Json.toJSONObject(str);
				String userId=user.getString("userid");
				String password=passInput.getText();
			
				System.out.println(nameInput.getText());
				System.out.println(password);
			
				String str2;
				str2 = HttpRequest.send("Admin/funktioner/redigera.php","funktion=redigeraKonto&anvandarid="+userId+"&losenord="+password);
				//str = Post.send("","nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=function&handling=skapa2&funktion=skapaAKonto&anamn=NilesTest&rollid=4&tjanst=blogg");
				//System.out.println(str2);
				
				
				
				
				
				String blogg;
				blogg = HttpRequest.send("Blogg/funktioner/skapa.php","funktion=skapaBlogg2&anvandarId=37&Titel="+bloggTitle.getText()+"&bloggAnvandarId="+userId);
				//str = Post.send("nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=function&handling=skapa&funktion=skapaBlogg&anvandarId=1&Titel=titelpåblogg&bloggAnvandarId=7");
				System.out.println(blogg);
				
				
				
				
				
				
				
				try {
					//String shit=Post.send("Login/login.php","nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=function&handling=login&anamn="+name+"&losenord="+password+"&rollid=4");
					//måste fixas. fungerar inte!!!!!!!!
					String shit=HttpRequest.send("Login/login.php","&tjanst=blogg&anamn="+nameInput.getText()+"&password="+passInput.getText());//anvandare1: kalle, 123
					System.out.println(shit);
					
					JSONObject object=Json.toJSONObject(shit);
					
					Main.login.setUsername(object.getString("anamn"));
					Main.login.setBloggId(object.getString("bloggId"));
					
					Main.menus.getUsername().setText(Main.login.getUsername());
					
					Main.explore.refresh();
					Main.window.setScene(Main.scene);
					
					Main.menus.getLogin().setText("Log out");
					
					
					
					Main.login.setLoggedIn(true);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				Main.window.setScene(Main.scene);
				
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


	public TextField getNameInput() {
		return nameInput;
	}


	public void setNameInput(TextField nameInput) {
		this.nameInput = nameInput;
	}


	public TextField getPassInput() {
		return passInput;
	}


	public void setPassInput(PasswordField passInput) {
		this.passInput = passInput;
	}


	public Button getCreate() {
		return create;
	}


	public void setCreate(Button create) {
		this.create = create;
	}


	public Button getBack() {
		return back;
	}


	public void setBack(Button back) {
		this.back = back;
	}


	public TextField getPassInput2() {
		return passInput2;
	}


	public void setPassInput2(PasswordField passInput2) {
		this.passInput2 = passInput2;
	}
	
	

	

	
}
