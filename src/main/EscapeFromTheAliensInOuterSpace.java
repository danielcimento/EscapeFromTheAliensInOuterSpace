//package main;
//
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.TabPane;
//import javafx.scene.layout.HBox;
//import javafx.stage.Stage;
//import model.map.MapConfiguration;
//import model.map.MapNode;
//import model.CharacterType;
//import model.engine.GameClient;
//import model.Player;
//import model.player.PlayerCharacter;
//import view.GameView;
//import view.HostServerView;
//import view.JoinServerView;
//import view.SocialView;
//
//public class EscapeFromTheAliensInOuterSpace extends Application{
//	private static final int WIDTH = 1320;
//	private static final int HEIGHT = 1000;
//	private static final int INTRO_FRAME_HEIGHT = 300;
//	private static final int INTRO_FRAME_WIDTH = 400;
//	private static final String TITLE = "Escape from the Aliens in Outer Space";
//	private static final String VERSION_NUMBER = "v0.1";
//	private static Stage primaryStage;
//
//	private static GameClient client;
//
//
//	public static void main(String[] args){
//		launch(args);
//	}
//
//	@Override
//	public void start(Stage stage){
//		primaryStage = stage;
//		stage.setTitle(TITLE + " - " + VERSION_NUMBER);
//		stage.setResizable(false);
//		stage.setScene(renderConnectToServerWindow());
//		stage.setOnCloseRequest(e -> System.exit(0));
//		stage.show();
//	}
//
//
//	public static void createGameClient(String serverAddress, int portNumber, String username){
//		primaryStage.hide();
//		client = new GameClient(initializeLocalPlayer(username), serverAddress, portNumber);
//		new Thread(client).start();
//		primaryStage.setScene(renderGameWindow());
//		primaryStage.show();
//	}
//
//	public static Scene renderConnectToServerWindow(){
//		TabPane tabPane = new TabPane();
//		tabPane.getTabs().addAll(new JoinServerView(), new HostServerView());
//		Scene scene = new Scene(tabPane, INTRO_FRAME_WIDTH, INTRO_FRAME_HEIGHT);
//        return scene;
//	}
//
//	public static Scene renderGameWindow(){
//		HBox root = new HBox();
//		//MapConfiguration cfg = MapConfiguration.loadFromFile("src/main.resources/fermi.ser");
//		//MapConfiguration cfg = MapConfiguration.loadFromFile("src/main.resources/galilei.ser");
//		MapConfiguration cfg = MapConfiguration.readConfigurationFromFile(EscapeFromTheAliensInOuterSpace.class.getResourceAsStream("resources/galilei.ser"));
//		GameMap(cfg);
//		root.getChildren().add(new GameView(client));
//		root.getChildren().add(new SocialView(client));
//		Scene scene = new Scene(root, WIDTH, HEIGHT);
//		scene.getStylesheets().add(EscapeFromTheAliensInOuterSpace.class.getResource("resources/stylesheet.css").toExternalForm());
//		return scene;
//	}
//
//	private static Player initializeLocalPlayer(String username){
//		//TODO: Change the way that characters are created (probably more beneficial once the game lobby is established)
//		PlayerCharacter pc = new PlayerCharacter(CharacterType.ALIEN, MapNode.get("A03"));
//		return new Player(pc, username);
//	}
//}
