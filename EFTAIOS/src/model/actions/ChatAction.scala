package model.actions

import java.util.UUID

import model.engine.GameEngine
import model.Player

/**
  * Created by daniel on 9/8/2017.
  */
case class ChatAction(chatMessage: ChatMessage) extends Action {
  override def perform(gameEngine: GameEngine) = ???

  override def isVisibleTo(player: Player) = ???

  override val uuid: UUID = UUID.randomUUID()
}
