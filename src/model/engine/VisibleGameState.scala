package model.engine

import model.map.GameMap
import model.player.Player

case class VisibleGameState(gameStatus: GameStatus, messages: List[String], players: List[Player], gameMap: GameMap) extends Serializable
