package net

import io.netty.channel.{ChannelHandler, ChannelHandlerContext, ChannelInboundHandlerAdapter, SimpleChannelInboundHandler}
import model.actions.Action
import model.engine.{ActionListener, VisibleGameState}
import view.GameStateListener

class GameClientHandler extends SimpleChannelInboundHandler[VisibleGameState] {
  var gameStateListeners: List[GameStateListener] = List()

  override def channelRead0(ctx: ChannelHandlerContext, msg: VisibleGameState): Unit = {
    gameStateListeners.foreach(_.gameStateChanged(msg))
  }

  def registerGameStateListener(gameStateListener: GameStateListener): Unit = {
    gameStateListeners = gameStateListener :: gameStateListeners
  }
}
