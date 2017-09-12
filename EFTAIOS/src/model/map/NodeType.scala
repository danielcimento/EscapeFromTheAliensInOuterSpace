package model.map

sealed trait NodeType

object Blocked extends NodeType
object Secure extends NodeType
object Dangerous extends NodeType
object Human extends NodeType
object Alien extends NodeType
object EscapeHatch extends NodeType
