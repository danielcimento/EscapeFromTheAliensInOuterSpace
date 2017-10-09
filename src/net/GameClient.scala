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
import view.{GameView, SocialView}

object GameClient extends ActionListener {
  val SSL: Boolean = System.getProperty("ssl") != null
  val clientHandler: GameClientHandler = new GameClientHandler()
  var channel: Option[Channel] = None

  override def receiveAction(action: Action): Unit = {
    channel.get.writeAndFlush(action)
  }

  // TODO: Change this to render the LobbyView
  // Stage will be used later so the LobbyView knows which stage to set as the GameView when it's done
  def createScene(stage: Stage): Scene = {
    // Render UI
    val root: HBox = new HBox
    // TODO: Move the entire map configuration handling to the server side. Clients should NEVER be allowed to have mismatching maps
    val cfg: MapConfiguration = MapConfiguration.readConfigurationFromFile(classOf[EscapeFromTheAliensInOuterSpace].getResourceAsStream("resources/galilei.ser"))
    val gameMap: GameMap = GameMap(cfg)
    // TODO: Make the socialView a part of the GameView, instead of orthogonal to it.
    val gameView: GameView = new GameView(this, gameMap)
    val socialView: SocialView = new SocialView(this)

    // Whenever we receive a new game state from the server, we need to pass it to the views so they can update
    clientHandler.registerGameStateListener(gameView)
    clientHandler.registerGameStateListener(socialView)

    root.getChildren.add(gameView)
    root.getChildren.add(socialView)

    val scene: Scene = new Scene(root, EscapeFromTheAliensInOuterSpace.WIDTH, EscapeFromTheAliensInOuterSpace.HEIGHT)
    scene.getStylesheets.add(classOf[EscapeFromTheAliensInOuterSpace].getResource("resources/stylesheet.css").toExternalForm)

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
        // TODO: This loop may be a bit unnecessary, as every time the state is changed, every listener gets notified anyway
        // Maybe make the delay a bit longer to compensate for the redundancy
        channel.get.writeAndFlush(QueryStateAction())
        Thread.sleep(4000)
      }
    } finally {
      group.shutdownGracefully()
    }
  }
}
