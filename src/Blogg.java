


import java.awt.Desktop;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.URL;

import org.json.JSONArray;
import org.json.JSONException;
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
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;

public class Blogg {

	
	private ScrollPane scrollPane;
	private ScrollPane postScrollPane;
	
	
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
	private TextArea linkText;
	private TextArea text;
	
	private HBox titleBox;
	


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
		
		postScrollPane= new ScrollPane();
		postScrollPane.setContent(getAddField());
		postScrollPane.setPannable(true);
		postScrollPane.setPrefSize(/*bloggar.getWidth()*/200, 800);
		//scrollPane.setFitToWidth(true);

		postScrollPane.fitToWidthProperty().set(true);
		
		postScrollPane.setOnDragDetected(e -> {
			postScrollPane.setCursor(Cursor.DEFAULT);
			System.out.println("blah");
		});
		postScrollPane.setStyle("-fx-background-color:transparent;");
		
		
		Button refresh = new Button("Refresh");
		
		refresh.setOnAction(e -> {
			refresh();
		});
		
		
		refreshField = new HBox();
		refreshField.getChildren().addAll(refresh);
		refreshField.getStyleClass().add("refreshField");
		
		
	}

	public ScrollPane getPostScrollPane() {
		return postScrollPane;
	}

	public void setPostScrollPane(ScrollPane postScrollPane) {
		this.postScrollPane = postScrollPane;
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
				
				
				
				text.getStyleClass().add("addField");
				text.setPromptText("Text");
				
				
				
				
				
				content.getChildren().add(text);
			});
			
			Button addPic = new Button("Add Pic");
			addPic.setOnAction(ee -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				
				
			        
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
				
				
				   selectedFile = fileChooser.showOpenDialog(null);
		            src.setText(selectedFile.getPath());

				content.getChildren().add(src);
				
			});
			
			Button addLink = new Button("Add Link");
			addLink.setOnAction(ee -> {
				
				linkText = new TextArea();
				
				//src.setMaxWidth(800);
				linkText.setWrapText(true);
				linkText.getStyleClass().add("addField");
				linkText.setPromptText("type a link here");
				
				
				linkText.setUserData("3");
				
				content.getChildren().add(linkText);
				
			});
			
			Button addTitle = new Button("Add Title");
			addTitle.setOnAction(ee -> {
				TextArea title = new TextArea();
				title.setMaxWidth(800);
				
				
				title.getStyleClass().add("addField");
				title.setPromptText("Title");
				
				title.setUserData("0");
				
				//addTextLimiter(text, 100);
				
				content.getChildren().add(title);
			});
			
			buttons = new HBox(20);
			buttons.getChildren().addAll(addText,addPic,addLink,addTitle);
			
			
			
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
								String imgSrc=MultipartUtility(line);
								inlagg+="<!img"+imgSrc+">";
							}
							else if(((TextArea)content.getChildren().get(i)).getUserData()=="3") {
								inlagg+="<!link"+line+">";
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
		
		
		//ArrayList<Button>tagButtons=new ArrayList<Button>();
		String tags="";
		String tag="";
		String action="";
		//ArrayList<Label>labelTexts=new ArrayList<Label>();
		
		//images
		//ArrayList<ImageView>images=new ArrayList<ImageView>();
		
		//ArrayList<Hyperlink>links=new ArrayList<Hyperlink>();
		
		
		ArrayList<Node>nodes=new ArrayList<Node>();
		
		
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
					if(text.charAt(i+2)=='l') {
						if(text.charAt(i+3)=='i') {
							if(text.charAt(i+4)=='n') {
								if(text.charAt(i+5)=='k') {
									action="link";
									startIndex=i+6;
								}
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
					nodes.add(new Label(subText));
					((Label) nodes.get(nodes.size()-1)).setPadding(new Insets(0, 0, 20, 0));
					((Label) nodes.get(nodes.size()-1)).setWrapText(true);
					
				}
				if(action.equals("title")) {
					nodes.add(new Label(subText));
					nodes.get(nodes.size()-1).getStyleClass().add("postInnerTitle");
				}
				if(action.equals("img")) {
					String path =subText;
					try {
						
						Image image = new Image(path);
						ImageView imageView = new ImageView(image);
						
						double ratio=image.getHeight()/image.getWidth();
						double scale=350;
						imageView.setFitWidth(scale);
						imageView.setFitHeight(scale*ratio);
					
						nodes.add(imageView);
					}
					catch (IllegalArgumentException e) {
						// TODO: handle exception
					}
				}
				if(action.equals("link")) {
					String path =subText;
					
					
					try {
						
						Desktop desktop = Desktop.getDesktop();
						Hyperlink hyperlink = new Hyperlink();
						hyperlink.setText(path);
						hyperlink.setOnAction(e -> {
							
							try {
								desktop.browse(new URI(path));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (URISyntaxException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						});
						
						nodes.add(hyperlink);
					}
					catch (IllegalArgumentException e) {
						// TODO: handle exception
					}
					
				}
				
				if(action.equals("tag")) {
					
					tags+="#"+subText+" ";
					tag="#"+subText+" ";
					nodes.add(new Button(tag));
					
					
					((Button) nodes.get(nodes.size()-1)).setOnAction(new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent event) {
							String tag=((Button) event.getSource()).getText();
							
							getScrollPaneBox().getChildren().clear();
							
							try {
								String blogg = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg);
							
								JSONObject json=new JSONObject(blogg);
								
								String bloggTitle=json.getString("titel");
								bloggId=json.getString("bloggId");
								//System.out.println(bloggId);
								
								/*if(Main.page==1) {
									labelTitle=new Label(bloggTitle);
									labelTitle.setFont(new Font(40));
									labelTitle.setAlignment(Pos.CENTER);
									labelTitle.setPrefWidth(4000);
								}*/
								
								
								JSONArray inlagg=json.getJSONArray("bloggInlagg");
								for(int i=0;i<inlagg.length();i++) {
									String inlaggStr = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg="+inlagg.getJSONObject(i).getString("id"));
									
									JSONObject inlaggJson=Json.toJSONObject(inlaggStr);
									
									String title=inlaggJson.getString("titel");
									
									String text=inlaggJson.getString("innehall");
									
									
									JSONArray array=inlaggJson.getJSONArray("gillningar");
									
									int likesAmount=array.length();
										
									
									
									String newTag=tag.substring(1,tag.length()-1);
									if(text.contains("<!tag"+newTag+">")) {
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
		
		for(int i=0;i<nodes.size();i++) {
			post.getChildren().add(nodes.get(i));
		}
		post.getChildren().addAll(likes);
		
		post.setPrefWidth(2000);
		post.getStyleClass().add("post");
		post.setOnMouseClicked( ( e ) ->
        {
       	 
        });
		
		getScrollPaneBox().getChildren().add(post);
		
		/*if(getScrollPaneBox().getChildren().size()==0){
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
		}*/
		
		
		
	}
	
	
	

	public void refresh() {
		getScrollPaneBox().getChildren().clear();
		Main.center.getChildren().remove(labelTitle);
		try {
			String blogg = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg);
		
			JSONObject json=new JSONObject(blogg);
			
			String bloggTitle=json.getString("titel");
			bloggId=json.getString("bloggId");
			
			JSONArray inlagg=json.getJSONArray("bloggInlagg");
			for(int i=inlagg.length()-1;i>=0;i--) {
				String inlaggStr = HttpRequest.send("nyckel=XNcV4BpztHN8yKye&tjanst=blogg&typ=JSON&blogg="+Main.currentBlogg+"&inlagg="+inlagg.getJSONObject(i).getString("id"));
				
				
				JSONObject inlaggJson=Json.toJSONObject(inlaggStr);
				
				String title=inlaggJson.getString("titel");
				String text=inlaggJson.getString("innehall");
				
				JSONArray array=inlaggJson.getJSONArray("gillningar");
				
				int likesAmount=array.length();
					
				//comments();
				
				post(title, text, likesAmount);
				
			}
			
			//title for blogg.
			labelTitle=new Label(bloggTitle);
			labelTitle.setFont(new Font(40));
			labelTitle.setAlignment(Pos.CENTER);
			labelTitle.setPrefWidth(4000);
			
			titleBox = new HBox();
			titleBox.getChildren().add(labelTitle);
			
			
			//removes and adds title.
			
			Main.center.getChildren().add(1,labelTitle);
			
			System.out.println(Main.login.getBloggId()+"  "+Main.currentBlogg);
			if(Main.login.getBloggId().equals(Main.currentBlogg+"")) {
				getAddField().getChildren().removeAll(getButtons(),getPost(),getAdd());
				getAddField().getChildren().add(getAdd());
				
				
			}
			else {
				getAddField().getChildren().removeAll(getButtons(),getPost(),getAdd());
			}
			
			
			
			
		
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
	
	
	public static String MultipartUtility(String file) {
        String charset = "UTF-8";
        File uploadFile1 = new File(file);
        String oldPath=uploadFile1.getPath();
        
        String newPath=oldPath.substring(0, oldPath.length()-4);
        newPath+=(long)(Math.random()*1000000000)+".png";
        File newFile=new File(newPath);
        uploadFile1.renameTo(newFile);
        
        System.out.println("name: "+newFile.getName());
        
        
        
        //String requestURL = "http://10.130.216.101/TP/Blogg/funktioner/bilder";
 
        String requestURL = "http://10.130.216.101/TP/Blogg/funktioner/skapaBilder.php";

        String location="";
        
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
             
            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");
             
            multipart.addFormField("description", "Cool Pictures");
            multipart.addFormField("keywords", "Java,upload,Spring");
            
            multipart.addFilePart("file", newFile);
            
            
            newFile.renameTo(new File(oldPath));
            System.out.println("renamed name: "+newFile.getName()+"  oldPath: "+oldPath+"  newPath: "+newPath);
            
            
            List<String> response = multipart.finish();
             
            System.out.println("SERVER REPLIED:");
             
            for (String line : response) {
                System.out.println(line);
                location += line;
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println(location);
        
        JSONObject json=Json.toJSONObject(location);
        String send="";
        try {
			send=json.getString("resp");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return send;
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

	public HBox getTitleBox() {
		return titleBox;
	}

	public void setTitleBox(HBox titleBox) {
		this.titleBox = titleBox;
	}

}
