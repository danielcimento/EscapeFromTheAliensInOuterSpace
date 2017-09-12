package model.engine;

import model.map.MapNode;

public interface GameEngineView {
	public MapNode getCharacterLocation(model.player.PlayerCharacter pc);
	public MapNode getMapNode(String id);
}
