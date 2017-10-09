package model.engine

import model.player.Player

case class VisibleGameState(gameStatus: GameStatus, messages: List[String], players: List[Player])
