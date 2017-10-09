package model.actions

import java.util.UUID

import model.player.Player

case class KickAction(player: Player) extends Action {
  val uuid: UUID = UUID.randomUUID()
}