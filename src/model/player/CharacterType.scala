package model.player

sealed trait CharacterType extends Serializable

object Alien extends CharacterType
object Human extends CharacterType
