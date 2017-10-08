package model.actions

import java.util.UUID

import model.player.PlayerCharacter
import model.engine.GameEngine

trait Action {
  val uuid: UUID

  // Defines what the action does. Should return the game state after the action has occurred
  def perform(gameEngine: GameEngine): GameEngine

  def isVisibleTo(player: PlayerCharacter): Boolean
}
