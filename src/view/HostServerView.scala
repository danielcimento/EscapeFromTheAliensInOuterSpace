package view

import java.net.{Inet4Address, InetAddress, UnknownHostException}
import javafx.geometry.{HPos, Pos}
import javafx.scene.control._
import javafx.scene.layout.GridPane
import javafx.stage.Stage

import main.EscapeFromTheAliensInOuterSpace

class HostServerView(stage: Stage) extends Tab {
  this.setText("Host a Server")
  this.setClosable(false)

  private val usernameLabel: Label = new Label("Username: ")
  private val username: TextField = new TextField
  username.setTooltip(new Tooltip("This will be your display name in game."))

  private val portLabel: Label = new Label("Port: ")
  private val port: TextField = new TextField()
  port.setTooltip(new Tooltip(
    "Don't know what this means?\n" +
    "Leave it as 6789.\n" +
    "You may need to forward your ports."
  ))
  port.setText("6789")
  port.textProperty().addListener(numericRegexListener(port))

  private val hostButton: Button = new Button("Host")
  hostButton.setOnAction(e => createServer())

  val frame = new GridPane
  frame.setAlignment(Pos.CENTER)
  frame.setHgap(12)
  frame.setVgap(10)
  frame.add(usernameLabel, 0, 0)
  frame.add(username, 1, 0)
  frame.add(portLabel, 0, 1)
  frame.add(port, 1, 1)
  frame.add(hostButton, 0, 2, 2, 1)
  GridPane.setHalignment(hostButton, HPos.CENTER)
  this.setContent(frame)

  private def createServer() = {
    val portNumberText = port.getText
    val portNumber = parsePortNumber(portNumberText)
    val localHost: String = try {
      InetAddress.getLocalHost.getHostAddress
    } catch {
      case ex: UnknownHostException =>
        ex.printStackTrace()
        "localhost"
    }

    EscapeFromTheAliensInOuterSpace.createGameServer(localHost, portNumber, stage)
  }
}
