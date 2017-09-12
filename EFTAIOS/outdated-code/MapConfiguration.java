//package model.map;
//
//import java.io.*;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.Scanner;
//
//public class MapConfiguration implements Serializable{
//	private static char[] nodePrefixes = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W'};
//	private static String[] nodeSuffixes = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14"};
//	private HashMap<String, NodeType> config = new HashMap<>();
//
//	public NodeType getNodeType(String s){
//		return config.get(s);
//	}
//
//
//	public static void createMapConfigFromFile(InputStream in, String out){
//		MapConfiguration newConfig = new MapConfiguration();
//		Scanner reader;
//		int rowPointer = 0;
//		try{
//            reader = new Scanner(in);
//
//            while(reader.hasNextLine()){
//                String currLine = reader.nextLine();
//                for(int i = 0; i < currLine.length(); i++){
//                    String nodeName = nodePrefixes[i] + nodeSuffixes[rowPointer];
//                    newConfig.config.put(nodeName, charCorrespondence.get(currLine.charAt(i)));
//                    System.out.println(nodeName + " is " + newConfig.config.get(nodeName));
//                }
//                rowPointer++;
//            }
//
//            ObjectOutputStream outStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("EFTAIOS/src/main/resources/" + out))));
//            outStream.writeObject(newConfig);
//            outStream.close();
//		}catch(IOException e){
//			e.printStackTrace();
//			System.exit(1);
//		}
//	}
//	public static MapConfiguration loadFromFile(InputStream s){
//		try{
//			ObjectInputStream inStream = new ObjectInputStream(s);
//			MapConfiguration config = (MapConfiguration)inStream.readObject();
//			return config;
//		}catch(IOException e){
//			e.printStackTrace();
//			System.exit(1);
//		}catch(ClassNotFoundException e){
//			e.printStackTrace();
//			System.exit(1);
//		}
//		//THIS SHOULD NEVER HAPPEN
//		return null;
//	}
//}
