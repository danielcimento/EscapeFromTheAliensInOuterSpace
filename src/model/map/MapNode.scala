package model.map

import model.player.PlayerCharacter


case class MapNode(name: String, nodeType: NodeType, players: List[PlayerCharacter]) extends Serializable{
  def containsPlayer(player: PlayerCharacter): Boolean = {
    players.contains(player)
  }

  def removePlayer(player: PlayerCharacter): MapNode = {
    this.copy(players = players.filter(_ != player))
  }

  def addPlayer(player: PlayerCharacter): MapNode = {
    this.copy(players = player :: players)
  }
}
