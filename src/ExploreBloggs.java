

import org.json.JSONArray;
import org.json.JSONObject;

import backend_request.Json;
import backend_request.Post;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExploreBloggs {
	
	private Menus menus = new Menus();
	
	private Scene scene;
	private HBox mainLayout;
	private VBox center;
	
	
	private ScrollPane scrollPane;
	private VBox scrollPaneBox= new VBox();
	
	public ExploreBloggs() {
		center=new VBox();
		center.getChildren().add(menus.getTopMenu());
		mainLayout=new HBox(40);
		
		//bloggs();
		//addBlogg("niles is good", "Niles ahmad", "142");
		scrollPaneSetup();
		
		
		
		
		
		mainLayout.getChildren().addAll(menus.getSideMenu(),center);
		
		scene=new Scene(mainLayout,800,600);
		scene.getStylesheets().add("main.css");
		//window.setMinWidth(600);
		//window.setMinHeight(300);
		
	}
	
	
	public void bloggs(){
		Label bloggsName = new Label("Give me kisses");
		bloggsName.getStyleClass().add("leftBloggText");
		

		Label username= new Label("by: Blah");
		username.getStyleClass().add("leftBloggText");
		
		Label posts= new Label("Posts: 3");
		posts.getStyleClass().add("leftBloggText");
		
		VBox leftBlogg= new VBox();
		leftBlogg.getChildren().addAll(bloggsName, username, posts);
		leftBlogg.setPrefSize(2000, 100);
		leftBlogg.getStyleClass().add("leftBlogg");
		leftBlogg.setOnMouseClicked( ( e ) ->
        {
       	 
       	 System.out.println("Left up");
        } );
		
		
		
		Label bloggsName2 = new Label("Give me kisses");
		bloggsName2.getStyleClass().add("rightBloggText");
		
		Label username2= new Label("by: Blah");
		username2.getStyleClass().add("rightBloggText");
		
		Label posts2= new Label("Posts: 6");
		posts2.getStyleClass().add("rightBloggText");

		
		VBox rightBlogg= new VBox();
		rightBlogg.getChildren().addAll(bloggsName2, username2, posts2);
		rightBlogg.setPrefSize(2000, 100);
		rightBlogg.getStyleClass().add("rightBlogg");
		
		
		HBox bloggar = new HBox();
		bloggar.getChildren().addAll(leftBlogg, rightBlogg);
		
		//test
		Label bloggsNameT = new Label("Give me kisses");
		bloggsNameT.getStyleClass().add("leftBloggText");
		

		Label usernameT= new Label("by: Blah");
		usernameT.getStyleClass().add("leftBloggText");
		
		Label postsT= new Label("Posts: 3");
		postsT.getStyleClass().add("leftBloggText");
		
		VBox leftBloggT= new VBox();
		leftBloggT.getChildren().addAll(bloggsNameT, usernameT, postsT);
		leftBloggT.setPrefSize(2000, 100);
		leftBloggT.getStyleClass().add("leftBlogg");
		
		
		
		Label bloggsName2T = new Label("Give me kisses");
		bloggsName2T.getStyleClass().add("rightBloggText");
		
		Label username2T= new Label("by: Blah");
		username2T.getStyleClass().add("rightBloggText");
		
		Label posts2T= new Label("Posts: 6");
		posts2T.getStyleClass().add("rightBloggText");

		
		VBox rightBloggT= new VBox();
		rightBloggT.getChildren().addAll(bloggsName2T, username2T, posts2T);
		rightBloggT.setPrefSize(2000, 100);
		rightBloggT.getStyleClass().add("rightBlogg");
		
		
		HBox bloggarT = new HBox();
		bloggarT.getChildren().addAll(leftBloggT, rightBloggT);
		
		VBox scrollPaneBox= new VBox();
		scrollPaneBox.getChildren().addAll(bloggar, bloggarT);
		
		ScrollPane scrollPane= new ScrollPane();
		scrollPane.setContent(scrollPaneBox);
		scrollPane.setPannable(true);
		scrollPane.setPrefSize(bloggar.getWidth(), 1000);
		//scrollPane.setFitToWidth(true);

		scrollPane.fitToWidthProperty().set(true);
		
		
		scrollPane.setOnDragDetected(e -> {
			scrollPane.setCursor(Cursor.DEFAULT);
		});
		scrollPane.setStyle("-fx-background-color:transparent;");
		
		
		Button refresh = new Button("Refresh");
		
		HBox refreshField = new HBox();
		refreshField.getChildren().addAll(refresh);
		refreshField.getStyleClass().add("refreshField");
		
		center.getChildren().addAll(scrollPane, refreshField );
		
		
	}
	
	
	
	public void scrollPaneSetup() {
		scrollPane= new ScrollPane();
		scrollPane.setContent(scrollPaneBox);
		scrollPane.setPannable(true);
		scrollPane.setPrefSize(/*bloggar.getWidth()*/200, 1000);
		//scrollPane.setFitToWidth(true);

		scrollPane.fitToWidthProperty().set(true);
		
		
		scrollPane.setOnDragDetected(e -> {
			scrollPane.setCursor(Cursor.DEFAULT);
		});
		scrollPane.setStyle("-fx-background-color:transparent;");
		
		
		Button refresh = new Button("Refresh");
		
		refresh.setOnAction(e -> {
			
			
			
			
			try {
				String str = Post.send("nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=JSON");
			
				System.out.println(str);
				JSONObject json=Json.toJSONObject(str);
				
				String name=json.getString("anamn");
				
				JSONArray array=json.getJSONArray("bloggar");
				for(int i=0;i<array.length();i++) {
					String title=array.getJSONObject(i).getString("titel");
					addBlogg(title, name, "142");
				}
				
			
			} catch (Exception ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
			
			
			
			
			
			
			
		});
		
		
		HBox refreshField = new HBox();
		refreshField.getChildren().addAll(refresh);
		refreshField.getStyleClass().add("refreshField");
		
		center.getChildren().addAll(scrollPane, refreshField );
	}
	
	
	
	
	public void addBlogg(String title, String user, String postAmount) {
		Label bloggsName = new Label(title);
		bloggsName.getStyleClass().add("leftBloggText");
		

		Label username= new Label("By: "+user);
		username.getStyleClass().add("leftBloggText");
		
		Label posts= new Label("Post: "+postAmount);
		posts.getStyleClass().add("leftBloggText");
		
		VBox blogg= new VBox();
		blogg.getChildren().addAll(bloggsName, username, posts);
		blogg.setPrefSize(2000, 100);
		blogg.getStyleClass().add("leftBlogg");
		blogg.setOnMouseClicked( ( e ) ->
        {
       	 
       	 System.out.println("Left up");
        });
		
		if(scrollPaneBox.getChildren().size()==0){
			HBox bloggar = new HBox();
			bloggar.getChildren().add(blogg);
			scrollPaneBox.getChildren().add(bloggar);
		}
		else {
			HBox lastBloggContainer=(HBox) scrollPaneBox.getChildren().get(scrollPaneBox.getChildren().size()-1);
			
			if(lastBloggContainer.getChildren().size()==1) {
				lastBloggContainer.getChildren().add(blogg);
			}
			else if(lastBloggContainer.getChildren().size()==2){
				HBox bloggar = new HBox();
				bloggar.getChildren().add(blogg);
				scrollPaneBox.getChildren().add(bloggar);
			}
			else {
				System.out.println("Error!!!!!");
			}
		}
		
		
		
	}

	
	

	public Scene getScene() {
		return scene;
	}


	public void setScene(Scene scene) {
		this.scene = scene;
	}


	public Menus getMenus() {
		return menus;
	}


	public void setMenus(Menus menus) {
		this.menus = menus;
	}
	
	
	
	
	

}
