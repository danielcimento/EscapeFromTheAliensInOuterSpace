package model.engine

import model.actions.Action

trait GameState {
  // If the GameState is a lobby, register the player. Otherwise do nothing.
  def addPlayer(username: String): GameState
  // Apply the given action to the game state.
  def processAction(playerContext: String, action: Action): GameState

  def generateVisibleGameState(player: String): VisibleGameState
}
