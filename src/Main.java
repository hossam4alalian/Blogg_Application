

import org.json.JSONObject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application{

	Login login = new Login();
	ExploreBloggs explore=new ExploreBloggs();
	Settings settings=new Settings();
	
	Stage window;
	HBox mainLayout;
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		
		mainLayout = new HBox();
		Scene scene= new Scene(mainLayout, 800, 600);
		window.setScene(settings.getScene());
		window.show();
		
		
		
		
	}

}
