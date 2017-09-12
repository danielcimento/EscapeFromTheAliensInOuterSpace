package model.engine;

import model.actions.ChatMessage;

import java.util.List;

public interface GameEngineController {
	public void addMessage(ChatMessage msg);

	public List<ChatMessage> getMessagesAfter(ChatMessage chatMessage);
}
