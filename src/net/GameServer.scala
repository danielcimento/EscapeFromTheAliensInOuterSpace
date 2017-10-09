package net


import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.{ChannelInitializer, ChannelPipeline, EventLoopGroup}
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.serialization.{ClassResolvers, ObjectDecoder, ObjectEncoder}
import io.netty.handler.logging.{LogLevel, LoggingHandler}
import io.netty.handler.ssl.{SslContext, SslContextBuilder}
import io.netty.handler.ssl.util.SelfSignedCertificate

object GameServer {
  val SSL: Boolean = System.getProperty("ssl") != null
  val serverHandler: GameServerHandler = new GameServerHandler()

  def createServer(port: Int): Unit = {
    new Thread(() => {
        val sslCtx: Option[SslContext] =
          if(SSL) {
            val ssc: SelfSignedCertificate = new SelfSignedCertificate()
            Some(SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build())
          } else {
            None
          }
        val bossGroup: EventLoopGroup = new NioEventLoopGroup(1)
        val workerGroup: EventLoopGroup = new NioEventLoopGroup()
        try {
          val bootstrap: ServerBootstrap = new ServerBootstrap()
          bootstrap
            .group(bossGroup, workerGroup)
            .channel(classOf[NioServerSocketChannel])
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer[SocketChannel] {
              override def initChannel(ch: SocketChannel) = {
                val p: ChannelPipeline = ch.pipeline()
                sslCtx match {
                  case Some(context) => p.addLast(context.newHandler(ch.alloc()))
                  case None => // NOOP
                }
                p.addLast(
                  new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                  new ObjectEncoder(),
                  serverHandler
                )
              }
            })
          bootstrap.bind(port).sync().channel().closeFuture().sync()
        } finally {
          bossGroup.shutdownGracefully()
          workerGroup.shutdownGracefully()
        }
      }).start()
  }

}
