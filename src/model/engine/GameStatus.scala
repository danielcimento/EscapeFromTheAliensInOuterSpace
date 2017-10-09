package model.engine

sealed trait GameStatus

object Lobby extends GameStatus
object InProgress extends GameStatus
object Complete extends GameStatus
