package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import map.MapNode;
import view.ChatListener;

public class GameEngine implements GameEngineView, GameEngineController {
	private ArrayList<PlayerCharacter> characters = new ArrayList<>();
	private ArrayList<GameEngineListener> gameEngineListeners = new ArrayList<>();
	private ArrayList<Action> actionHistory = new ArrayList<>();
	private ArrayList<ChatMessage> messages = new ArrayList<>();
	
	@Override
    public void addMessage(ChatMessage msg){
    	messages.add(msg);
    	for(GameEngineListener gL : gameEngineListeners){
    		gL.newChatMessage(msg);
    	}
    }
    
	public MapNode getMapNode(String id){
		return MapNode.get(id);
	}
	
	public MapNode getCharacterLocation(PlayerCharacter pc){
		assert characters.contains(pc);
		for(PlayerCharacter character : characters){
			if(pc.equals(character)){
				return character.getCurrentLocation();
			}
		}
		//This should NEVER happen
		return MapNode.get("a1");
	}

    public void addGameEngineListener(GameEngineListener listener){
    	gameEngineListeners.add(listener);
    }

	@Override
	public List<ChatMessage> getMessagesAfter(ChatMessage chatMessage) {
		LinkedList<ChatMessage> newMessages = new LinkedList<>();
		int ptr = messages.size() - 1;
		//move back through the list, until the hash codes align and the messages align
		while(chatMessage.hashCode() != messages.get(ptr).hashCode() 
				|| chatMessage.equals(messages.get(ptr).hashCode())){
			newMessages.addFirst(messages.get(ptr));
			ptr--;
		}
		
		return newMessages;
	}

}
