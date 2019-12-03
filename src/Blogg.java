


import org.json.JSONArray;
import org.json.JSONObject;

import backend_request.HttpRequest;
import backend_request.Json;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;

public class Blogg {

	
	private ScrollPane scrollPane;
	private HBox refreshField;
	private VBox addField;
	
	private VBox content;
	
	private HBox buttons;
	private Button post;
	private Button add;
	
	private VBox scrollPaneBox= new VBox();
	
	private String bloggId="null";//this is bloggId.
	public Blogg() {
		addPost();
		scrollPaneSetup();
		
		//window.setMinWidth(600);
		//window.setMinHeight(300);
		
	}
	
	public void scrollPaneSetup() {
		scrollPane= new ScrollPane();
		scrollPane.setContent(getScrollPaneBox());
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

	 double orgSceneX, orgSceneY;
	    double orgTranslateX, orgTranslateY;
	
	public void addPost() {
		
		addField = new VBox(20);
		addField.getStyleClass().add("addField");
		add = new Button("What is on your mind?");
		add.setOnAction(e -> {
			TextField mainTitle = new TextField();
			mainTitle.setPromptText("Title for your post");
			mainTitle.setMaxWidth(600);
			mainTitle.getStyleClass().add("addContent");
			
			addTextLimiter(mainTitle, 80);
			content = new VBox(20);
			content.getStyleClass().add("addContent");
			content.getChildren().add(mainTitle);
			
			Button addText = new Button("Add Text");
			addText.setOnAction(ee -> {
				TextArea text = new TextArea();
				text.setMaxWidth(800);
				text.setWrapText(true);
				
				
				
				text.setOnDragDetected(new EventHandler<MouseEvent>() {
				    public void handle(MouseEvent event) {
				        /* drag was detected, start a drag-and-drop gesture*/
				        /* allow any transfer mode */
				        Dragboard db = text.startDragAndDrop(TransferMode.ANY);
				        
				        /* Put a string on a dragboard */
				        ClipboardContent content = new ClipboardContent();
				        content.putString(text.getText());
				        db.setContent(content);
				        
				        event.consume();
				    }
				});
				
				text.getStyleClass().add("addField");
				text.setPromptText("Text");
				
				
				
				
				content.getChildren().add(text);
			});
			
			Button addPic = new Button("Add Pic");
			addPic.setOnAction(ee -> {
				TextArea src = new TextArea();
				src.setMaxWidth(800);
				src.setWrapText(true);
				src.getStyleClass().add("addField");
				src.setPromptText("Image Source");
				
				src.setOnDragDetected(new EventHandler<MouseEvent>() {
				    public void handle(MouseEvent event) {
				        /* drag was detected, start a drag-and-drop gesture*/
				        /* allow any transfer mode */
				        Dragboard db = src.startDragAndDrop(TransferMode.ANY);
				        
				        /* Put a string on a dragboard */
				        ClipboardContent content = new ClipboardContent();
				        content.putString(src.getText());
				        db.setContent(content);
				        
				        event.consume();
				    }
				});
				
				String mediaSrc= src.getText();
				//Media media = new Media(mediaSrc);
				
				System.out.println(mediaSrc);
				content.getChildren().addAll(src);
			});
			
			Button addTitle = new Button("Add Title");
			addTitle.setOnAction(ee -> {
				TextField text = new TextField();
				text.setMaxWidth(800);
				
				
				text.getStyleClass().add("addField");
				text.setPromptText("Title");
				
				text.setOnDragDetected(new EventHandler<MouseEvent>() {
				    public void handle(MouseEvent event) {
				        /* drag was detected, start a drag-and-drop gesture*/
				        /* allow any transfer mode */
				        Dragboard db = text.startDragAndDrop(TransferMode.ANY);
				        
				        /* Put a string on a dragboard */
				        ClipboardContent content = new ClipboardContent();
				        content.putString(text.getText());
				        db.setContent(content);
				        
				        event.consume();
				    }
				});
				
				addTextLimiter(text, 100);
				
				content.getChildren().add(text);
			});
			
			buttons = new HBox(20);
			buttons.getChildren().addAll(addText,addPic,addTitle);
			
			
			post = new Button("Post");
			post.setOnAction(ee -> {
				
				
				String str;
				try {
					
					String inlagg="";
					String title="nothing";
					for(int i=0;i<content.getChildren().size();i++) {
						if(i==0) {
							title=((TextField) content.getChildren().get(i)).getText();
							System.out.println(title);
							
						}
						else {
							
							String line=((TextArea) content.getChildren().get(i)).getText();
							System.out.println(line);
							inlagg+=line;
						}
					}
					
					str = HttpRequest.send("Blogg/funktioner/skapa.php","funktion=skapaInlagg&bloggId="+Main.currentBlogg+"&Title="+title+"&innehall="+inlagg);
					
					
					
					
					
				} catch (Exception eee) {
					// TODO Auto-generated catch block
					eee.printStackTrace();
				}
				
				
				
				
				addField.getChildren().removeAll(content, buttons, post);
				addField.getChildren().add(add);
			});
				addField.getChildren().addAll(content, buttons, post);
			
				addField.getChildren().remove(add);
				
		});
		
		
		
		
	}
	
	
	
	
	
	public static void addTextLimiter(final TextField text, final int maxLength) {
	    text.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
	            if (text.getText().length() > maxLength) {
	                String s = text.getText().substring(0, maxLength);
	                text.setText(s);
	            }
	        }
	    });
	}
	
	public void post(String title, String text, int likesAmount) {
		Label bloggsName = new Label(title);
		bloggsName.getStyleClass().add("leftBloggText");
		

		Label username= new Label(text);
		username.getStyleClass().add("leftBloggText");
		
		Label posts= new Label("Likes: "+likesAmount);
		posts.getStyleClass().add("leftBloggText");
		
		VBox blogg= new VBox();
		blogg.getChildren().addAll(bloggsName, username, posts);
		blogg.setPrefSize(2000, 100);
		blogg.getStyleClass().add("post");
		blogg.setOnMouseClicked( ( e ) ->
        {
       	 
       	 System.out.println("Left up");
        });
		
		if(getScrollPaneBox().getChildren().size()==0){
			HBox bloggar = new HBox();
			bloggar.getChildren().add(blogg);
			getScrollPaneBox().getChildren().add(bloggar);
		}
		else {
			/*if(getScrollPaneBox().getChildren().size()-1==0) {
				return;
			}*/
			HBox lastBloggContainer=(HBox) getScrollPaneBox().getChildren().get(getScrollPaneBox().getChildren().size()-1);
			
			if(lastBloggContainer.getChildren().size()==1){
				HBox bloggar = new HBox();
				bloggar.getChildren().add(blogg);
				getScrollPaneBox().getChildren().add(bloggar);
			}
			else {
				System.out.println("Error!!!!!");
			}
		}
		
		
		
	}
	
	
	

	public void refresh() {
		getScrollPaneBox().getChildren().clear();
		
		try {
			String blogg = HttpRequest.send("nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg);
		
			System.out.println(blogg);
			
			JSONObject json=new JSONObject(blogg);
			
			String bloggTitle=json.getString("titel");
			bloggId=json.getString("bloggId");
			System.out.println(bloggId);
			
			
			/*Label labelTitle=new Label(bloggTitle);
			labelTitle.setAlignment(Pos.CENTER);
			getScrollPaneBox().getChildren().add(labelTitle);*/
			
			
			JSONArray inlagg=json.getJSONArray("bloggInlagg");
			
			for(int i=0;i<inlagg.length();i++) {
				String inlaggStr = HttpRequest.send("nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg="+inlagg.getJSONObject(i).getString("id"));
				//System.out.println(inlaggStr);
				
				JSONObject inlaggJson=Json.toJSONObject(inlaggStr);
				
				String text=inlaggJson.getString("innehall");
				String title=inlaggJson.getString("titel");
				
				JSONArray array=inlaggJson.getJSONArray("gillningar");
				System.out.println(array.length());
				int likesAmount=array.length();
					
				//comments();
				
				post(title, text, likesAmount);
				
			}
			
			
			
			if(Main.login.getBloggId().equals(Main.currentBlogg+"") /*|| currentBlogg==13*/) {
				getAddField().getChildren().removeAll(getButtons(),getPost());
				getAddField().getChildren().add(getAdd());
			}
			
			
			/*JSONObject json=Json.toJSONObject(str);
			
			String text=json.getString("innehall");
			String title=json.getString("titel");
			
			
			JSONArray array=json.getJSONArray("gillningar");
			System.out.println(array.length());
			int likesAmount=array.length();
				
			//comments();
			
			addPost(title, text, likesAmount);*/
		
		} catch (Exception ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}
		
		
	}
	

	
	
	public void comments(JSONArray array) {
		try {
			String str = HttpRequest.send("nyckel=JIOAJWWNPA259FB2&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg=3");
		
			System.out.println(str);
			JSONObject json=Json.toJSONObject(str);
		
			array=json.getJSONArray("kommentarer");
			for(int i=0;i<array.length();i++) {
				JSONArray array2=array;
				comments(array2);
			}
			
			System.out.println(array.length());
			
			
				
				
			
			
		
		} catch (Exception ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
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

	public VBox getAddField() {
		return addField;
	}

	public void setAddField(VBox addField) {
		this.addField = addField;
	}


	public HBox getButtons() {
		return buttons;
	}

	public void setButtons(HBox buttons) {
		this.buttons = buttons;
	}

	public Button getPost() {
		return post;
	}

	public void setPost(Button post) {
		this.post = post;
	}

	public VBox getContent() {
		return content;
	}

	public void setContent(VBox content) {
		this.content = content;
	}


	public Button getAdd() {
		return add;
	}

	public void setAdd(Button add) {
		this.add = add;
	}

	public VBox getScrollPaneBox() {
		return scrollPaneBox;
	}

	public void setScrollPaneBox(VBox scrollPaneBox) {
		this.scrollPaneBox = scrollPaneBox;
	}

	
	

}
