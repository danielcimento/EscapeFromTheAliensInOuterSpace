package runtime;

import java.net.InetAddress;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import map.MapConfiguration;
import map.MapNode;
import model.CharacterType;
import model.GameClient;
import model.GameEngine;
import model.Player;
import model.PlayerCharacter;
import view.GameView;
import view.HostServerView;
import view.JoinServerView;
import view.SocialView;

public class EscapeFromTheAliensInOuterSpace extends Application{
	private static final int WIDTH = 1320;
	private static final int HEIGHT = 1000;
	private static final int INTRO_FRAME_HEIGHT = 300;
	private static final int INTRO_FRAME_WIDTH = 400;
	private static final String TITLE = "Escape from the Aliens in Outer Space";
	private static final String VERSION_NUMBER = "v0.1";
	private static Stage primaryStage;
	
	private static boolean serverConnectionChosen = false;
	
	private static GameEngine engine;
	private static GameClient client;
	
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage){
		primaryStage = stage;
		stage.setTitle(TITLE + " - " + VERSION_NUMBER);
		stage.setResizable(false);
		stage.setScene(renderConnectToServerWindow());
		stage.show();
	}
	
	
	public static void createGameClient(){
		primaryStage.hide();
		client = new GameClient(initializeLocalPlayer());
		primaryStage.setScene(renderGameWindow());
		primaryStage.show();
	}
	
	public static Scene renderConnectToServerWindow(){
		TabPane tabPane = new TabPane();
		tabPane.getTabs().addAll(new JoinServerView(), new HostServerView());
		Scene scene = new Scene(tabPane, INTRO_FRAME_WIDTH, INTRO_FRAME_HEIGHT);
        return scene;
	}
	
	public static Scene renderGameWindow(){
		HBox root = new HBox();
		//MapConfiguration cfg = MapConfiguration.loadFromFile("src/resources/fermi.ser");
		MapConfiguration cfg = MapConfiguration.loadFromFile("src/resources/galilei.ser");
		MapNode.initNodes(cfg);
		root.getChildren().add(new GameView(client));
		root.getChildren().add(new SocialView(client));
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		scene.getStylesheets().add(EscapeFromTheAliensInOuterSpace.class.getResource("stylesheet.css").toExternalForm());
		return scene;
	}
	
	public static void setServerConnectionChosen(boolean b){
		serverConnectionChosen = b;
	}
	
	private static Player initializeLocalPlayer(){
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
