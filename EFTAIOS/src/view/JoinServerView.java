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
import main.EscapeFromTheAliensInOuterSpace;

public class JoinServerView extends Tab{
	private TextField username;
	private TextField serverAddress;
	private TextField port;
	
	public JoinServerView(){
		super();
		this.setText("Join a Server");
		this.setClosable(false);
		
		Label usernameLabel = new Label("Username: ");
		username = new TextField();
		username.setTooltip(new Tooltip("This will be your display name during the game"));
			
		Label serverAddressLabel = new Label("Server Address: ");
		serverAddress = new TextField();
		serverAddress.setTooltip(new Tooltip("The IP Address of the server you wish to connect to.\nDon't know it?\nAsk the host!"));

		
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
		port.setTooltip(new Tooltip("The port on which the host is running the server.\n"
				+ "By default it is 6789.\n"
				+ "If this does not work, try asking the host"));
		port.setText("6789");
			

		Button joinButton = new Button("Join");
		joinButton.setOnAction(e -> joinServer());

		GridPane frame = new GridPane();
		frame.setAlignment(Pos.CENTER);
		frame.setHgap(12);
		frame.setVgap(10);
		frame.add(usernameLabel, 0, 0);
		frame.add(username, 1, 0);
		frame.add(serverAddressLabel, 0, 1);
		frame.add(serverAddress, 1, 1);
		frame.add(portLabel, 0, 2);
		frame.add(port, 1, 2);
		frame.add(joinButton, 0, 3, 2, 1);
		GridPane.setHalignment(joinButton, HPos.CENTER);
		this.setContent(frame);
	}
	
	public void joinServer(){
		String serverAddressText = serverAddress.getText();
		String portNumberText = port.getText();
		int portNumber = 6789;
		try{
			 portNumber = Integer.parseInt(portNumberText);
		}catch(NumberFormatException e){
			//TODO: Create better input validation	
		}
		
		EscapeFromTheAliensInOuterSpace.createGameClient(serverAddressText, portNumber, username.getText());
	}
}
