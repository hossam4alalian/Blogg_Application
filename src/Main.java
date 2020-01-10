import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import backend_request.HttpRequest;
import backend_request.Json;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
	static Scene scene;
	
	static int page=1;
	
	static ComboBox<String>archive;
	
	static Settings settings=new Settings();
	
	static Menus menus = new Menus();
	static ExploreBloggs explore=new ExploreBloggs();
	static Blogg blogg = new Blogg();
	
	static Login login = new Login();
	
	CreateAccount createAccount=new CreateAccount();
	
	static Stage window;
	static HBox mainLayout;
	static VBox center;
	
	//McDubRVdlwAzu3z4
	
	static int currentBlogg=-1;//this is tjanstId
	
	public static void main(String[] args) {
		launch(args);
		
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		window = primaryStage;
		
		mainLayout = new HBox();
		
		center=new VBox(6);
		center.getChildren().addAll(menus.getTopMenu(),explore.getScrollPane());
		
		
		mainLayout.getChildren().addAll(menus.getSideMenu(),center);
		
		loadExplore();
		
		scene= new Scene(mainLayout, 1280, 720);
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
				window.setScene(login.getScene());
			}
		});
		
		menus.getLogin().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(login.isLoggedIn()) {
					Main.menus.getLogin().setText("Log In");
					login.setUsername("Guest");
					login.setBloggId("null");
					
					menus.getUsername().setText(login.getUsername());
					menus.getLikes().setText("");
					
					login.setLoggedIn(false);
					
					if(page==2) {
						blogg.refresh();
					}
					
				}
				else {
					window.setScene(login.getScene());
				}
			}
		});
		menus.getSetings().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(login.isLoggedIn()) {
					settings.getPass().getChildren().clear();
					settings.createChangePassword();
				}
				else {
					settings.getPass().getChildren().clear();
				}
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
					
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("");
					alert.setHeaderText("You need to be a user to use this page!");
					//alert.setContentText("Are you ok with this?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){
					    // ... user chose OK
						window.setScene(login.getScene());
					} else {
					    return;
					}
				}else {
					currentBlogg=Integer.parseInt(login.getBloggId());
					//currentBlogg=13;
					loadBlogg();
				}
			}
		});
	}
	
	public static void loadBlogg() {
		if(settings.getColorTheme()=="Light") {
			menus.getExploreBlogg().getStyleClass().remove("topButtonOn");
		}
		else {
			menus.getExploreBlogg().getStyleClass().remove("topButtonOnDark");
		}
		menus.getExploreBlogg().getStyleClass().add("topButton");
		
		menus.getLeftTop().getChildren().remove(explore.getRefreshField());
		if(page==2) {
			blogg.refresh();
			return;
		}
		//remove explore nodes
		Main.center.getChildren().removeAll(Main.explore.getScrollPane());
		blogg.getAddField().getChildren().removeAll(blogg.getContent(),blogg.getHashtagField());
		menus.getLeftTop().getChildren().add(blogg.getRefreshField());
		menus.getTopMenu().getChildren().add(menus.getRightTop());
       	Main.center.getChildren().addAll(blogg.getAddField(),Main.blogg.getScrollPane());
       	archive();
       	blogg.refresh();
       	page=2;
       	//add
       	
       	
       	//center.getChildren().add(1,blogg.getLabelTitle());
    }
	public static void loadExplore() {
		if(page==2) {
			menus.getLeftTop().getChildren().add(explore.getRefreshField());
		}
		menus.getTopMenu().getChildren().remove(menus.getRightTop());
		menus.getLeftTop().getChildren().remove(blogg.getRefreshField());
		
		if(settings.getColorTheme()=="Light") {
			menus.getExploreBlogg().getStyleClass().add("topButtonOn");
		}
		else {
			menus.getExploreBlogg().getStyleClass().add("topButtonOnDark");
		}
		
		if(page==1) {
			explore.refresh();
			return;
		}
		//remove blogg nodes.
		Main.center.getChildren().removeAll(Main.blogg.getScrollPane(),Main.blogg.getAddField(),blogg.getLabelTitle());
		blogg.getAddField().getChildren().removeAll(blogg.getContent(), blogg.getButtons(), blogg.getPost(),blogg.getAdd(),blogg.getHashtagField());
		
       	Main.center.getChildren().addAll(Main.explore.getScrollPane());
       	
       	page=1;
       	
       	explore.refresh();
       	
       	menus.getLeftTop().getChildren().remove(archive);
       	
       	
	}

	static String selected="";
	public static void archive() {
	
       	List<String> allDates = new ArrayList<>();
       	SimpleDateFormat monthDate = new SimpleDateFormat("yyyy-MM");
       	Calendar cal = Calendar.getInstance();
       	
			archive=new ComboBox<String>();
			archive.getStyleClass().add("settingsButton");
			
			allDates.add("All posts");
			
			/*for (int i = 1; i <= 10; i++) {
				
				String month_name1= monthDate.format(cal.getTime());
				
	       	     allDates.add(month_name1);
	       	     cal.add(Calendar.MONTH, -1);
	       	 }
			
			
			*/
			try {
					String blogg = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg);
					
					JSONObject json=new JSONObject(blogg);
					
					JSONArray inlagg=json.getJSONArray("bloggInlagg");
					if(inlagg.length()>0) {
						String firstDate = inlagg.getJSONObject(0).getString("datum");
						 boolean onGoing=true;
						while(onGoing) {
							String firstMonth = firstDate.substring(0,7);
							
							String month_name1= monthDate.format(cal.getTime());
							
								allDates.add(month_name1);
							
								cal.add(Calendar.MONTH, -1);
								if(month_name1.equals(firstMonth)) {
									onGoing=false;
									break;
								}
						}
					}
				} 
       		catch (Exception ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
			
			archive.setValue("Archive");
			archive.getItems().addAll(allDates);
			menus.getLeftTop().getChildren().add(archive);
			
			
			EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
			  public void handle(ActionEvent e) { 
				  selected= archive.getValue(); 
			     
			      
			      try {
						String blogg = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg);
					
						Main.blogg.getScrollPaneBox().getChildren().clear();
						JSONObject json=new JSONObject(blogg);
						
						JSONArray inlagg=json.getJSONArray("bloggInlagg");
						
						for(int i=inlagg.length()-1;i>=0;i--) {
								String inlaggStr = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg="+inlagg.getJSONObject(i).getString("id"));
								
								JSONObject inlaggJson=Json.toJSONObject(inlaggStr);
								
								String date=inlaggJson.getString("datum");
								
								String dateMonth = date.substring(0,7);
								
								if(selected.equals(dateMonth)) {
									String text=inlaggJson.getString("innehall");
									String title=inlaggJson.getString("titel");
									String postId=inlaggJson.getString("id");
									
									JSONArray array=inlaggJson.getJSONArray("gillningar");
									
									int likesAmount=array.length();
									
									Main.blogg.post(postId,title, text, likesAmount);
								}
								else if(selected.equals("All posts")) {
									String text=inlaggJson.getString("innehall");
										String title=inlaggJson.getString("titel");
										
										JSONArray array=inlaggJson.getJSONArray("gillningar");
										String postId=inlaggJson.getString("id");
										
										int likesAmount=array.length();
											
										//comments();
										Main.blogg.post(postId,title, text, likesAmount);
								}
							}
					} 
		       		catch (Exception ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
			  } 
			  	}; 
				 archive.setOnAction(event);
			}
}
