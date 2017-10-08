package view

import javafx.scene.paint.Color
import javafx.scene.shape.Polygon
import javafx.scene.shape.StrokeType

import model.actions.MoveAction
import model.engine.ActionListener
import model.map._

/* Mathematics of Hexagons (for personal reference). When creating a hexagon centered at x, y with a distance of
 * n between each of its corners:
 * recall that a 30-60-90 triangle with a hypotenuse of length n, the length of the side opposite 30 degrees
 * is n/2. The length of the side opposite to 60 degrees is sqrt(3)*n/2
 * */
/**
  * @author Daniel Cimento & Tristan Shoemaker
  *         Huge thanks to Tristan for helping me debug my geometry calculations!
  **/
object HexagonShape {
  private val DARKEST_GREY = Color.rgb(100, 100, 100)

  /**
    * With a hypotenuse of length n, this function returns the length of the longer side in a 30-60-90 triangle
    **/
  private def calculateLongSide(n: Double) = (Math.sqrt(3) * n) / 2

  private def calculateShortSide(n: Double) = n / 2

  private def calculateCoordinates(xCoordinate: Double, yCoordinate: Double, n: Double): List[Double] = {
    //hexagon has 6 coordinates, therefore 12 points are necessary
    List(
      // Point 1 is n to the right of the center
      xCoordinate + n,
      yCoordinate,
      // Point 2 is n/2 to the right and sqrt(3)n/2 up
      xCoordinate + calculateShortSide(n),
      yCoordinate + calculateLongSide(n),
      // Point 3 is " left and " up
      xCoordinate - calculateShortSide(n),
      yCoordinate + calculateLongSide(n),
      // Point 4 : The next 3 points are symmetric to the first 3
      xCoordinate - n,
      yCoordinate,
      // Point 5
      xCoordinate - calculateShortSide(n),
      yCoordinate - calculateLongSide(n),
      // Point 6
      xCoordinate + calculateShortSide(n),
      yCoordinate - calculateLongSide(n)
    )
  }
}

/**
  * Builds a hexagon for use with JavaFX
  * @param xCoordinate The center horizontal coordinate of the hexagon in the cartesian plane
  * @param yCoordinate The center vertical coordinate of the hexagon in the cartesian plane
  * @param size The "radius" of the hexagon, measured as the circle which circumscribes the hexagon
  * @param mapNode The model.map's node which this hexagon is meant to visually represent
  * @param actionListener The controller layer to allow callbacks on click
  */
class HexagonShape(
  val xCoordinate: Double,
  val yCoordinate: Double,
  val size: Double,
  val mapNode: MapNode,
  val actionListener: ActionListener
) extends Polygon(HexagonShape.calculateCoordinates(xCoordinate, yCoordinate, size): _*) {
  // Initialization Code
  {
    setFill(getHexColor(mapNode.nodeType))
    setDefaultStroke()
    setOnMouseClicked(e => actionListener.receiveAction(MoveAction(mapNode.name)))
  }

  private def getHexColor(t: NodeType): Color = {
    t match {
      case Alien => Color.RED
      case Human => Color.PINK
      case Dangerous => Color.SILVER
      case Secure | Blocked => Color.WHITE
      case EscapeHatch => Color.GREEN
    }
  }

  def setDefaultStroke() {
    setStroke(HexagonShape.DARKEST_GREY)
    setStrokeWidth(2.0)
    setStrokeType(StrokeType.CENTERED)
  }

  def setSelectedStroke() {
    setStroke(Color.ORANGE)
    setStrokeWidth(3.0)
    setStrokeType(StrokeType.INSIDE)
  }

  def isSelectedStroke: Boolean = getStroke == Color.ORANGE && getStrokeType == StrokeType.INSIDE && getStrokeWidth == 3.0
}
