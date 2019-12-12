

import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import org.json.JSONArray;
import org.json.JSONObject;

import backend_request.HttpRequest;
import backend_request.Json;
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
	
	
	

	private HBox refreshField;
	
	private ScrollPane scrollPane;
	private VBox scrollPaneBox= new VBox();
	
	public ExploreBloggs() {
		
		refresh();
		
		//bloggs();
		//addBlogg("niles is good", "Niles ahmad", "142");
		scrollPaneSetup();
		
		//window.setMinWidth(600);
		//window.setMinHeight(300);
		
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
			
			
			refresh();
			
			
		});
		
		
		refreshField = new HBox();
		refreshField.getChildren().addAll(refresh);
		refreshField.getStyleClass().add("refreshField");
		
		
	}
	
	
	public void refresh() {
		
		scrollPaneBox.getChildren().clear();
		
		try {
			String str = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON");
			
			
			
			JSONObject json=Json.toJSONObject(str);
			
			String name=json.getString("anamn");
			
			JSONArray array=json.getJSONArray("bloggar");
			for(int i=array.length()-1;i>=0;i--) {
				String title=array.getJSONObject(i).getString("titel");
				String bloggId=array.getJSONObject(i).getString("bloggId");
				
				
				String blogg = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+bloggId);
				
				JSONObject inlagg=Json.toJSONObject(blogg);
				JSONArray bloggInlagg=inlagg.getJSONArray("bloggInlagg");
				
				
				addBlogg(title, name, ""+bloggInlagg.length(),bloggId);
			}
			
		
		} catch (Exception ee ) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
			
			addBlogg("bloggNiles", "niles", "142","0");
			addBlogg("blogg monkey", "ahmad", "142","0");
			addBlogg("hello blogg", "niles", "142","0");
			addBlogg("this is a blogg", "niles", "142","0");
			
		}
	}
	
	
	
	public void addBlogg(String title, String user, String postAmount, String bloggId) {
		Label bloggsName = new Label(title);
		bloggsName.getStyleClass().add("leftBloggText");
		

		Label username= new Label("By: "+user);
		username.getStyleClass().add("leftBloggText");
		
		Label posts= new Label("Post: "+postAmount);
		posts.getStyleClass().add("leftBloggText");
		
		
		
		VBox blogg= new VBox();
		
		blogg.setUserData(bloggId);
		
		
		blogg.getChildren().addAll(bloggsName, username, posts);
		blogg.setPrefSize(2000, 100);
		blogg.getStyleClass().add("exploreBlogg");
		blogg.setOnMouseClicked( ( e ) ->
        {
       	 Main.currentBlogg=Integer.parseInt(blogg.getUserData().toString());
       	 
       	 Main.loadBlogg();
       	 
       	 
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


	public ScrollPane getScrollPane() {
		return scrollPane;
	}




	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}




	public HBox getRefreshField() {
		return refreshField;
	}




	public void setRefreshField(HBox refreshField) {
		this.refreshField = refreshField;
	}


	
	
	

}
