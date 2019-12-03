import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import backend_request.HttpRequest;
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
	
	static Login login = new Login();
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
		
		scene= new Scene(mainLayout, 1000, 800);
		scene.getStylesheets().add("main.css");
		window.setScene(scene);
		
		window.show();
		//window.setMaximized(true);
		
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
					System.out.println("log out!!!!!!!!!");
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
				if(login.getBloggId()=="null") {
					System.out.println("need to login");
				}else {
					currentBlogg=Integer.parseInt(login.getBloggId());
					loadBlogg();
				}
			}
		});
	}
	
	public static void loadBlogg() {
		if(page==2) {
			return;
		}
		
		//remove explore nodes
		Main.center.getChildren().removeAll(Main.explore.getScrollPane(),Main.explore.getRefreshField());
		blogg.getAddField().getChildren().removeAll(blogg.getContent());
		
		
		if(login.getBloggId().equals(currentBlogg+"")) {
			blogg.getAddField().getChildren().removeAll(blogg.getButtons(),blogg.getPost());
			blogg.getAddField().getChildren().add(blogg.getAdd());
		}
		
       	//adds 
		
       	Main.center.getChildren().addAll(blogg.getAddField(),Main.blogg.getScrollPane(),Main.blogg.getRefreshField());
       	page=2;
       	System.out.println(page);
       	
       	blogg.refresh();
       	
       	archive();
       
		
       	
	}
	public static void loadExplore() {
		if(page==1) {
			return;
		}
		//remove blogg nodes.
		Main.center.getChildren().removeAll(Main.blogg.getScrollPane(),Main.blogg.getRefreshField(),Main.blogg.getAddField());
		blogg.getAddField().getChildren().removeAll(blogg.getContent(), blogg.getButtons(), blogg.getPost(),blogg.getAdd());
       	Main.center.getChildren().addAll(Main.explore.getScrollPane(),Main.explore.getRefreshField());
       	page=1;
       	System.out.println(page);
       	menus.getLeftTop().getChildren().remove(archive);
	}
	
	public static void archive() {
		
		
       	List<String> allDates = new ArrayList<>();
       	SimpleDateFormat monthDate = new SimpleDateFormat("MMM-yyyy");
       	Calendar cal = Calendar.getInstance();
       	/*try {
			cal.setTime(monthDate.parse(maxDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
       	
       	
			archive=new ComboBox<String>();
			archive.getStyleClass().add("settingsButton");
			
			
			
			
			for (int i = 1; i <= 10; i++) {
				
				String month_name1= monthDate.format(cal.getTime());
				
	       	     allDates.add(month_name1);
	       	     cal.add(Calendar.MONTH, -1);
	       	     
	       	}
			archive.setValue("Archive");
			archive.getItems().addAll(allDates);
			menus.getLeftTop().getChildren().add(archive);
			
	}
	
}
