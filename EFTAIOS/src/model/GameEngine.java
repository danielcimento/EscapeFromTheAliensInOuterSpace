package model;

import java.util.ArrayList;

import map.MapNode;
import view.ChatListener;

public class GameEngine implements GameEngineView, GameEngineController {
	private ArrayList<PlayerCharacter> characters = new ArrayList<>();
	private ArrayList<String> messages = new ArrayList<>();
	private ArrayList<GameEngineListener> gameEngineListeners = new ArrayList<>();
	
	@Override
    public void addMessage(String s){
    	messages.add(s);
    	for(GameEngineListener gL : gameEngineListeners){
    		gL.newChatMessage(s);
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

}
