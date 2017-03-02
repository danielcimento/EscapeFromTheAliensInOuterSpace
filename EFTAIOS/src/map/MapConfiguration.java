package map;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;

public class MapConfiguration implements Serializable{
	private static char[] nodePrefixes = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W'};
	private static String[] nodeSuffixes = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14"};  
	private HashMap<String, NodeType> config = new HashMap<>();
	
	private static HashMap<Character, NodeType> charCorrespondence = new HashMap();
	
	static{
        charCorrespondence.put(new Character('b'), NodeType.BLOCKED);
        charCorrespondence.put(new Character('s'), NodeType.SECURE);
        charCorrespondence.put(new Character('d'), NodeType.DANGEROUS);
        charCorrespondence.put(new Character('a'), NodeType.ALIEN);
        charCorrespondence.put(new Character ('h'), NodeType.HUMAN);
        charCorrespondence.put(new Character('e'), NodeType.ESCAPE_HATCH);
    }
	
	
	public static void main(String[] args){
		createMapConfigFromFile("src/resources/fermi.txt", "src/resources/fermi.ser");
	}
	
	public NodeType getNodeType(String s){
		return config.get(s);
	}
	
	
	public static void createMapConfigFromFile(String inputFile, String outputFile){
		MapConfiguration newConfig = new MapConfiguration();
		Scanner reader;
		int rowPointer = 0;
		try{
            reader = new Scanner(new FileInputStream(inputFile));
            
            while(reader.hasNextLine()){
                String currLine = reader.nextLine();
                for(int i = 0; i < currLine.length(); i++){
                    String nodeName = nodePrefixes[i] + nodeSuffixes[rowPointer];
                    newConfig.config.put(nodeName, charCorrespondence.get(currLine.charAt(i)));
                    System.out.println(nodeName + " is " + newConfig.config.get(nodeName));
                }
                rowPointer++;
            }
            
            ObjectOutputStream outStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
            outStream.writeObject(newConfig);
            outStream.close();
		}catch(IOException e){
			System.out.println("IO Error!");
		}
	}
	public static MapConfiguration loadFromFile(String s){
		try{
			ObjectInputStream inStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(s)));
			MapConfiguration config = (MapConfiguration)inStream.readObject();
			return config;
		}catch(IOException e){
			System.err.println("IO Error!");
		}catch(ClassNotFoundException e){
			System.err.println("Could not convert to MapConfiguration");
		}
		//THIS SHOULD NEVER HAPPEN
		return null;
	}
}
