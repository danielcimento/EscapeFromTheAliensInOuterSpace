package model.engine

import java.io.{BufferedInputStream, BufferedOutputStream, ObjectInputStream, ObjectOutputStream}
import java.net.Socket

import net.{PlayerCreationMessage, ServerInformation}

@Deprecated
class GameClient(
  var gameEngine: GameEngine,
  serverInformation: ServerInformation,
  username: String
) extends Runnable {

  private def registerPlayerToServer() = {
    val clientConnection = new Socket(serverInformation.hostIpAddress, serverInformation.hostPort)
    val out = new ObjectOutputStream(new BufferedOutputStream(clientConnection.getOutputStream))
    out.writeObject(PlayerCreationMessage(username, serverInformation.identifier))
  }

  override def run(): Unit = {
    registerPlayerToServer()
  }
}
