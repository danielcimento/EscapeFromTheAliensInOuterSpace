package net

import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.Stage

import io.netty.channel.nio._
import io.netty.bootstrap._
import io.netty.channel._
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.serialization.{ClassResolvers, ObjectDecoder, ObjectEncoder}
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import io.netty.handler.ssl.{SslContext, SslContextBuilder}
import main.EscapeFromTheAliensInOuterSpace
import model.actions.{Action, QueryStateAction}
import model.engine.ActionListener
import model.map.{GameMap, MapConfiguration}
import view.{GameView, LobbyView, SocialView}

object GameClient extends ActionListener {
  val SSL: Boolean = System.getProperty("ssl") != null
  val clientHandler: GameClientHandler = new GameClientHandler()
  var channel: Option[Channel] = None

  override def receiveAction(action: Action): Unit = {
    channel.get.writeAndFlush(action)
  }

  // Stage will be used later so the LobbyView knows which stage to set as the GameView when it's done
  def createScene(stage: Stage): Scene = {
    val lobbyView: LobbyView = new LobbyView(GameClient, stage)
    clientHandler.registerGameStateListener(lobbyView)
    clientHandler.registerGameStateListener(lobbyView.socialView)
    val scene: Scene = new Scene(lobbyView, EscapeFromTheAliensInOuterSpace.WIDTH, EscapeFromTheAliensInOuterSpace.HEIGHT)
    scene
  }

  def createGameClient(host: String, port: Int): Unit = {
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
      channel = Some(bootstrap.connect(host, port).sync().channel())
      while(true){
        // Continually query the server for the game state
        channel.get.writeAndFlush(QueryStateAction())
        Thread.sleep(4000)
      }
    } finally {
      group.shutdownGracefully()
    }
  }
}
