package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import map.MapNode;
import view.ChatListener;
import view.MovementListener;

//The Game Client has an interesting position of being both observable and an observer.

public class GameClient implements GameEngineListener, GameClientController{
	//Model Data
	private Player localPlayer;
	private GameEngineView view = null;
	private String serverName;
	private int port;
	
	private ArrayList<ChatMessage> receivedMessages = new ArrayList<>();
	
	//Observable Data
	private ArrayList<ChatListener> chatListeners = new ArrayList<>();
	private ArrayList<GameListener> gameListeners = new ArrayList<>();
	private ArrayList<MovementListener> movementListeners = new ArrayList<>();
	
	public GameClient(Player p){
		localPlayer = p;
	}
	
	//this method will be used once networking is established to pull from the model
	public GameEngine pullStateFromEngine(){
		try{
			Socket clientConnection = new Socket(serverName, port);
			ObjectInputStream serverIn = new ObjectInputStream(clientConnection.getInputStream());
			Object serverOutput = serverIn.readObject();
			GameEngine eng = null;
			
			if(serverOutput instanceof GameEngine){
				eng = (GameEngine)(serverOutput);
			}
			
			serverIn.close();
			clientConnection.close();
			
			return eng;
		}catch (IOException e){
			//if there is an exception we'll just wait until next pull.
		}catch (ClassNotFoundException e){
			throw new RuntimeException("Class not found!", e);
		}
		
		return null;
	}
	
	public void pullChatMessages(){
		GameEngine eng = pullStateFromEngine();
		for(ChatMessage msg : eng.getMessagesAfter(receivedMessages.get(receivedMessages.size() - 1))){
			receivedMessages.add(msg);
		}
	}


	
	//Observer Methods
	@Override
	public void newChatMessage(ChatMessage msg){
		receivedMessages.add(msg);
		notifyChatListeners(msg);
	}
	
	//View Methods
	public String getUsername(){
		return localPlayer.getName();
	}
	
	public MapNode getLocalPlayerLocation(){
		return view.getCharacterLocation(localPlayer.getCharacter());
	}
	
	public MapNode getMapNode(String id){
		return view.getMapNode(id);
	}
	
	//Controller Methods
	public void processMessage(String message){
		message = getUsername() + ": " + message;
		ChatMessage msg = new ChatMessage(message, localPlayer);
		
		try{
			Socket clientConnection = new Socket(serverName, port);
			ObjectOutputStream out = new ObjectOutputStream(clientConnection.getOutputStream());
			out.writeObject(msg);
			out.close();
			clientConnection.close();
		}catch (IOException e){
			//I'm really not 100% sure what I should best do in this case, as
			//a message to the server was lost.
		}
	}
	
	public void mapNodeSelected(MapNode m){
		//Do stuff
		//Placeholder
		for(GameListener gL : gameListeners){
			gL.gameStateChanged(m.getName());
		}
	}
	
	//Observable Methods
	/**
	 * @precondition cL is not already in the list of chat listeners 
	 */
	public void registerChatListener(ChatListener cL){
		assert chatListeners.indexOf(cL) == -1;
		chatListeners.add(cL);
	}
	
	
	public void removeChatListener(ChatListener cL){
		int index = chatListeners.indexOf(cL);
		if(index != -1){ chatListeners.remove(index); }
	}
	
	public void notifyChatListeners(ChatMessage cM){
		for(ChatListener c : chatListeners){
			c.newChatMessage(cM.getMessage());
		}
	}
	
	public void registerGameListener(GameListener gL){
		assert gameListeners.indexOf(gL) == -1;
		gameListeners.add(gL);
	}
	
	public void removeGameListener(GameListener gL){
		int index = gameListeners.indexOf(gL);
		if(index != -1){ gameListeners.remove(index); }
	}
	
	public void registerMovementListener(MovementListener mL){
		assert movementListeners.indexOf(mL) == -1;
		movementListeners.add(mL);
	}
	
	public void removeMovementListener(MovementListener mL){
		int index = movementListeners.indexOf(mL);
		if(index != -1){ movementListeners.remove(index); }
	}
	
}
