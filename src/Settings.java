import javax.swing.text.html.Option;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Settings implements EventHandler<ActionEvent> {
	private BorderPane mainLayout;
	private Scene scene;
	
	private Button apply;
	private Button cancel;
	
	public Settings() {
		scene();
	}
	
	public void scene() {
		mainLayout= new BorderPane();
		scene =new Scene(mainLayout, 800, 600);
		scene.getStylesheets().add("main.css");
		settings();
		
		
		
	}
	
	public void settings() {
		
		Label settingsLabel= new Label("Settings:");
		settingsLabel.setFont(new Font("Arial", 30));
		settingsLabel.setPadding(new Insets(80, 20, 20, 20));
		
		Button Languege=new Button("Languege");	
		Languege.getStyleClass().add("settingsButton");
		
		
		ComboBox<String>color=new ComboBox<String>();
		color.getStyleClass().add("settingsButton");
		color.setValue("Light");
		
		color.getItems().addAll("Light");
		color.getItems().addAll("Dark");
		color.getItems().addAll("Red");
		color.getItems().addAll("Blue");
		
		
		VBox pass=new VBox(20);
		pass.setAlignment(Pos.CENTER);
		
		Label changePassword= new Label("Change password");
		changePassword.setFont(new Font("Arial", 20));
		
		TextField username=new TextField();
		username.setPromptText("password");
		
		TextField password=new TextField();
		password.setPromptText("new password");
		
		TextField comfirmPassword=new TextField();
		comfirmPassword.setPromptText("comfirm Password");
		
		pass.getChildren().addAll(changePassword,username,password,comfirmPassword);
		pass.setPadding(new Insets(0,40,0,40));
		
		
		
		//cancel apply.
		HBox cancelApply=new HBox(20);
		cancel= new Button("cancel");
		cancel.getStyleClass().add("sideButton");
		
		apply= new Button("Apply");
		apply.getStyleClass().add("sideButton");
		
		
		cancelApply.getChildren().addAll(apply,cancel);
		cancelApply.setAlignment(Pos.CENTER_RIGHT);
		
		
		VBox center= new VBox(40);
		center.getChildren().addAll(settingsLabel,Languege,color,pass,cancelApply);
		center.setAlignment(Pos.TOP_CENTER);
		center.getStyleClass().add("settings");
		center.setMaxSize(300, 800);
		
		
		
		mainLayout.setCenter(center);
		
	}

	
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==null) {
			
			
			
		}
	}

	public BorderPane getMainLayout() {
		return mainLayout;
	}

	public void setMainLayout(BorderPane mainLayout) {
		this.mainLayout = mainLayout;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Button getApply() {
		return apply;
	}

	public void setApply(Button apply) {
		this.apply = apply;
	}

	public Button getCancel() {
		return cancel;
	}

	public void setCancel(Button cancel) {
		this.cancel = cancel;
	}
	
	

	
}
