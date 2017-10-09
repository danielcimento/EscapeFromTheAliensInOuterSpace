package model.engine

sealed trait GameStatus extends Serializable

object Lobby extends GameStatus
object InProgress extends GameStatus
object Complete extends GameStatus
