package model;

import map.MapNode;

public interface GameClientController {

	public void mapNodeSelected(MapNode mapNode);
	public void processMessage(String message);
	
}
