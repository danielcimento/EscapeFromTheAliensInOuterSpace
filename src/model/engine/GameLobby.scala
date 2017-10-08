package model.engine
import model.actions.{Action, ChatAction, MoveAction, ReadyAction}

case class GameLobby(listOfPlayers: List[String], listOfMessages: List[String]) extends GameState {
  override def addPlayer(username: String): GameLobby = {
    this.copy(listOfPlayers = username :: listOfPlayers)
  }

  def addMessage(message: String): GameLobby = {
    this.copy(listOfMessages = message :: listOfMessages)
  }

  def markReady(player: String, readied: ReadyAction): GameState = {
    // TODO: Add storage of players' readied statuses and implement conversion handler to GameEngine
    this
  }

  override def generateVisibleGameState(player: String): VisibleGameState = {
    VisibleGameState(listOfMessages)
  }

  override def processAction(playerContext: String, action: Action): GameState = {
    action match {
      case ChatAction(msg) => this.addMessage(msg)
      case MoveAction(destinationNode) => this.addMessage(
        "Player " + playerContext + " tried to move to " + destinationNode + "!\n"
        + "Unfortunately movement is not yet enabled!"
      )
      case ready: ReadyAction => this.markReady(playerContext, ready)
      case _ => this // NOOP for unknown methods
    }
  }
}