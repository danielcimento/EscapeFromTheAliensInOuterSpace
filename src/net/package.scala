import java.net.{InetAddress, InetSocketAddress}

import io.netty.channel.{Channel, ChannelHandlerContext}

package object net {
  def getHostname(ctx: ChannelHandlerContext): String = {
    ctx.channel().remoteAddress().asInstanceOf[InetSocketAddress].getAddress.getHostAddress
  }

  def getHostname(channel: Channel): String = {
    channel.remoteAddress().asInstanceOf[InetSocketAddress].getAddress.getHostAddress
  }
}
