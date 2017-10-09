package model.player

sealed trait Role

object Host extends Role
object Participant extends Role
// Currently not in use, may add later.
object Spectator extends Role