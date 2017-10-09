package model.player

sealed trait Status extends Serializable

object Alive extends Status
object Dead extends Status
object Escaped extends Status
