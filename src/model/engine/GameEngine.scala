package model.engine

import model.map.GameMap
import model.player.PlayerCharacter
import model.actions.{Action, ChatMessage}

class GameEngine(
  gameMap: GameMap,
  actionHistory: List[Action],
  messageLog: List[String],
  players: List[PlayerCharacter]
) {



}
