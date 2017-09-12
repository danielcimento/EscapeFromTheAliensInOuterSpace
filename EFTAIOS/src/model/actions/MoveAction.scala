package model.actions

import java.util.UUID

import model.engine.GameEngine
import model.player.PlayerCharacter

/**
  * Created by daniel on 9/8/2017.
  */
class MoveAction(performingPlayer: PlayerCharacter, destinationNode: String) extends Action {
  override val uuid: UUID = UUID.randomUUID()

  // Previously this was set to always return true with justification: All moves are visible to all players
  // The definition of visibility is currently unclear
  // TODO: Determine what visibility means and whether it applies to all
  override def isVisibleTo(player: PlayerCharacter) = {
    true
  }

  override def perform(gameEngine: GameEngine) = {
    // TODO: Write in a more type safe manner
    val startingPosition = gameEngine.gameMap findTiles (_.containsPlayer(performingPlayer)) head
    val endPosition = gameEngine.gameMap findTiles (_.name == destinationNode) head

    if (gameEngine.gameMap.isTraversable(startingPosition, endPosition, performingPlayer)) {
      val newStartPosition = startingPosition.removePlayer(performingPlayer)
      val newEndPosition = endPosition.addPlayer(performingPlayer)
      val updatedMap = gameEngine.gameMap
        .updateTile(startingPosition, newStartPosition)
        .updateTile(endPosition, newEndPosition)
      gameEngine.copy(gameMap = updatedMap)
    } else {
      gameEngine
    }
  }
}
