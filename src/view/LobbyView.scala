package view

import javafx.collections.FXCollections
import javafx.event.Event
import javafx.geometry.{Insets, Pos}
import javafx.scene.Scene
import javafx.scene.control._
import javafx.scene.input.ContextMenuEvent
import javafx.scene.layout._
import javafx.stage.Stage

import main.EscapeFromTheAliensInOuterSpace
import model.actions.{Action, KickAction, ReadyAction}
import model.engine.{ActionListener, Lobby, VisibleGameState}
import model.map.{GameMap, MapConfiguration}
import model.player.{Host, Player}
import net.GameClient

import scala.collection.JavaConverters._

// The Lobby View should contain:
// - An image of the currently selected map (will probably be rendered realtime, just without any listener attached)
// - A grid of setting dropdowns/checkboxes
// - A list of players, and whether or not they're ready to start
// - The `Ready` button

// TODO: Refactor this big mess of UI code
// Players label should only be aligned with listview
// Players listview group should be anchored to the bottom of the scene
// Code should be reorganized to be more readable.
// Add vertical lines behind the dummy map view as well
// Remove list of players during lobby (maybe additional param in the lobby view?)
// Add better chat validation, etc
class LobbyView(actionListener: ActionListener, stage: Stage) extends HBox with GameStateListener {
  val socialView: SocialView = new SocialView(actionListener)
  val mapOptionsPlayers: VBox = new VBox()
  val settingsOptions: GridPane = new GridPane()
  val playersAndReadyButton: HBox = new HBox()

  val cfg: MapConfiguration = MapConfiguration.readConfigurationFromFile(classOf[EscapeFromTheAliensInOuterSpace].getResourceAsStream("resources/galilei.ser"))
  var gameMap: GameMap = GameMap(cfg)

  val mapGroup = new VBox(10)

  val mapLabel: Label = new Label("Selected Map Preview")
  var dummyMap: DummyMap = new DummyMap(gameMap)

  mapGroup.setPadding(new Insets(LobbyView.PADDING))
  mapGroup.setAlignment(Pos.CENTER)
  mapGroup.getStyleClass.add("game-window")
  mapGroup.minHeight(LobbyView.WIDTH)
  mapGroup.minWidth(LobbyView.MAP_HEIGHT)
  mapGroup.prefHeight(LobbyView.MAP_HEIGHT)

  mapGroup.getChildren.addAll(mapLabel, dummyMap)

  val listOfPlayers: ListView[Player] = new ListView()
  listOfPlayers.setPrefWidth(LobbyView.WIDTH * (3.0/4.0))

  // TODO: Add indication of readied players
  listOfPlayers.setCellFactory(lv => {
    val cell: ListCell[Player] = new ListCell[Player] {
      override def updateItem(item: Player, empty: Boolean): Unit = {
        super.updateItem(item, empty)
        if(empty) {
          this.setText("")
          this.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, {e: ContextMenuEvent => e.consume()})
        } else {
          this.setText(formatPlayerItem(item))
          this.setContextMenu(new PlayerListContextMenu(item, actionListener))
        }
      }
    }
    cell
  })

  val optionsGroup: VBox = new VBox(10)

  val settingsLabel: Label = new Label("Settings")

  optionsGroup.getChildren.addAll(settingsLabel, settingsOptions)

  val playersGroup: VBox = new VBox(10)

  val readyButton: Button = new ReadyButton(actionListener)

  val playersLabel: Label = new Label("Players")

  playersAndReadyButton.getChildren.addAll(listOfPlayers, readyButton)
  playersAndReadyButton.setPrefHeight(LobbyView.HEIGHT / 4)
  playersAndReadyButton.setPrefWidth(LobbyView.WIDTH)
  playersAndReadyButton.setSpacing(30)
  playersAndReadyButton.setAlignment(Pos.CENTER)

  playersGroup.getChildren.addAll(playersLabel, playersAndReadyButton)

  mapOptionsPlayers.getChildren.addAll(mapGroup, optionsGroup, playersGroup)
  this.getChildren.addAll(mapOptionsPlayers, socialView)

  override def gameStateChanged(vgs: VisibleGameState): Unit = {
    if(vgs.gameStatus != Lobby){
      // Render UI for game view
      // TODO: Move the entire map configuration handling to the server side. Clients should NEVER be allowed to have mismatching maps
      val cfg: MapConfiguration = MapConfiguration.readConfigurationFromFile(classOf[EscapeFromTheAliensInOuterSpace].getResourceAsStream("resources/galilei.ser"))
      val gameMap: GameMap = GameMap(cfg)
      // TODO: Determine whether socialView needs to be the same instance or whether creating a new one and letting updating take over is fine
      val root: GameView = new GameView(GameClient, socialView, gameMap)
      // socialView currently is/should be already registered as a game state listener, so it doesn't need to be added again
      GameClient.clientHandler.registerGameStateListener(root)

      val scene: Scene = new Scene(root, EscapeFromTheAliensInOuterSpace.WIDTH, EscapeFromTheAliensInOuterSpace.HEIGHT)
      scene.getStylesheets.add(classOf[EscapeFromTheAliensInOuterSpace].getResource("resources/stylesheet.css").toExternalForm)
      stage.setScene(scene)
    } else {
      if(vgs.gameMap != gameMap) {
        gameMap = vgs.gameMap
        dummyMap = new DummyMap(gameMap)
      }
      // TODO: Remove redundant element updating
      listOfPlayers.setItems(FXCollections.observableList(vgs.players.asJava))
    }
  }

  // TODO: Come up with better formatting
  private def formatPlayerItem(player: Player): String = {
    player.username + (if(player.role == Host) " [Host]" else "")
  }
}

// This is sealed because declaring it private caused some weird scope errors that I didn't want to deal with...
sealed class DummyMap(gameMap: GameMap) extends HexagonGrid(23, 14, 10.0, 10.0, 20, action => {}, gameMap, addLabels = false) {}

private class ReadyButton(actionListener: ActionListener) extends Button("Ready") {
  this.setOnAction(e => {
    if(this.getText == "Ready"){
      actionListener.receiveAction(ReadyAction(true))
      this.setText("Not Ready")
    } else {
      actionListener.receiveAction(ReadyAction(false))
      this.setText("Ready")
    }
  })
}

private class PlayerListContextMenu(player: Player, actionListener: ActionListener) extends ContextMenu {
  val kickPlayer: MenuItem = new MenuItem()
  kickPlayer.setOnAction(e => {
    actionListener.receiveAction(KickAction(player))
  })
}

object LobbyView {
  val PADDING = 10
  val WIDTH = EscapeFromTheAliensInOuterSpace.WIDTH - SocialView.SOCIAL_VIEW_WIDTH
  val HEIGHT = EscapeFromTheAliensInOuterSpace.HEIGHT
  val MAP_WIDTH = 1280
  val MAP_HEIGHT = 720
}