package main

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.TabPane
import javafx.scene.layout.HBox
import javafx.stage.Stage

import model.map._
import model.player.PlayerCharacter
import net.{GameClient, GameServer}
import view.GameView
import view.HostServerView
import view.JoinServerView
import view.SocialView

object EscapeFromTheAliensInOuterSpace {
  val WIDTH: Int = 1320
  val HEIGHT: Int = 1000
  val INTRO_FRAME_HEIGHT: Int = 300
  val INTRO_FRAME_WIDTH: Int = 400
  val TITLE: String = "Escape from the Aliens in Outer Space"
  val VERSION_NUMBER: String = "v0.1"

  def main(args: Array[String]) {
    Application.launch(classOf[EscapeFromTheAliensInOuterSpace], args: _*)
  }

  def createGameClient(
    serverAddress: String,
    portNumber: Int,
    primaryStage: Stage
  ): Unit = {
      primaryStage.hide()
      primaryStage.setScene(GameClient.createGameClient(serverAddress, portNumber))
      primaryStage.show()
  }

  def createGameServer(
    serverAddress: String,
    portNumber: Int,
    primaryStage: Stage
  ): Unit = {
    // TODO: Actually create the server!
    GameServer.createServer(portNumber)

    createGameClient(serverAddress, portNumber, primaryStage)
  }

  def renderConnectToServerWindow(stage: Stage): Scene = {
    val tabPane: TabPane = new TabPane
    tabPane.getTabs.addAll(new JoinServerView(stage), new HostServerView(stage))
    val scene: Scene = new Scene(tabPane, INTRO_FRAME_WIDTH, INTRO_FRAME_HEIGHT)
    scene
  }

//  private def initializeLocalPlayer(username: String): Player = {
//    //TODO: Change the way that characters are created (probably more beneficial once the game lobby is established)
//    val pc: PlayerCharacter = new PlayerCharacter(CharacterType.ALIEN, MapNode.get("A03"))
//    new Player(pc, username)
//  }
}

class EscapeFromTheAliensInOuterSpace extends Application {
  def start(stage: Stage) {
    stage.setTitle(EscapeFromTheAliensInOuterSpace.TITLE + " - " + EscapeFromTheAliensInOuterSpace.VERSION_NUMBER)
    stage.setResizable(false)
    stage.setScene(EscapeFromTheAliensInOuterSpace.renderConnectToServerWindow(stage))
    stage.setOnCloseRequest(e => sys.exit(0))
    stage.show()
  }
}