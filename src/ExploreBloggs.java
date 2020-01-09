
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;

import backend_request.HttpRequest;
import backend_request.Json;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ExploreBloggs {
	
	private HBox refreshField;
	
	private ScrollPane scrollPane;
	private VBox scrollPaneBox= new VBox(40);
	
	private Image refreshImg;
	
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
		
		
	
		
		 FileInputStream input;
			try {
				input = new FileInputStream("refresh.png");
				refreshImg = new Image(input);
				ImageView imageView = new ImageView(refreshImg);
				imageView.setFitHeight(18);
				imageView.setFitWidth(15);
				Button refresh = new Button("",imageView);
				
				
				refresh.setOnAction(e -> {
					refresh();
				});
				
				
				refreshField = new HBox();
				refreshField.getChildren().addAll(refresh);
				refreshField.getStyleClass().add("refreshField");
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	
	static VBox blogg= new VBox(20);
	public void addBlogg(String title, String user, String postAmount, String bloggId) {
		Label bloggsName = new Label(title);
		
		if(Main.settings.getColorTheme()=="Light") {
			bloggsName.getStyleClass().add("exploreBloggTitle");
		}
		if(Main.settings.getColorTheme()=="Dark") {
			bloggsName.getStyleClass().add("exploreBloggTitleDark");
		}

		//Label username= new Label("By: "+user);
		//username.getStyleClass().add("exploreBloggUser");
		
		Label posts= new Label("Post: "+postAmount);
		if(Main.settings.getColorTheme()=="Light") {
			posts.getStyleClass().add("exploreBloggPosts");
		}if(Main.settings.getColorTheme()=="Dark") {
			posts.getStyleClass().add("exploreBloggPostsDark");
		}
		
		VBox blogg= new VBox(20);
		blogg.setPadding(new Insets(20));
	
		blogg.setUserData(bloggId);
		
		blogg.getChildren().addAll(bloggsName, posts);
		blogg.setPrefSize(2000, 100);
		
		if(Main.settings.getColorTheme()=="Light") {
			blogg.getStyleClass().add("exploreBlogg");	
		}
		if(Main.settings.getColorTheme()=="Dark") {
			blogg.getStyleClass().add("exploreBloggDark");
		}
		
		blogg.setOnMouseClicked( ( e ) ->
        {
       	 Main.currentBlogg=Integer.parseInt(blogg.getUserData().toString());
       	 
       	 Main.loadBlogg();
       	 
        });
		
		if(scrollPaneBox.getChildren().size()==0){
			HBox bloggar = new HBox(40);
			bloggar.getChildren().add(blogg);
			scrollPaneBox.getChildren().add(bloggar);
		}
		else {
			HBox lastBloggContainer=(HBox) scrollPaneBox.getChildren().get(scrollPaneBox.getChildren().size()-1);
			
			if(lastBloggContainer.getChildren().size()==1) {
				lastBloggContainer.getChildren().add(blogg);
			}
			else if(lastBloggContainer.getChildren().size()==2){
				HBox bloggar = new HBox(40);
				bloggar.getChildren().add(blogg);
				scrollPaneBox.getChildren().add(bloggar);
			}
			else {
				System.out.println("Error!!!!!");
			}
		}
		
		scrollPaneBox.setPadding(new Insets(20));
		
		
		if(Main.settings.getColorTheme()=="Light") {
			scrollPaneBox.getStyleClass().clear();
			scrollPaneBox.getStyleClass().add("center");
			
		}
		if(Main.settings.getColorTheme()=="Dark") {
			scrollPaneBox.getStyleClass().clear();
			scrollPaneBox.getStyleClass().add("centerDark");
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
