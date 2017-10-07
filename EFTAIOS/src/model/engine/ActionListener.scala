package model.engine

import model.actions.Action

trait ActionListener {
  def receiveAction(action: Action): Unit
}
