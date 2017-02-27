package runtime;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import map.MapNode;
import model.GameClient;
import model.GameEngine;
import view.HexagonGrid;
import view.SocialView;

public class EscapeFromTheAliensInOuterSpace extends Application{
	private static final int WIDTH = 1080;
	private static final int HEIGHT = 720;
	private static final String TITLE = "Escape from the Aliens in Outer Space";
	
	private GameEngine engine = new GameEngine();
	private GameClient client = new GameClient(engine);
	
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage){
		HBox root = new HBox();
		MapNode.initNodes("src/resources/galilei.txt");
		root.getChildren().add(new HexagonGrid(23, 14, 10.0, 10.0, 25, client));
		root.getChildren().add(new SocialView(client));
		stage.setTitle(TITLE);
		stage.setResizable(false);
        stage.setScene(new Scene(root, WIDTH, HEIGHT));
        stage.show();
	}
}
