package net

import io.netty.channel.{ChannelHandlerContext, SimpleChannelInboundHandler}


// TODO: Replace with correct server read type
class GameServerHandler extends SimpleChannelInboundHandler[String] {
  override def channelRead0(ctx: ChannelHandlerContext, msg: String): Unit = {
    // TODO: Define Server Read Action
  }
}
