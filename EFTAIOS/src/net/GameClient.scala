package net

import javafx.scene.Scene
import javafx.scene.layout.HBox

import io.netty.channel.nio._
import io.netty.bootstrap._
import io.netty.channel._
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.serialization.{ClassResolvers, ObjectDecoder, ObjectEncoder}
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import io.netty.handler.ssl.{SslContext, SslContextBuilder}
import main.EscapeFromTheAliensInOuterSpace
import model.map.{GameMap, MapConfiguration}
import view.{GameView, SocialView}

object GameClient {
  val SSL: Boolean = System.getProperty("ssl") != null

  def createGameClient(host: String, port: Int): Scene = {
    val clientHandler: GameClientHandler = new GameClientHandler()

    val sslCtx: Option[SslContext] =
      if(SSL)
        Some(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build())
      else
        None

    val group: EventLoopGroup = new NioEventLoopGroup()
    try {
      val bootstrap: Bootstrap = new Bootstrap()
      bootstrap
        .group(group)
        .channel(classOf[NioSocketChannel])
        .handler(new ChannelInitializer[SocketChannel] {
          override def initChannel(ch: SocketChannel) = {
            val p: ChannelPipeline = ch.pipeline()
            sslCtx match {
              case Some(context) => p.addLast(context.newHandler(ch.alloc(), host, port))
              case None => // NOOP
            }

            p.addLast(
              new ObjectEncoder(),
              new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
              clientHandler
            )
          }
        })

      val ch: Channel = bootstrap.connect(host, port).sync().channel()

      // Render UI
      val root: HBox = new HBox
      val cfg: MapConfiguration = MapConfiguration.readConfigurationFromFile(classOf[EscapeFromTheAliensInOuterSpace].getResourceAsStream("resources/galilei.ser"))
      val gameMap: GameMap = GameMap(cfg)

      val gameView: GameView = new GameView(clientHandler, gameMap)
      val socialView: SocialView = new SocialView(clientHandler)

      // Whenever we receive a new game state from the server, we need to pass it to the views so they can update
      clientHandler.registerGameStateListener(gameView)
      clientHandler.registerGameStateListener(socialView)

      root.getChildren.add(gameView)
      root.getChildren.add(socialView)

      val scene: Scene = new Scene(root, EscapeFromTheAliensInOuterSpace.WIDTH, EscapeFromTheAliensInOuterSpace.HEIGHT)
      scene.getStylesheets.add(classOf[EscapeFromTheAliensInOuterSpace].getResource("resources/stylesheet.css").toExternalForm)
      scene

      // TODO: Continue to listen to server
    } finally {
      group.shutdownGracefully()
    }
  }
}
