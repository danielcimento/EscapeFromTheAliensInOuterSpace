package model;

import map.MapNode;

public class MoveAction implements Action{
	private GameEngineController engine;
	private Player performingPlayer;
	private MapNode destinationNode;
	
	MoveAction(GameEngineController gEC, Player performer, MapNode dest){
		engine = gEC;
		performingPlayer = performer;
		destinationNode = dest;
	}
	
	@Override
	public void perform(){
		PlayerCharacter pC = performingPlayer.getCharacter();
		pC.move(destinationNode);
	}

	@Override
	public boolean isVisibleToPlayer(Player p) {
		//All move actions are visible to all players
		return true;
	}
	
}
