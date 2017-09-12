package model.actions;

import model.Player;
import model.player.PlayerCharacter;

import java.io.Serializable;
import java.util.Date;

/**
 * A chat message is a serializable object that will be stored within the serverside
 * game model object. It contains the message itself, the player who spoke it,
 * and a hashCode for faster checking to ensure that no duplicates are loaded by
 * listeners
 */
public class ChatMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4131875886100540902L;
	private String aMessage;
	private int hashCode;
	private PlayerCharacter aPlayer;
	private Date timeStamp;
	
	public ChatMessage(String pMessage, PlayerCharacter pPlayer){
		aMessage = pMessage;
		timeStamp = new Date();
		aPlayer = pPlayer;
		//default hash code. will calculate a new one only when requested
		hashCode = 0;
	}

	public String getMessage(){
		return aMessage;
	}
	
	
	@Override
	public int hashCode() {
		if(hashCode != 0){
			return hashCode;
		}else{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((aMessage == null) ? 0 : aMessage.hashCode());
			result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
			hashCode = result;
			return hashCode;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatMessage other = (ChatMessage) obj;
		if (aMessage == null) {
			if (other.aMessage != null)
				return false;
		} else if (!aMessage.equals(other.aMessage))
			return false;
		if (hashCode != other.hashCode)
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}
	
}
