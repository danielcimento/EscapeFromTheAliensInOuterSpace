package model.player

sealed trait Status

object Alive extends Status
object Dead extends Status
object Escaped extends Status
