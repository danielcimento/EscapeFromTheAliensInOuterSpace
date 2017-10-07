package model.cards

sealed trait CardType

object Silence extends CardType
object NoiseInSector extends CardType
object NoiseInOtherSector extends CardType
