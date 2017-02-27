package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import model.GameClient;
import model.GameEngineListener;
import model.GameListener;

public class GameView extends VBox implements GameListener{
	private static final int MARGIN_OUTER = 10;

	private GameClient aClient;
	private TextArea notificationsArea;
	
	public GameView(GameClient pClient){
		this.setPadding(new Insets(MARGIN_OUTER));
		this.setSpacing(10);
		aClient = pClient;
		
		//Label for the map
		Label mapLabel = new Label("Game Map");
		this.getChildren().add(mapLabel);
		
		//map
		HexagonGrid hexGrid = new HexagonGrid(23, 14, 10.0, 10.0, 30, aClient);
		this.getChildren().add(hexGrid);
		
		//Label for game notifications
		Label gameNotificationsLabel = new Label("Game Notifications");
		this.getChildren().add(gameNotificationsLabel);
		
		//Game notifications
		notificationsArea = new TextArea();
		notificationsArea.setEditable(false);
		notificationsArea.setWrapText(true);
		this.getChildren().add(notificationsArea);
		
		pClient.registerGameListener(this);
	}
	
	@Override
	public void gameStateChanged(String s){
		notificationsArea.appendText(s + '\n');
	}
}
