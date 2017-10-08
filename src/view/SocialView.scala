package view

import javafx.geometry.Insets
import javafx.scene.control.ScrollPane.ScrollBarPolicy
import javafx.scene.control.{Label, ScrollPane, TextArea, TextField}
import javafx.scene.layout.VBox

import model.actions.{ChatAction, ChatMessage}
import model.engine.{ActionListener, VisibleGameState}

class SocialView(actionListener: ActionListener) extends VBox with GameStateListener {
  this.setPadding(new Insets(SocialView.MARGIN_OUTER))

  // Text field for sending messages
  private val messageArea = new TextField()
  messageArea.setPromptText("Type chat messages here")
  messageArea.setOnAction(e => sendMessage())

  // Scroll pane with chat messages
//  private val chatScroll: ScrollPane = new ScrollPane
  private val messageDisplay: TextArea = new TextArea
  messageDisplay.setEditable(false)
  messageDisplay.setPrefSize(SocialView.SOCIAL_VIEW_WIDTH, SocialView.MESSAGE_DISPLAY_HEIGHT)
  messageDisplay.setWrapText(true)

  // Scroll pane with current players
  val playersList: TextArea = new TextArea
  playersList.setEditable(false)
  playersList.setPrefSize(SocialView.SOCIAL_VIEW_WIDTH, SocialView.PLAYERS_LIST_HEIGHT)

  // Setting scroll contents and settings
//  chatScroll.setContent(messageDisplay)
//  chatScroll.setPrefSize(SocialView.SOCIAL_VIEW_WIDTH, SocialView.MESSAGE_DISPLAY_HEIGHT)
//  chatScroll.setHbarPolicy(ScrollBarPolicy.NEVER)

  // Labels
  val playersLabel: Label = new Label("Players:")
  val chatLabel: Label = new Label("Chat")

  // Containing VBox
  val chatAndMessages = new VBox
  chatAndMessages.getChildren.addAll(messageDisplay, messageArea)
  chatAndMessages.setSpacing(5)
  this.setSpacing(20)
  this.getChildren.addAll(playersLabel, playersList, chatLabel, chatAndMessages)

  // TODO: Replace string with more meta-data containing format
  def sendMessage(): Unit = {
    val message = messageArea.getText
    messageArea.setText("")
    actionListener.receiveAction(ChatAction(message))
  }

  // TODO: Look into ChatMessage so as to contain metadata for messages
  // This way, two messages with the same text still get shown.
  override def gameStateChanged(vgs: VisibleGameState): Unit = {
    vgs.messages.foreach(msg => if(!messageDisplay.getText.contains(msg)) messageDisplay.appendText(msg + "\n"))
  }
}

object SocialView {
  val MARGIN_OUTER = 10
  val SOCIAL_VIEW_WIDTH = 300
  val MESSAGE_DISPLAY_HEIGHT = 700
  val PLAYERS_LIST_HEIGHT = 180
}
