package model.actions

import java.util.UUID

import model.engine.GameEngine
import model.player.{Alien, Alive, PlayerCharacter}

/**
  * Created by daniel on 9/8/2017.
  */
case class MoveAction(destinationNode: String) extends Action {
  override val uuid: UUID = UUID.randomUUID()

  // TODO: Move handling logic into the game state itself, rather than the action
//  // Previously this was set to always return true with justification: All moves are visible to all players
//  // The definition of visibility is currently unclear
//  // TODO: Determine what visibility means and whether it applies to all
//  override def isVisibleTo(player: PlayerCharacter) = {
//    true
//  }
//
//  // TODO: Add some parameterized context for the action which is executed. This will allow us to identify
//  // the player to determine which character to move
//  override def perform(gameEngine: GameEngine) = {
//    // TODO: REMOVE THIS
//    val performingPlayer: PlayerCharacter = new PlayerCharacter(Alien, Alive, "foo")
//
//    // TODO: Write in a more type safe manner
//    val startingPosition = gameEngine.gameMap findTiles (_.containsPlayer(performingPlayer)) head
//    val endPosition = gameEngine.gameMap findTiles (_.name == destinationNode) head
//
//    if (gameEngine.gameMap.isTraversable(startingPosition, endPosition, performingPlayer)) {
//      val newStartPosition = startingPosition.removePlayer(performingPlayer)
//      val newEndPosition = endPosition.addPlayer(performingPlayer)
//      val updatedMap = gameEngine.gameMap
//        .updateTile(startingPosition, newStartPosition)
//        .updateTile(endPosition, newEndPosition)
//      gameEngine.copy(gameMap = updatedMap)
//    } else {
//      gameEngine
//    }
//  }
}
