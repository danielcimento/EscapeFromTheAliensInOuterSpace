package main

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.TabPane
import javafx.scene.layout.HBox
import javafx.stage.Stage

import model.map._
import model.CharacterType
import model.Player
import model.engine.GameClient
import model.player.PlayerCharacter
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
    username: String,
    primaryStage: Stage): GameClient = {
      primaryStage.hide()
      val client = new GameClient(initializeLocalPlayer(username), serverAddress, portNumber)
      new Thread(client).start()
      primaryStage.setScene(renderGameWindow(client))
      primaryStage.show()
      client
  }

  def renderConnectToServerWindow: Scene = {
    val tabPane: TabPane = new TabPane
    tabPane.getTabs.addAll(new JoinServerView, new HostServerView)
    val scene: Scene = new Scene(tabPane, INTRO_FRAME_WIDTH, INTRO_FRAME_HEIGHT)
    scene
  }

  def renderGameWindow(client: GameClient): Scene = {
    val root: HBox = new HBox
    //MapConfiguration cfg = MapConfiguration.loadFromFile("src/main.resources/fermi.ser");
    //MapConfiguration cfg = MapConfiguration.loadFromFile("src/main.resources/galilei.ser");
    val cfg: MapConfiguration = MapConfiguration.readConfigurationFromFile(classOf[EscapeFromTheAliensInOuterSpace].getResourceAsStream("resources/galilei.ser"))
    GameMap(cfg)
    root.getChildren.add(new GameView(client))
    root.getChildren.add(new SocialView(client))
    val scene: Scene = new Scene(root, WIDTH, HEIGHT)
    scene.getStylesheets.add(classOf[EscapeFromTheAliensInOuterSpace].getResource("resources/stylesheet.css").toExternalForm)
    scene
  }

  private def initializeLocalPlayer(username: String): Player = {
    //TODO: Change the way that characters are created (probably more beneficial once the game lobby is established)
    val pc: PlayerCharacter = new PlayerCharacter(CharacterType.ALIEN, MapNode.get("A03"))
    new Player(pc, username)
  }
}

class EscapeFromTheAliensInOuterSpace extends Application {
  def start(stage: Stage) {
    stage.setTitle(EscapeFromTheAliensInOuterSpace.TITLE + " - " + EscapeFromTheAliensInOuterSpace.VERSION_NUMBER)
    stage.setResizable(false)
    stage.setScene(EscapeFromTheAliensInOuterSpace.renderConnectToServerWindow)
    stage.setOnCloseRequest(e => sys.exit(0))
    stage.show()
  }
}