package model.engine

import model.map.GameMap
import model.player.PlayerCharacter
import model.actions.{Action, ChatMessage}

case class GameEngine(
  gameMap: GameMap,
  actionHistory: List[Action],
  messageLog: List[ChatMessage],
  players: List[PlayerCharacter]
) {



}
