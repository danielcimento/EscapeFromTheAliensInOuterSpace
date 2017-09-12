package model.engine;

import model.actions.ChatMessage;

public interface GameEngineListener {
	public void newChatMessage(ChatMessage msg);
}
