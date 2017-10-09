package view

import javafx.collections.FXCollections
import javafx.event.Event
import javafx.scene.control._
import javafx.scene.input.ContextMenuEvent
import javafx.scene.layout.{GridPane, HBox}

import model.actions.{KickAction, ReadyAction}
import model.engine.{ActionListener, Lobby, VisibleGameState}
import model.player.{Host, Player}

import scala.collection.JavaConverters._

class LobbyView(actionListener: ActionListener) extends HBox with GameStateListener {
  val socialView: SocialView = new SocialView(actionListener)

  val listOfPlayers: ListView[Player] = new ListView()

  listOfPlayers.setCellFactory(lv => {
    val cell: ListCell[Player] = new ListCell[Player] {
      override def updateItem(item: Player, empty: Boolean): Unit = {
        super.updateItem(item, empty)
        if(empty) {
          this.setText("")
          this.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, _.consume())
        } else {
          this.setText(formatPlayerItem(item))
          this.setContextMenu(new PlayerListContextMenu(item, actionListener))
        }
      }
    }
    cell
  })

  // TODO: Add indication of readied players
  val readyButton: Button = new Button("Ready")
  readyButton.setOnAction(e => {
    if(readyButton.getText == "Ready"){
      actionListener.receiveAction(ReadyAction(true))
      readyButton.setText("Not Ready")
    } else {
      actionListener.receiveAction(ReadyAction(false))
      readyButton.setText("Ready")
    }
  })

  // TODO: Add settings!
  val settingsAndPlayers: GridPane = new GridPane()
  settingsAndPlayers.add(listOfPlayers, 0, 1)
  settingsAndPlayers.add(readyButton, 1, 1)

  this.getChildren.addAll(settingsAndPlayers, socialView)

  // TODO: Remove redundant element updating
  override def gameStateChanged(vgs: VisibleGameState): Unit = {
    if(vgs.gameStatus != Lobby){
      // TODO: Add code to transform scene into game view
    } else {
      listOfPlayers.setItems(FXCollections.observableList(vgs.players.asJava))
    }
  }

  // TODO: Come up with better formatting
  private def formatPlayerItem(player: Player): String = {
    player.username + (if(player.role == Host) " [Host]" else "")
  }
}

object LobbyView {

}

private class PlayerListContextMenu(player: Player, actionListener: ActionListener) extends ContextMenu {
  val kickPlayer: MenuItem = new MenuItem()
  kickPlayer.setOnAction(e => {
    actionListener.receiveAction(KickAction(player))
  })
}