package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import net.EFTAIOSServer;
import runtime.EscapeFromTheAliensInOuterSpace;

public class HostServerView extends Tab {
	private TextField username;
	private TextField port;
	
	public HostServerView(){
		super();
		this.setText("Host a Server");
		this.setClosable(false);

		Label usernameLabel = new Label("Username: ");
		username = new TextField();
		username.setTooltip(new Tooltip("This will be your display name in game."));
		
		Label portLabel = new Label("Port: ");
		port = new TextField();
		port.textProperty().addListener(new ChangeListener<String>(){
			@Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                port.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
		});
		port.setTooltip(new Tooltip("Don't know what this means?\nLeave it as 6789.\nYou may need to forward your ports"));
		port.setText("6789");
		
		Button hostButton = new Button("Host");
		hostButton.setOnAction(e -> createServer());
		
		GridPane frame = new GridPane();
		frame.setAlignment(Pos.CENTER);
		frame.setHgap(12);
		frame.setVgap(10);
		frame.add(usernameLabel, 0, 0);
		frame.add(username, 1, 0);
		frame.add(portLabel, 0, 1);
		frame.add(port, 1, 1);
		frame.add(hostButton, 0, 2, 2, 1);
		GridPane.setHalignment(hostButton, HPos.CENTER);
		this.setContent(frame);
	}
	
	public void createServer(){
		String portNumberText = port.getText();
		int portNumber = 6789;
		try{
			portNumber = Integer.parseInt(portNumberText);
		}catch (NumberFormatException e){
			//this should never really happen except for really big ints i guess...
		}
		EFTAIOSServer.hostNewServer(portNumber);
		
		new Thread(EFTAIOSServer.getInstance()).start();
		EscapeFromTheAliensInOuterSpace.createGameClient();
	}
}
	