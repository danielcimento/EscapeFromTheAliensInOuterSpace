package model;

import map.MapNode;

public interface GameEngineView {
	public MapNode getCharacterLocation(PlayerCharacter pc);
	public MapNode getMapNode(String id);
}
