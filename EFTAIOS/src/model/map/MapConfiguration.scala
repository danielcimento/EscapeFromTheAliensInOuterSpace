package model.map

import java.io._
import java.nio.file.Paths
import java.util.Scanner

import scala.collection.immutable.HashMap

object MapConfiguration {
  // Turns a text based configuration file into a MapConfiguration object as well as serializing
  def createMapConfigFromFile(in: InputStream, outfileName: String): MapConfiguration = {
    val reader = new Scanner(in)
    val rows: IndexedSeq[List[(String, NodeType)]] = for (suffix <- GameMap.nodeSuffixes) yield {
      val line: String = reader.nextLine()
      val lineAsChars: List[Char] = line.toList
      val typeIdentifierAndPrefix: List[(Char, Char)] = lineAsChars.zip(GameMap.nodePrefixes)
      val rowAsMappedValues: List[(String, NodeType)] = typeIdentifierAndPrefix map {
        case (typeIdentifier, prefix) => (prefix + suffix, GameMap.characterToNodeType(typeIdentifier))
      }
      rowAsMappedValues
    }
    val mappedElements: List[(String, NodeType)] = rows.flatten.toList
    val configuration = new MapConfiguration(HashMap(mappedElements: _*))
    val outStream = new ObjectOutputStream(new FileOutputStream(outfileName))
    outStream.writeObject(configuration)
    outStream.close()
    configuration
  }


  def readConfigurationFromFile(inputStream: InputStream): MapConfiguration = {
    val input = new ObjectInputStream(inputStream)
    val obj = input.readObject()
    obj match {
      case cfg: MapConfiguration => cfg
      case _ => sys.error("Type mismatch when reading from file")
    }
  }
}

class MapConfiguration(tileConfig: HashMap[String, NodeType]) extends Serializable {
  def apply(s: String): NodeType = tileConfig(s)
}
