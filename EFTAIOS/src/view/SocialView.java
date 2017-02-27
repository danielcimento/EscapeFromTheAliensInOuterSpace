package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.GameClient;

public class SocialView extends VBox implements ChatListener{
	private static final int MARGIN_OUTER = 10;
	
	private GameClient client;
	private TextField messageArea;
	private TextArea messageDisplay;
	
	public SocialView(GameClient pClient){
		this.setPadding(new Insets(MARGIN_OUTER));
		client = pClient;
		
		//Text field for sending messages
		messageArea = new TextField();
		messageArea.setOnAction(e -> sendMessage());
		
		//Scroll pane with chat messages
		ScrollPane chatScroll = new ScrollPane();
		messageDisplay = new TextArea();
		messageDisplay.setEditable(false);
		messageDisplay.setPrefSize(200, 600);
		messageDisplay.setWrapText(true);
		
		//Scroll pane with current players
		ScrollPane playersScroll = new ScrollPane();
		TextArea playersList = new TextArea();
		playersList.setEditable(false);
		playersList.setPrefSize(200, 120);
		
		//Setting scroll contents and settings
		chatScroll.setContent(messageDisplay);
		playersScroll.setContent(playersList);
		chatScroll.setPrefSize(200, 600);
		playersScroll.setPrefSize(200, 120);
		chatScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		playersScroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		//Labels
		Label playersLabel = new Label("Players:");
		Label chatLabel = new Label("Chat");
		
		VBox chatAndMessages = new VBox();
		chatAndMessages.getChildren().addAll(chatScroll, messageArea);
		chatAndMessages.setSpacing(5);
		
		setSpacing(20);
		getChildren().addAll(playersLabel, playersScroll, chatLabel, chatAndMessages);
		
		client.registerChatListener(this);
	}
	
	private void sendMessage(){
		String message = messageArea.getText();
		messageArea.setText("");
		client.processMessage(message);
	}
	
	@Override
	public void newChatMessages(String... messages){
		for(String message : messages){
			messageDisplay.appendText(message + '\n');
		}
	}
}
