package runtime;

import java.net.InetAddress;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import map.MapNode;
import model.CharacterType;
import model.GameClient;
import model.GameEngine;
import model.Player;
import model.PlayerCharacter;
import view.GameView;
import view.HexagonGrid;
import view.SocialView;

public class EscapeFromTheAliensInOuterSpace extends Application{
	private static final int WIDTH = 1320;
	private static final int HEIGHT = 1000;
	private static final String TITLE = "Escape from the Aliens in Outer Space";
	
	private GameEngine engine = new GameEngine();
	private GameClient client;
	
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage){
		client = new GameClient(initializeLocalPlayer(), engine);
		
		HBox root = new HBox();
		MapNode.initNodes("src/resources/galilei.txt");
		root.getChildren().add(new GameView(client));
		root.getChildren().add(new SocialView(client));
		stage.setTitle(TITLE);
		stage.setResizable(false);
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
	}
	
	private Player initializeLocalPlayer(){
		PlayerCharacter pc = new PlayerCharacter(CharacterType.ALIEN, MapNode.get("A03"));
		String name;
		try{
			name = InetAddress.getLocalHost().getHostAddress();
		}catch (Exception e){
			name = "Unidentified Player";
		}
		return new Player(pc, name);
	}
}
