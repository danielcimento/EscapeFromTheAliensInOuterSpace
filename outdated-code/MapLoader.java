package main;

import model.map.MapConfiguration;

/**
 * Created by daniel on 3/11/2017.
 */
public class MapLoader {
    public static void main(String[] args){
        MapConfiguration.createMapConfigFromFile(MapLoader.class.getResourceAsStream("resources/galilei.txt"), "galilei.ser");
        MapConfiguration.createMapConfigFromFile(MapLoader.class.getResourceAsStream("resources/fermi.txt"), "fermi.ser");
    }
}
