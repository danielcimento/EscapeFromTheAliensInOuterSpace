package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.GameClient;
import model.GameClientController;

public class SocialView extends VBox implements ChatListener{
	private static final int MARGIN_OUTER = 10;
	private static final int SOCIAL_VIEW_WIDTH = 300;
	private static final int MESSAGE_DISPLAY_HEIGHT = 700;
	private static final int PLAYERS_LIST_HEIGHT = 180;
	
	private GameClientController client;
	private TextField messageArea;
	private TextArea messageDisplay;
	
	public SocialView(GameClientController pClient){
		this.setPadding(new Insets(MARGIN_OUTER));
		client = pClient;
		
		//Text field for sending messages
		messageArea = new TextField();
		messageArea.setPromptText("Type chat messages here");
		messageArea.setOnAction(e -> sendMessage());
		
		//Scroll pane with chat messages
		ScrollPane chatScroll = new ScrollPane();
		messageDisplay = new TextArea();
		messageDisplay.setEditable(false);
		messageDisplay.setPrefSize(SOCIAL_VIEW_WIDTH, MESSAGE_DISPLAY_HEIGHT);
		messageDisplay.setWrapText(true);
		
		//Scroll pane with current players
		TextArea playersList = new TextArea();
		playersList.setEditable(false);
		playersList.setPrefSize(SOCIAL_VIEW_WIDTH, PLAYERS_LIST_HEIGHT);
		
		//Setting scroll contents and settings
		chatScroll.setContent(messageDisplay);
		chatScroll.setPrefSize(SOCIAL_VIEW_WIDTH, MESSAGE_DISPLAY_HEIGHT);
		chatScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		//Labels
		Label playersLabel = new Label("Players:");
		Label chatLabel = new Label("Chat");
		
		VBox chatAndMessages = new VBox();
		chatAndMessages.getChildren().addAll(chatScroll, messageArea);
		chatAndMessages.setSpacing(5);
		
		setSpacing(20);
		getChildren().addAll(playersLabel, playersList, chatLabel, chatAndMessages);
		
		client.registerChatListener(this);
	}
	
	private void sendMessage(){
		String message = messageArea.getText();
		messageArea.setText("");
		client.processMessage(message);
	}
	
	@Override
	public void newChatMessage(String message){
			messageDisplay.appendText(message + '\n');
	}
}
