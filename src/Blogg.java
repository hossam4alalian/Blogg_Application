


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import backend_request.HttpRequest;
import backend_request.Json;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

public class Blogg {

	
	private ScrollPane scrollPane;
	private HBox refreshField;
	private VBox addField;
	
	private VBox content;
	
	private HBox buttons;
	private Button post;
	private Button add;
	
	private Label labelTitle;
	
	private Button hashtag;
	
	private HBox hashtagField;

	private TextArea src;
	
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
	
	
	File selectedFile;
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
				
				text.setUserData("1");
				
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
				
				src = new TextArea();
				
				//src.setMaxWidth(800);
				src.setWrapText(true);
				src.getStyleClass().add("addField");
				src.setPromptText("Image Source");
				
				
				src.setUserData("2");
				
				src.setOnDragDetected(new EventHandler<MouseEvent>() {
				    public void handle(MouseEvent event) {
				       
				        Dragboard db = src.startDragAndDrop(TransferMode.ANY);
				        
				        ClipboardContent content = new ClipboardContent();
				        content.putString(src.getText());
				        db.setContent(content);
				        
				        event.consume();
				    }
				});
				
				
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				
				
				 Button button = new Button("Select File");
			        button.setOnAction(eee -> {
			            selectedFile = fileChooser.showOpenDialog(null);
			            src.setText(selectedFile.getPath());
			        });

				
			
				
				content.getChildren().add(button);
				
				content.getChildren().add(src);
				
				

			});
			
			Button addTitle = new Button("Add Title");
			addTitle.setOnAction(ee -> {
				TextArea text = new TextArea();
				text.setMaxWidth(800);
				
				
				text.getStyleClass().add("addField");
				text.setPromptText("Title");
				
				text.setUserData("0");
				
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
				
				//addTextLimiter(text, 100);
				
				content.getChildren().add(text);
			});
			
			buttons = new HBox(20);
			buttons.getChildren().addAll(addText,addPic,addTitle);
			
			
			
			hashtagField = new HBox();
			hashtag = new Button("Add #Hashtag");
			hashtag.setOnAction(ee -> {
				TextArea hashtagWord = new TextArea();
				hashtagWord.setMaxWidth(150);
				addTextLimiterArea(hashtagWord, 20);
				hashtagWord.getStyleClass().add("hashtag");
				hashtagWord.setPromptText("Any word");
				
				hashtagField.getChildren().add(hashtagWord);
			});
			
			hashtagField.getChildren().add(hashtag);
			
			
			
			post = new Button("Post");
			post.setOnAction(ee -> {
				
				//final File file = new File(src.getText());
				
				MultipartUtility(src.getText());
				String str;
				try {
					
					String inlagg="";
					String title="";
					for(int i=0;i<content.getChildren().size();i++) {
						if(i==0) {
							title=((TextField) content.getChildren().get(i)).getText();
							
						}
						
						else {
							String line=((TextArea) content.getChildren().get(i)).getText();
							if(((TextArea)content.getChildren().get(i)).getUserData()=="0") {
								inlagg+="<!title"+line+">";
							}
							else if(((TextArea)content.getChildren().get(i)).getUserData()=="1") {
								inlagg+="<!text"+line+">";
							}
							else if(((TextArea)content.getChildren().get(i)).getUserData()=="2") {
								inlagg+="<!img"+line+">";
							}
						}
					}
					
					//hashtag!!!!!
					for(int i=1;i<hashtagField.getChildren().size();i++) {
						String line=((TextArea) hashtagField.getChildren().get(i)).getText();
						inlagg+="<!tag"+line+">";
					}
					
					
					
					str = HttpRequest.send("Blogg/funktioner/skapa.php","funktion=skapaInlagg&bloggId="+Main.currentBlogg+"&Title="+title+"&innehall="+inlagg);
					
					
				} catch (Exception eee) {
					// TODO Auto-generated catch block
					eee.printStackTrace();
				}
				
				
				addField.getChildren().removeAll(content, buttons,hashtagField, post);
				addField.getChildren().add(add);
			});
			
			

			
			addField.getChildren().addAll(content, buttons,hashtagField, post);
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
	
	public static void addTextLimiterArea(final TextArea text, final int maxLength) {
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
		Label postTitle = new Label(title);
		postTitle.getStyleClass().add("postTitle");
		
		
		ArrayList<Button>tagButtons=new ArrayList<Button>();
		String tags="";
		String tag="";
		String action="";
		ArrayList<Label>labelTexts=new ArrayList<Label>();
		int startIndex=0;
		for(int i=0;i<text.length();i++) {
			if(text.charAt(i)=='<') {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='t') {
						if(text.charAt(i+3)=='i') {
							if(text.charAt(i+4)=='t') {
								if(text.charAt(i+5)=='l') {
									if(text.charAt(i+6)=='e') {
										action="title";
										startIndex=i+7;
									}
								}
							}
						}
					}
				}	
			}
			
			if(text.charAt(i)=='<') {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='t') {
						if(text.charAt(i+3)=='e') {
							if(text.charAt(i+4)=='x') {
								if(text.charAt(i+5)=='t') {
									action="text";
									startIndex=i+6;
								}
							}
						}
					}
				}	
			}
			
			if(text.charAt(i)=='<') {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='i') {
						if(text.charAt(i+3)=='m') {
							if(text.charAt(i+4)=='g') {
								action="img";
								startIndex=i+5;
							}
						}
					}
				}	
			}
			
			if(text.charAt(i)=='<') {
				if(text.charAt(i+1)=='!') {
					if(text.charAt(i+2)=='t') {
						if(text.charAt(i+3)=='a') {
							if(text.charAt(i+4)=='g') {
								action="tag";
								startIndex=i+5;
							}
						}
					}
				}	
			}
			
			
			if(text.charAt(i)=='>') {
				String subText=text.substring(startIndex,i);
				
				if(action.equals("text")) {
					labelTexts.add(new Label(subText));
					labelTexts.get(labelTexts.size()-1).setPadding(new Insets(0, 0, 20, 0));
					
				}
				if(action.equals("title")) {
					labelTexts.add(new Label(subText));
					labelTexts.get(labelTexts.size()-1).getStyleClass().add("postInnerTitle");
				}
				if(action.equals("img")) {
					
				}
				if(action.equals("tag")) {
					System.out.print("#"+subText+" ");
					tags+="#"+subText+" ";
					tag="#"+subText+" ";
					tagButtons.add(new Button(tag));
					
					
					tagButtons.get(tagButtons.size()-1).setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent event) {
							String tag=((Button) event.getSource()).getText();
							/*for(int i=scrollPaneBox.getChildren().size()-1;i>=0;i--) {
								if(!scrollPaneBox.getChildren().get(i).getUserData().toString().contains(tag)) {
									System.out.println(scrollPaneBox.getChildren().get(i).getUserData()+" tags i: "+i);
									scrollPaneBox.getChildren().remove(i);
								}
								
							}*/
							getScrollPaneBox().getChildren().clear();
							
							try {
								String blogg = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg);
							
								JSONObject json=new JSONObject(blogg);
								
								String bloggTitle=json.getString("titel");
								bloggId=json.getString("bloggId");
								//System.out.println(bloggId);
								
								if(Main.page==1) {
									labelTitle=new Label(bloggTitle);
									labelTitle.setFont(new Font(40));
									labelTitle.setAlignment(Pos.CENTER);
									labelTitle.setPrefWidth(4000);
								}
								
								
								JSONArray inlagg=json.getJSONArray("bloggInlagg");
								for(int i=0;i<inlagg.length();i++) {
									String inlaggStr = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg="+inlagg.getJSONObject(i).getString("id"));
									//System.out.println(inlaggStr);
									
									JSONObject inlaggJson=Json.toJSONObject(inlaggStr);
									
									String title=inlaggJson.getString("titel");
									
									String text=inlaggJson.getString("innehall");
									
									
									
									JSONArray array=inlaggJson.getJSONArray("gillningar");
									
									int likesAmount=array.length();
										
									//comments();
									
									String newTag=tag.substring(1,tag.length()-1);
									if(text.contains("<!tag"+newTag+">")) {
										//System.out.println(newTag);
										post(title, text, likesAmount);
									}
									
								}
								
								
								if(Main.login.getBloggId().equals(Main.currentBlogg+"")) {
									getAddField().getChildren().removeAll(getButtons(),getPost(),getAdd());
									getAddField().getChildren().add(getAdd());
								}
													
							} catch (Exception ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
							
							
						}
					});
					
				}
			}
			
			
		}
		
		Label labelText= new Label(text);
		labelText.setWrapText(true);
	
		Label likes= new Label("Likes: "+likesAmount);
		likes.getStyleClass().add("leftBloggText");
		
		VBox post= new VBox();
		post.setUserData(tags);
		
		post.getChildren().addAll(postTitle);
		
		for(int i=0;i<labelTexts.size();i++) {
			post.getChildren().add(labelTexts.get(i));
		}
		for(int i=0;i<tagButtons.size();i++) {
			post.getChildren().add(tagButtons.get(i));
		}
		
		post.getChildren().addAll(likes);
		
		post.setPrefWidth(2000);
		post.getStyleClass().add("post");
		post.setOnMouseClicked( ( e ) ->
        {
       	 
        });
		
		if(getScrollPaneBox().getChildren().size()==0){
			HBox bloggar = new HBox();
			bloggar.setUserData(tags);
			bloggar.getChildren().add(post);
			getScrollPaneBox().getChildren().add(bloggar);
		}
		else {
			
			HBox lastBloggContainer=(HBox) getScrollPaneBox().getChildren().get(getScrollPaneBox().getChildren().size()-1);
			
			if(lastBloggContainer.getChildren().size()==1){
				HBox bloggar = new HBox();
				bloggar.setUserData(tags);
				bloggar.getChildren().add(post);
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
			String blogg = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg);
		
			JSONObject json=new JSONObject(blogg);
			
			String bloggTitle=json.getString("titel");
			bloggId=json.getString("bloggId");
			//System.out.println(bloggId);
			
			if(Main.page==1) {
				labelTitle=new Label(bloggTitle);
				labelTitle.setFont(new Font(40));
				labelTitle.setAlignment(Pos.CENTER);
				labelTitle.setPrefWidth(4000);
			}
			
			
			JSONArray inlagg=json.getJSONArray("bloggInlagg");
			for(int i=0;i<inlagg.length();i++) {
				String inlaggStr = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg="+inlagg.getJSONObject(i).getString("id"));
				//System.out.println(inlaggStr);
				
				JSONObject inlaggJson=Json.toJSONObject(inlaggStr);
				
				String title=inlaggJson.getString("titel");
				String text=inlaggJson.getString("innehall");
				
				JSONArray array=inlaggJson.getJSONArray("gillningar");
				
				int likesAmount=array.length();
					
				//comments();
				
				post(title, text, likesAmount);
				
			}
			
			
			
			if(Main.login.getBloggId().equals(Main.currentBlogg+"")) {
				getAddField().getChildren().removeAll(getButtons(),getPost(),getAdd());
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
			String str = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg=3");
		
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

	
	public static void MultipartUtility(String file) {
        String charset = "UTF-8";
        File uploadFile1 = new File(file);
        
       
        //String requestURL = "http://10.130.216.101/TP/Blogg/funktioner/bilder";
 
        String requestURL = "http://10.130.216.101/TP/Blogg/funktioner/skapaBilder.php";

        
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
             
            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");
             
            multipart.addFormField("description", "Cool Pictures");
            multipart.addFormField("keywords", "Java,upload,Spring");
             
            multipart.addFilePart("file", uploadFile1);
            
            List<String> response = multipart.finish();
             
            System.out.println("SERVER REPLIED:");
             
            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
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

	public Label getLabelTitle() {
		return labelTitle;
	}

	public void setLabelTitle(Label labelTitle) {
		this.labelTitle = labelTitle;
	}

	public Button getHashtag() {
		return hashtag;
	}

	public void setHashtag(Button hashtag) {
		this.hashtag = hashtag;
	}

	public HBox getHashtagField() {
		return hashtagField;
	}

	public void setHashtagField(HBox hashtagField) {
		this.hashtagField = hashtagField;
	}


}
