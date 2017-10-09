package model.player

case class Player(
  username: String,
  hostAddress: String,
  role: Role,
  playerCharacter: Option[PlayerCharacter]
) extends Serializable
