package model.actions

import java.util.UUID

import model.player.PlayerCharacter
import model.engine.GameEngine

trait Action {
  val uuid: UUID
}
