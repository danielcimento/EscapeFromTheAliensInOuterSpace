package view

import model.engine.VisibleGameState

trait GameStateListener {
  def gameStateChanged(vgs: VisibleGameState): Unit
}
