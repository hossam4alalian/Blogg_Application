

import java.awt.Menu;

import org.json.JSONObject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application{
	Blogg blogg = new Blogg();
	Login login = new Login();
	Menus menus = new Menus();
	
	ExploreBloggs explore=new ExploreBloggs();
	Settings settings=new Settings();
	
	static Stage window;
	HBox mainLayout;
	
	static int currentBlogg=-1;
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		
		mainLayout = new HBox();
		Scene scene= new Scene(mainLayout, 800, 600);
		window.setScene(explore.getScene());
		window.show();
		
		
		settings.getApply().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				window.setScene(explore.getScene());
			}
		});
		settings.getCancel().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				window.setScene(explore.getScene());
			}
		});
		
		login.getSkip().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				window.setScene(explore.getScene());
			}
		});
		
		blogg.getMenus().getLogin().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				window.setScene(login.getScene());
			}
		});
		
		blogg.getMenus().getSetings().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				window.setScene(settings.getScene());
			}
		});
		
		menus.getExploreBlogg().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println("sadsadsad");
				window.setScene(explore.getScene());
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

}
