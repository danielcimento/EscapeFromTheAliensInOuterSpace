package main

import model.map.MapConfiguration

/**
  * Created by daniel on 3/11/2017.
  */
object MapLoader {
  def main(args: Array[String]) {
    MapConfiguration.createMapConfigFromFile(classOf[MapConfiguration].getResourceAsStream("resources/galilei.txt"), "galilei.ser")
    MapConfiguration.createMapConfigFromFile(classOf[MapConfiguration].getResourceAsStream("resources/fermi.txt"), "fermi.ser")
  }
}