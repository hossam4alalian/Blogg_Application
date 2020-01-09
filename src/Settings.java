import javax.swing.text.html.Option;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
	
	private VBox pass;
	
	private String colorTheme="Light";
	
	public Settings() {
		scene();
	}
	
	public void scene() {
		mainLayout= new BorderPane();
		scene =new Scene(mainLayout,  1280, 720);
		scene.getStylesheets().add("main.css");
		settings();
		
	}
	
	public void settings() {
		Label settingsLabel= new Label("Settings:");
		settingsLabel.setFont(new Font("Arial", 30));
		settingsLabel.setPadding(new Insets(80, 20, 20, 20));
		
		ComboBox<String>languege=new ComboBox<String>();
		languege.getStyleClass().add("settingsButton");
		languege.setValue("English");
		languege.getItems().addAll("Swedish");
		languege.getItems().addAll("English");
		languege.getItems().addAll("Arabic");
		
		ComboBox<String>color=new ComboBox<String>();
		color.getStyleClass().add("settingsButton");
		color.setValue("Light");
		
		color.getItems().addAll("Light");
		color.getItems().addAll("Dark");
		color.getItems().addAll("Red");
		color.getItems().addAll("Blue");
		
		
		
		color.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				colorTheme=color.getValue();
				System.out.println(colorTheme);
				if(Main.page==1) {
					Main.explore.refresh();
				}
				if(Main.page==2) {
					Main.blogg.refresh();
				}
				
				if(Main.settings.getColorTheme()=="Light") {
					Main.menus.getTopMenu().getStyleClass().clear();
					Main.menus.getTopMenu().getStyleClass().add("topMenu");
					
					Main.mainLayout.getStyleClass().clear();
					Main.mainLayout.getStyleClass().add("center");
					
					Main.menus.getSearchPost().getStyleClass().add("searchDark");
				}
				if(Main.settings.getColorTheme()=="Dark") {
					Main.menus.getTopMenu().getStyleClass().clear();
					Main.menus.getTopMenu().getStyleClass().add("topMenuDark");
					
					Main.mainLayout.getStyleClass().clear();
					Main.mainLayout.getStyleClass().add("centerDark");
					
					
					
					//this shit is no good.
					Main.menus.setSearchPost(new TextField(""));
					
					Main.menus.getSearchPost().getStylesheets().clear();
					Main.menus.getSearchPost().getStyleClass().add("searchDark");
					Main.menus.getRightTop().getChildren().remove(1);
					Main.menus.getRightTop().getChildren().add(Main.menus.getSearchPost());
				}
				
				if(Main.page==1) {
					if(Main.settings.getColorTheme()=="Light") {
						Main.menus.getExploreBlogg().getStyleClass().add("topButtonOn");
					}
					else {
						Main.menus.getExploreBlogg().getStyleClass().add("topButtonOnDark");
					}
				}
				
				
			}
		});
		
		pass=new VBox(20);
		pass.setAlignment(Pos.CENTER);
		
		/*Label changePassword= new Label("Change password");
		changePassword.setFont(new Font("Arial", 20));
		
		PasswordField oldPassword=new PasswordField();
		oldPassword.setPromptText("old password");
		
		PasswordField password=new PasswordField();
		password.setPromptText("new password");
		
		PasswordField comfirmPassword=new PasswordField();
		comfirmPassword.setPromptText("comfirm Password");
		
		pass.getChildren().addAll(changePassword,oldPassword,password,comfirmPassword);*/
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
		center.getChildren().addAll(settingsLabel,languege,color,pass,cancelApply);
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
	
	
	
	
	public void createChangePassword() {
		Label changePassword= new Label("Change password");
		changePassword.setFont(new Font("Arial", 20));
		
		PasswordField oldPassword=new PasswordField();
		oldPassword.setPromptText("old password");
		
		PasswordField password=new PasswordField();
		password.setPromptText("new password");
		
		PasswordField comfirmPassword=new PasswordField();
		comfirmPassword.setPromptText("comfirm Password");
		
		pass.getChildren().addAll(changePassword,oldPassword,password,comfirmPassword);
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

	public VBox getPass() {
		return pass;
	}

	public void setPass(VBox pass) {
		this.pass = pass;
	}

	public String getColorTheme() {
		return colorTheme;
	}

	public void setColorTheme(String colorTheme) {
		this.colorTheme = colorTheme;
	}
	
	
}
