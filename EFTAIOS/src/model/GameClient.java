package model;

import java.util.ArrayList;

import map.MapNode;
import view.ChatListener;
import view.MovementListener;

//The Game Client has an interesting position of being both observable and an observer.

public class GameClient implements GameEngineListener, GameClientController{
	//Model Data
	private Player localPlayer;
	private GameEngineController engine; //TODO: Figure out what to do with the creation of the game engine controller
	private GameEngineView view;
	
	private ArrayList<ChatMessage> receivedMessages = new ArrayList<>();
	
	//Observable Data
	private ArrayList<ChatListener> chatListeners = new ArrayList<>();
	private ArrayList<GameListener> gameListeners = new ArrayList<>();
	private ArrayList<MovementListener> movementListeners = new ArrayList<>();
	
	public GameClient(Player p, GameEngine e){
		localPlayer = p;
		engine = e;
		view = e;
		e.addGameEngineListener(this);
	}
	
	//this method will be used once networking is established to pull from the model
	public void pullStateFromEngine(){
		for(ChatMessage msg : engine.getMessagesAfter(receivedMessages.get(receivedMessages.size() - 1))){
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
		engine.addMessage(msg);
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
