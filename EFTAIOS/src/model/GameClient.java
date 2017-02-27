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
	
	//Observer Methods
	public void newChatMessage(String s){
		notifyChatListeners(s);
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
		engine.addMessage(message);
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
	
	public void notifyChatListeners(String s){
		for(ChatListener c : chatListeners){
			c.newChatMessages(s);
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
