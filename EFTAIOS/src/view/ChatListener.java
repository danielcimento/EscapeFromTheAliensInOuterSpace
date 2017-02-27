package view;

import model.ChatMessage;

public interface ChatListener {
	public void newChatMessage(String message);
}
