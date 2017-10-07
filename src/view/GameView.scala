package view

import javafx.geometry.Insets
import javafx.scene.control.{Label, TextArea}
import javafx.scene.layout.{AnchorPane, VBox}

import model.engine.{ActionListener, VisibleGameState}
import model.map.GameMap

class GameView(
  actionListener: ActionListener,
  gameMap: GameMap
) extends VBox
  with GameStateListener {

  this.setPadding(new Insets(GameView.MARGIN_OUTER))
  this.setSpacing(10)
  this.setMinHeight(GameView.MAP_HEIGHT)
  this.setMinWidth(GameView.MAP_WIDTH)

  // Label for the model.map
  val mapLabel: Label = new Label("Game Map")
  this.getChildren.add(mapLabel)

  // model.map
  val mapGroup: AnchorPane = new AnchorPane
  mapGroup.minHeight(GameView.MAP_WIDTH)
  mapGroup.minWidth(GameView.MAP_HEIGHT)
  mapGroup.prefHeight(GameView.MAP_HEIGHT)
  mapGroup.getStyleClass.add("game-window")
  val hexGrid: HexagonGrid = new HexagonGrid(23, 14, 10.0, 10.0, 30, actionListener, gameMap)
  mapGroup.getChildren.add(hexGrid)
  this.getChildren.add(mapGroup)

  // Label for game notifications
  val gameNotificationsLabel = new Label("Game Notifications")
  this.getChildren.add(gameNotificationsLabel)

  // Game notifications
  private val notificationsArea = new TextArea
  notificationsArea.setEditable(false)
  notificationsArea.setWrapText(true)
  this.getChildren.add(notificationsArea)

  override def gameStateChanged(vgs: VisibleGameState) = {
    // TODO: Fill with game state changes
  }

}

object GameView {
  private val MARGIN_OUTER = 10
  private val MAP_WIDTH = 1080
  private val MAP_HEIGHT = 1000
}
