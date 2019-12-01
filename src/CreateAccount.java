import org.json.JSONObject;

import backend_request.Json;
import backend_request.Post;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CreateAccount implements EventHandler<ActionEvent> {
	private Scene scene;
	private BorderPane mainLayout;
	
	private TextField nameInput;
	private TextField passInput;
	private TextField passInput2;
	private Button create;
	private Button back;
	
	
	
	public CreateAccount() {
		scene();
	}
	

	public void scene() {
		mainLayout= new BorderPane();
		scene =new Scene(mainLayout, 800, 600);
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
		inputs.setPadding(new Insets(0, 60, 0, 60));
		nameInput=new TextField("");
		nameInput.setPromptText("username");
		
		passInput=new TextField("");
		passInput.setPromptText("password");
		
		passInput2=new TextField("");
		passInput2.setPromptText("confirm password");
		inputs.getChildren().addAll(nameInput,passInput,passInput2);
		
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
		center.setPadding(new Insets(0, 30, 0, 30));
		center.getChildren().addAll(top,middle);
		center.getStyleClass().add("center");
		center.setMaxSize(300, 800);
		
		
		
		mainLayout.setCenter(center);
		
	}

	
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==create) {
			System.out.println("click!!!!!!");
			
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


	public void setPassInput(TextField passInput) {
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


	public void setPassInput2(TextField passInput2) {
		this.passInput2 = passInput2;
	}
	
	

	

	
}
