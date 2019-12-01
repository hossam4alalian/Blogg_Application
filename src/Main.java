import java.awt.Menu;

import org.json.JSONObject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
	static Scene scene;
	
	static int page=1;
	
	static ComboBox<String>archive;
	
	static Menus menus = new Menus();
	static ExploreBloggs explore=new ExploreBloggs();
	static Blogg blogg = new Blogg();
	
	Login login = new Login();
	Settings settings=new Settings();
	CreateAccount createAccount=new CreateAccount();
	
	static Stage window;
	static HBox mainLayout;
	static VBox center;
	
	static int currentBlogg=-1;
	public static void main(String[] args) {
		launch(args);
		
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		
		mainLayout = new HBox();
		
		center=new VBox();
		center.getChildren().addAll(menus.getTopMenu(),explore.getScrollPane(),explore.getRefreshField());
		
		
		mainLayout.getChildren().addAll(menus.getSideMenu(),center);
		
		scene= new Scene(mainLayout, 800, 600);
		scene.getStylesheets().add("main.css");
		window.setScene(scene);
		window.show();
		
		
		settings.getApply().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				window.setScene(scene);
			}
		});
		settings.getCancel().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				window.setScene(scene);
			}
		});
		
		login.getSkip().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				window.setScene(scene);
			}
		});
		login.getCreateAccount().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				window.setScene(createAccount.getScene());
			}
		});
		
		createAccount.getBack().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				window.setScene(scene);
			}
		});
		
		menus.getLogin().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(login.isLoggedIn()) {
					System.out.println("log out!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					Main.menus.getLogin().setText("Log In");
					login.setUsername("Guest");
					login.setBloggId("null");
					
					menus.getUsername().setText(login.getUsername());
							
					login.setLoggedIn(false);
				}
				else {
					window.setScene(login.getScene());
				}
			}
		});
		
		menus.getSetings().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				window.setScene(settings.getScene());
			}
		});
		menus.getExploreBlogg().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				loadExplore();
			}
		});
		
		menus.getYourBlogg().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				window.setScene(blogg.getScene());
				System.out.println("blah");
			}
		});
	}
	
	public static void loadBlogg() {
		Main.center.getChildren().remove(Main.explore.getScrollPane());
       	Main.center.getChildren().remove(Main.explore.getRefreshField());
       	
       	Main.center.getChildren().addAll(Main.blogg.getScrollPane(),Main.blogg.getRefreshField());
       	page=2;
       	System.out.println(page);
       	
       	
			archive=new ComboBox<String>();
			archive.getStyleClass().add("settingsButton");
			archive.setValue("this month");
			
			for (int i=0; i<12; i++) {
				archive.getItems().addAll("blah "+ i);
			}
			menus.getLeftTop().getChildren().add(archive);
			System.out.println("blah");
		
       	
	}
	public static void loadExplore() {
		Main.center.getChildren().remove(Main.blogg.getScrollPane());
       	Main.center.getChildren().remove(Main.blogg.getRefreshField());
       	Main.center.getChildren().addAll(Main.explore.getScrollPane(),Main.explore.getRefreshField());
       	page=1;
       	System.out.println(page);
       	menus.getLeftTop().getChildren().remove(archive);
	}
	
}
