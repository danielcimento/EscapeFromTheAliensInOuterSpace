package model.player

case class PlayerCharacter(
  charType: CharacterType,
  status: Status,
  identifiableString: String
) {

  def kill(): PlayerCharacter = status match {
    case Alive => copy(status = Dead)
    case _ => this
  }

}
