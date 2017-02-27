package model;

public class Player {
	private PlayerCharacter character;
	private String name;
	
	public Player(PlayerCharacter c, String n){
		name = n;
		character = c;
	}
	
	public String getName(){
		return name;
	}
	
	public PlayerCharacter getCharacter(){
		return character;
	}
}
