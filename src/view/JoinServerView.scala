package view

import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.geometry.{HPos, Pos}
import javafx.scene.control._
import javafx.scene.layout.GridPane
import javafx.stage.Stage

import main.EscapeFromTheAliensInOuterSpace

class JoinServerView(stage: Stage) extends Tab {
  this.setText("Join a Server")
  this.setClosable(false)

  private val usernameLabel: Label = new Label("Username: ")
  private val username: TextField = new TextField
  private val serverAddressLabel: Label = new Label("Server Address: ")
  private val serverAddress: TextField = new TextField
  private val portLabel: Label = new Label("Port: ")
  private val port: TextField = new TextField

  username.setTooltip(new Tooltip("This will be your display name during the game"))
  serverAddress.setTooltip(new Tooltip(
    "The IP Address of the server you wish to connect to.\n" +
    "Don't know it?\n" +
    "Ask the host!"
  ))
  port.setTooltip(new Tooltip(
    "The port on which the host is running the server.\n" +
    "By default this is 6789.\n" +
    "If this does not work, try asking the host!"
  ))

  port.textProperty().addListener(numericRegexListener(port))
  port.setText("6789")

  private val joinButton: Button = new Button("Join")
  joinButton.setOnAction(e => joinServer())

  val frame: GridPane = new GridPane
  frame.setAlignment(Pos.CENTER)
  frame.setHgap(12)
  frame.setVgap(10)
  frame.add(usernameLabel, 0, 0)
  frame.add(username, 1, 0)
  frame.add(serverAddressLabel, 0, 1)
  frame.add(serverAddress, 1, 1)
  frame.add(portLabel, 0, 2)
  frame.add(port, 1, 2)
  frame.add(joinButton, 0, 3, 2, 1)
  GridPane.setHalignment(joinButton, HPos.CENTER)

  this.setContent(frame)

  private def joinServer() = {
    val serverAddressText = serverAddress.getText
    val portNumberText = port.getText
    val portNumber = parsePortNumber(portNumberText)
    EscapeFromTheAliensInOuterSpace.createGameClient(serverAddressText, portNumber, stage)
  }
}
