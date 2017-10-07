package net

import io.netty.channel.{ChannelHandler, ChannelHandlerContext, ChannelInboundHandlerAdapter, SimpleChannelInboundHandler}
import model.engine.{GameListener, VisibleGameState}

class GameClientHandler extends SimpleChannelInboundHandler[VisibleGameState] {
  override def channelRead0(ctx: ChannelHandlerContext, msg: VisibleGameState): Unit = {
    System.out.println(msg)
  }

  def registerGameStateListener(gameListener: GameListener) = {
    // TODO: Manage list of listeners and query on update
  }
}
