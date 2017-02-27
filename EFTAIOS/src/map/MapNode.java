package map;
import java.util.LinkedList;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.io.IOException;

public class MapNode{
    private static char[] nodePrefixes = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W'};
    private static String[] nodeSuffixes = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14"};
    
    private static HashMap<String, MapNode> ALL_NODES = new HashMap();
    private static HashMap<Character, NodeType> charCorrespondence = new HashMap();
    
    private NodeType aType;
    private LinkedList<MapNode> adjacentNodes;
    private String aName;

    static{
        charCorrespondence.put(new Character('b'), NodeType.BLOCKED);
        charCorrespondence.put(new Character('s'), NodeType.SECURE);
        charCorrespondence.put(new Character('d'), NodeType.DANGEROUS);
        charCorrespondence.put(new Character('a'), NodeType.ALIEN);
        charCorrespondence.put(new Character ('h'), NodeType.HUMAN);
        charCorrespondence.put(new Character('e'), NodeType.ESCAPE_HATCH);
    }
    
    private MapNode(NodeType pType, String pName){
        adjacentNodes = new LinkedList();
        aType = pType;
        aName = pName;
    }


    public static boolean hasNode(String input){
            return ALL_NODES.containsKey(input);
    }

    public static MapNode get(String input){
        return ALL_NODES.get(input);
    }


    public static void initNodes(MapConfiguration cfg){
    	for(char pre : nodePrefixes){
    		for(String suf : nodeSuffixes){
    			String id = pre + suf;
    			NodeType type = cfg.getNodeType(id);
    			MapNode node = new MapNode(type, id);
    			ALL_NODES.put(id, node);
    		}
    	}
        connectAdjacencies();
    }

    public String getName(){
        return aName;
    }

    public MapNode[] getAdjacencies(){
        return adjacentNodes.toArray(new MapNode[0]);
    }

    public String listAdjacencies(){
        String retString = "";
        for(MapNode m : this.adjacentNodes){
            if(retString.length() > 0){
                retString += " ";
            }
            retString += m.getName();
        }
        return retString;
    }

    public NodeType getType(){
        return aType;
    }

    public void addNeighbor(MapNode neighbor){
        this.adjacentNodes.add(neighbor);
    }

    public static void connectAdjacencies(){
        for(int i = 0; i < nodePrefixes.length; i++){
            for(int j = 0; j < nodeSuffixes.length; j++){
                MapNode currNode = ALL_NODES.get(nodePrefixes[i] + nodeSuffixes[j]);
                if((i+1) % 2 == 0){
                    //even nodes connect to their right/left neigbors with equal row, and 1 row lower
                    if(i > 0){
                        currNode.addNeighbor(ALL_NODES.get(nodePrefixes[i-1] + nodeSuffixes[j]));
                        if(j < nodeSuffixes.length - 1){
                            currNode.addNeighbor(ALL_NODES.get(nodePrefixes[i-1] + nodeSuffixes[j+1]));
                        }
                    }
                    if(i < nodePrefixes.length - 1){
                        currNode.addNeighbor(ALL_NODES.get(nodePrefixes[i+1] + nodeSuffixes[j]));
                            if(j < nodeSuffixes.length - 1){
                                currNode.addNeighbor(ALL_NODES.get(nodePrefixes[i+1] + nodeSuffixes[j+1]));
                            }
                    }
    

                }else{
                    //odd nodes connect to their right/left neighbors with equal row and 1 row higher                
                    if(i > 0){
                        currNode.addNeighbor(ALL_NODES.get(nodePrefixes[i-1] + nodeSuffixes[j]));
                        if(j > 0){
                            currNode.addNeighbor(ALL_NODES.get(nodePrefixes[i-1] + nodeSuffixes[j-1]));
                        }
                    }
                    if(i < nodePrefixes.length - 1){
                         currNode.addNeighbor(ALL_NODES.get(nodePrefixes[i+1] + nodeSuffixes[j]));
                            if(j > 0){
                                currNode.addNeighbor(ALL_NODES.get(nodePrefixes[i+1] + nodeSuffixes[j-1]));
                            }
                    }
                }
                //all nodes except for top nodes connect to the node above them
                if(j > 0){
                    currNode.addNeighbor(ALL_NODES.get(nodePrefixes[i] + nodeSuffixes[j-1]));
                }
                //all nodes except the bottom nodes connect to the node above them
                if(j < nodeSuffixes.length - 1){
                    currNode.addNeighbor(ALL_NODES.get(nodePrefixes[i] + nodeSuffixes[j+1])); 
                }
            }  
        }
    }
}
