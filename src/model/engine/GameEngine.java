//package model;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//
//import model.map.MapNode;
//
//public class GameEngine implements GameEngineView, GameEngineController, Serializable {
//	private ArrayList<PlayerCharacter> characters = new ArrayList<>();
//	private ArrayList<Action> actionHistory = new ArrayList<>();
//	private ArrayList<ChatMessage> messages = new ArrayList<>();
//
//	//Methods that change the state of the GameEngine
//
//    public void addMessage(ChatMessage msg){
//    	messages.add(msg);
//    	System.out.println(msg.getMessage());
//    	/*
//    	 * To add the network code, we need to remove game engine listeners and
//    	 * have the clients pull the information they need from the server's instance
//    	for(GameEngineListener gL : gameEngineListeners){
//    		gL.newChatMessage(msg);
//    	}
//    	*/
//    }
//
//	public void addAction(Action act){
//		actionHistory.add(act);
//	}
//
//	public void addPlayerCharacter(PlayerCharacter pc){
//		characters.add(pc);
//		System.out.println("New player registered!");
//	}
//
//	//Methods that return a state from the GameEngine
//
//	public MapNode getMapNode(String id){
//		return MapNode.get(id);
//	}
//
//	public MapNode getCharacterLocation(PlayerCharacter pc){
//		assert characters.contains(pc);
//		for(PlayerCharacter character : characters){
//			if(pc.equals(character)){
//				return character.getCurrentLocation();
//			}
//		}
//		//This should NEVER happen
//		//TODO: Figure out if this ever happens
//		//And what to do in order to fix it...
//		return MapNode.get("a1");
//	}
//
//	@Override
//	public List<ChatMessage> getMessagesAfter(ChatMessage chatMessage) {
//		LinkedList<ChatMessage> newMessages = new LinkedList<>();
//		int ptr = messages.size() - 1;
//
//		if(chatMessage == null){
//			//server and client are on different main environments, so changes to messages won't
//			//affect the server
//			return new LinkedList<ChatMessage>(messages);
//		}
//
//		//continually move back through the serverside messages, adding messages which aren't the user's
//		while(chatMessage.hashCode() != messages.get(ptr).hashCode()
//				|| !chatMessage.equals(messages.get(ptr).hashCode())
//				&& ptr > 0){
//			newMessages.addFirst(messages.get(ptr));
//			ptr--;
//		}
//
//		return newMessages;
//	}
//
//}
