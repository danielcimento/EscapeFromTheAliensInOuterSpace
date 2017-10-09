package net

import java.net.InetSocketAddress

import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.group.{ChannelGroup, ChannelGroupFuture, DefaultChannelGroup}
import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}
import io.netty.util.concurrent.GlobalEventExecutor
import model.actions.{Action, ChatAction, QueryStateAction}
import model.engine.{GameEngine, GameLobby, GameState, VisibleGameState}

@Sharable
class GameServerHandler extends SimpleChannelInboundHandler[Action] {
  // Keep track of the players who have connected to the server.
  val channelGroup: ChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE)
  var gameState: GameState = GameLobby(List.empty, List.empty)

  override def channelActive(ctx: ChannelHandlerContext): Unit = {
    val hostName = getHostname(ctx)
    gameState = gameState.processAction(hostName, ChatAction(hostName + " has joined the game!"))
    channelGroup.add(ctx.channel())
  }

  // TODO: Figure out why the server no longer sends back visible game state (or why the client doesn't process it)
  override def channelRead0(ctx: ChannelHandlerContext, msg: Action): Unit = {
    gameState = gameState.processAction(getHostname(ctx), msg)
    msg match {
      case query: QueryStateAction =>
        ctx.channel().write(gameState.generateVisibleGameState(getHostname(ctx)))
      case _ =>
        channelGroup.forEach(channel => {
          channel.write(
            gameState.generateVisibleGameState(getHostname(channel))
          )
        })
    }
  }

  override def channelReadComplete(ctx: ChannelHandlerContext): Unit = {
    ctx.flush()
  }


}
