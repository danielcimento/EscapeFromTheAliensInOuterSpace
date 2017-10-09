package model.map

import model.player.PlayerCharacter

import scala.collection.immutable.IndexedSeq
import scala.collection.immutable.NumericRange.Inclusive
import scalaz.syntax.std.boolean._
import scala.languageFeature.implicitConversions

class GameMap(nodeGraph: Graph[MapNode]) extends Serializable {
  def findTiles(predicate: MapNode => Boolean): Set[MapNode] = nodeGraph.findVertex(predicate)

  // TODO: Fix problem where players can move to/through blocked tiles
  def isTraversable(startPosition: MapNode, endPosition: MapNode, performer: PlayerCharacter) = {
    performer match {
      case PlayerCharacter(model.player.Alien, _, _) => nodeGraph(startPosition, endPosition, 2)
      case PlayerCharacter(model.player.Human, _, _) => nodeGraph(startPosition, endPosition)
      case _ => false
    }
  }

  def updateTile(oldNode: MapNode, newNode: MapNode): GameMap =
    new GameMap(nodeGraph.updateVertex(oldNode, newNode))
}

object GameMap {
  val nodePrefixes: Inclusive[Char] = 'A' to 'W'
  val nodeSuffixes: IndexedSeq[String] = (1 to 14) map ("%02d".format(_))

  private def nextSuffix(suffix: String) = "%02d".format(suffix.toInt + 1)
  private def isLast(prefix: Char) = prefix == 'W'
  private def isFirst(prefix: Char) = prefix == 'A'
  private def isLast(suffix: String) = suffix == "14"
  // In the EFTAIOS board, every other row is shifted 1 higher, on account of the hex
  // nature of the board. This helper tells us whether a prefix's column is one such
  // column. The first such column is "A" which is odd in ascii, hence all odd chars
  // are higher columns
  private def isHigherColumn(prefix: Char) = {
    prefix % 2 == 1
  }

  val characterToNodeType = Map(
    'b' -> Blocked,
    's' -> Secure,
    'd' -> Dangerous,
    'a' -> Alien,
    'h' -> Human,
    'e' -> EscapeHatch
  )

  def createAllNodes(cfg: MapConfiguration): Set[MapNode] = {
    val nodeIds = for(
      prefix <- nodePrefixes;
      suffix <- nodeSuffixes
    ) yield prefix + suffix

    nodeIds.map(id => {
      val nodeType = cfg(id)
      MapNode(id, nodeType, List())
    }).toSet
  }

  def convertToMap(nodeSet: Set[MapNode]): Map[String, MapNode] = {
    nodeSet.map(node => node.name -> node).toMap
  }

  /**
    * Creates a list of edges used to create the game board graph. Since whether tiles are adjacent
    * can be viewed more easily by their labels, we use another for comprehension. In this algorithm,
    * each label looks for the tiles directly below and to the lower left and right, creating an edge
    * between them. If the tiles are on a boundary, no edge is created. Since two adjacent tiles
    * have to have one higher than the other, all edges will be created with no duplicates
    * @param nodeMap A helper model.map which connects the label of a node to its node
    * @return A list of all undirected edges between the two nodes
    */
  def createEdges(nodeMap: Map[String, MapNode]): Set[Edge[MapNode]] = {
    val allEdges: Set[Set[Edge[MapNode]]] =
      (for(prefix <- nodePrefixes; suffix <- nodeSuffixes) yield {
        val currNode = nodeMap(prefix + suffix)


        val lowerNodeEdge = !isLast(suffix) option {
          Edge(currNode, nodeMap(s"$prefix${nextSuffix(suffix)}"))
        }
        val lowerLeftNodeEdge = !isFirst(prefix) option {
          if(isHigherColumn(prefix)){
            Some(Edge(currNode, nodeMap(s"${(prefix - 1).toChar}$suffix")))
          } else if(!isLast(suffix)){
            Some(Edge(currNode, nodeMap(s"${(prefix - 1).toChar}${nextSuffix(suffix)}")))
          } else {
            None
          }
        } flatMap identity
        // For some reason, using flatten converts the option to an iterable and causes things to break wrt breakOut
        val lowerRightNodeEdge = !isLast(prefix) option {
          if(isHigherColumn(prefix)){
            Some(Edge(currNode, nodeMap(s"${(prefix + 1).toChar}$suffix")))
          } else if(!isLast(suffix)) {
            Some(Edge(currNode, nodeMap(s"${(prefix + 1).toChar}${nextSuffix(suffix)}")))
          } else {
            None
          }
        } flatMap identity
        Set(lowerNodeEdge, lowerLeftNodeEdge, lowerRightNodeEdge).filter(_.isDefined).map(_.get)
      })(collection.breakOut)

    allEdges.flatten
  }

  def apply(cfg: MapConfiguration): GameMap = {
    val nodes = createAllNodes(cfg)
    val edges = createEdges(convertToMap(nodes))
    new GameMap(Graph(nodes, edges))
  }
}