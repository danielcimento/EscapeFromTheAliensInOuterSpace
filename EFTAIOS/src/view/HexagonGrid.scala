package view

import javafx.scene.paint.Color
import javafx.scene.shape.StrokeType
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.text.Text

import model.map._
import model.engine.{ActionListener, GameClientController, GameEngine}

/*for mathematics' sake, if we imagine a square grid of hexagons with size n,
* then the center of the first hexagon is at (n, sqrt(3)*n/2)
*/

// TODO: Refactor the client/engine parameter that gets passed in
/**
  * @param lengthCount = the number of hexagons that make up the horizontal edge
  * @param heightCount = the number of hexagons that make up the vertical edge
  * @param topLeftX    = the x coordinate of the bottom left coordinate of the grid
  * @param topLeftY    = the y coordinate of the bottom left coordinate of the grid
  * @param size        = the desired size of each hexagon
  **/
class HexagonGrid(
  val lengthCount: Int,
  val heightCount: Int,
  val topLeftX: Double,
  val topLeftY: Double,
  val size: Double,
  val actionListener: ActionListener,
  val gameMap: GameMap
) extends Group() with MovementListener {

  // In a name with format "c##", the i co-ordinate is c - 'A'. The j co-ordinate is ## - 1
  private def getRelativePositionFromName(name: String): (Int, Int) = {
    val i: Int = name(0) - 'A'
    val j: Int = name.substring(1).toInt - 1
    (i, j)
  }

  // Initialization code
  {
    val centerXOfFirstHex: Double = size
    val centerYOfFirstHex: Double = Math.sqrt(3) * size / 2
    gameMap.findTiles(_ => true).foreach { tile =>
      val (i, j) = getRelativePositionFromName(tile.name)

      //the if we are on the nth horizontal hexagon, and
      //n is even (starting @ 0), then we shift down an extra
      //sqrt(3)n/2
      //standardly, the (i, j)th horizontal hexagon appears at
      //{(start + (n + sqrt(3)n/2)*i), start + (sqrt(3)n)*j}
      val currentXCoordinate = centerXOfFirstHex + ((size + (size/2)) * i)
      val verticalShift = if(i % 2 == 0) 0.0 else Math.sqrt(3) * size / 2
      val currentYCoordinate = centerYOfFirstHex + ((Math.sqrt(3) * size) * j) + verticalShift

      val thisNodeType = tile.nodeType

      val label: Text = new Text(currentXCoordinate - (size/3), currentYCoordinate+(size/5), tile.name)
      label.setMouseTransparent(true)
      val hex: HexagonShape = new HexagonShape(currentXCoordinate, currentYCoordinate, size, tile, actionListener)
      // We don't want to render tiles that are non-interactive. Special tiles don't have a label on them.
      thisNodeType match {
        case Blocked =>
          hex.setVisible(false)
          label.setVisible(false)
        case Alien | EscapeHatch | Human =>
          label.setVisible(false)
        case _ => // no-op
      }
      getChildren.addAll(hex, label)
    }
  }

  //The location of the local player should be bordered with orange.
  // TODO: Consider HexShape immutability refactoring.
  def updateLocalPlayerLocation(m: MapNode) {
    this.getChildren forEach {
      case oldHex: HexagonShape if oldHex.isSelectedStroke => oldHex.setDefaultStroke()
      case newHex: HexagonShape if m.name == newHex.mapNode.name => newHex.setSelectedStroke()
    }
  }
}
