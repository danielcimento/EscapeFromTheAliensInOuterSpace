package view

import javafx.geometry.Insets
import javafx.scene.control.{Label, TextArea}
import javafx.scene.layout.{AnchorPane, HBox, VBox}

import model.engine.{ActionListener, VisibleGameState}
import model.map.GameMap

class GameView(
  actionListener: ActionListener,
  socialView: SocialView,
  gameMap: GameMap
) extends HBox
  with GameStateListener {

  val mapAndNotifications: VBox = new VBox()
  mapAndNotifications.setPadding(new Insets(GameView.MARGIN_OUTER))
  mapAndNotifications.setSpacing(10)
  mapAndNotifications.setMinHeight(GameView.MAP_HEIGHT)
  mapAndNotifications.setMinWidth(GameView.MAP_WIDTH)

  // Label for the model.map
  private val mapLabel: Label = new Label("Game Map")
  mapAndNotifications.getChildren.add(mapLabel)

  // model.map
  private val mapGroup: AnchorPane = new AnchorPane
  mapGroup.minHeight(GameView.MAP_WIDTH)
  mapGroup.minWidth(GameView.MAP_HEIGHT)
  mapGroup.prefHeight(GameView.MAP_HEIGHT)
  mapGroup.getStyleClass.add("game-window")
  val hexGrid: HexagonGrid = new HexagonGrid(23, 14, 10.0, 10.0, 30, actionListener, gameMap)
  mapGroup.getChildren.add(hexGrid)
  mapAndNotifications.getChildren.add(mapGroup)

  // Label for game notifications
  private val gameNotificationsLabel = new Label("Game Notifications")
  mapAndNotifications.getChildren.add(gameNotificationsLabel)

  // Game notifications
  private val notificationsArea = new TextArea
  notificationsArea.setEditable(false)
  notificationsArea.setWrapText(true)
  mapAndNotifications.getChildren.add(notificationsArea)

  this.getChildren.add(mapAndNotifications)
  this.getChildren.add(socialView)

  override def gameStateChanged(vgs: VisibleGameState) = {
    // TODO: Fill with game state changes
  }

}

object GameView {
  private val MARGIN_OUTER = 10
  private val MAP_WIDTH = 1080
  private val MAP_HEIGHT = 1000
}
