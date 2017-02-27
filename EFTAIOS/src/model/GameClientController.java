package model;

import map.MapNode;
import view.ChatListener;

public interface GameClientController {

	public void mapNodeSelected(MapNode mapNode);
	public void processMessage(String message);
	public void registerChatListener(ChatListener cL);
	
}
