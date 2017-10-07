package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.engine.ActionListener;
import model.engine.GameClient;
import model.engine.GameListener;
import model.map.GameMap;
import net.GameClientHandler;

public class GameView extends VBox implements GameListener{
	private static final int MARGIN_OUTER = 10;
	private static final int MAP_WIDTH = 1080;
	private static final int MAP_HEIGHT = 1000;

	private TextArea notificationsArea;
	
	public GameView(ActionListener actionListener, GameMap gameMap){
		this.setPadding(new Insets(MARGIN_OUTER));
		this.setSpacing(10);
		this.setMinHeight(MAP_HEIGHT);
		this.setMinWidth(MAP_WIDTH);

		//Label for the model.map
		Label mapLabel = new Label("Game Map");
		this.getChildren().add(mapLabel);
		
		//model.map
		AnchorPane mapGroup = new AnchorPane();
		mapGroup.minHeight(MAP_WIDTH);
		mapGroup.minWidth(MAP_HEIGHT);
		mapGroup.prefHeight(MAP_HEIGHT);
		mapGroup.getStyleClass().add("game-window");
		HexagonGrid hexGrid = new HexagonGrid(
				23, 14,
				10.0, 10.0, 30,
				actionListener, gameMap);
		mapGroup.getChildren().add(hexGrid);
		this.getChildren().add(mapGroup);
		
		//Label for game notifications
		Label gameNotificationsLabel = new Label("Game Notifications");
		this.getChildren().add(gameNotificationsLabel);
		
		//Game notifications
		notificationsArea = new TextArea();
		notificationsArea.setEditable(false);
		notificationsArea.setWrapText(true);
		this.getChildren().add(notificationsArea);
	}
	
	@Override
	public void gameStateChanged(String s){
		notificationsArea.appendText(s + '\n');
	}
}
