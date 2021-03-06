package model.actions

import java.util.UUID

import model.engine.GameEngine
import model.player.PlayerCharacter

/**
  * Created by daniel on 9/8/2017.
  */
case class ChatAction(chatMessage: String) extends Action {
  override def perform(gameEngine: GameEngine) = ???

  override def isVisibleTo(player: PlayerCharacter) = ???

  override val uuid: UUID = UUID.randomUUID()
}
