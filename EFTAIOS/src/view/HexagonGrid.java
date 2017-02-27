package view;

import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.text.Text;
import map.MapNode;
import map.NodeType;
import model.GameClientController;


/*for mathematics' sake, if we imagine a square grid of hexagons with size n,
* then the center of the first hexagon is at (n, sqrt(3)*n/2)
*/


public class HexagonGrid extends Group implements MovementListener {
	private static char[] nodePrefixes = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W'};
    private static String[] nodeSuffixes = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14"};
	
	/**
	 * @param lengthCount = the number of hexagons that make up the horizontal edge
	 * @param heightCount = the number of hexagons that make up the vertical edge
	 * @param topLeftX = the x coordinate of the bottom left coordinate of the grid
	 * @param topLeftY = the y coordinate of the bottom left coordinate of the grid
	 * @param size = the desired size of each hexagon
	 * */
	public HexagonGrid(int lengthCount, int heightCount,
			double topLeftX, double topLeftY, double size, GameClientController client){
		super();
		double centerXOfFirstHex = size;
		double centerYOfFirstHex = Math.sqrt(3)*size/2;
		
		for(int i = 0; i < lengthCount; i++){
			for(int j = 0; j < heightCount; j++){
				//the if we are on the nth horizontal hexagon, and
				//n is even (starting @ 0), then we shift down an extra
				//sqrt(3)n/2
				//standardly, the (i, j)th horizontal hexagon appears at 
				//{(start + (n + sqrt(3)n/2)*i), start + (sqrt(3)n)*j}
				String id = "" + nodePrefixes[i] + nodeSuffixes[j]; 
				
				double currXCoordinate = centerXOfFirstHex + ((size + (size / 2))*i);
				double currYCoordinate = centerYOfFirstHex + ((Math.sqrt(3) * size)*j);
				currYCoordinate += (i % 2 == 0) ? 0.0 : (Math.sqrt(3) * size / 2);

				NodeType thisNodeType = MapNode.get(id).getType();
				
				
				//Don't render blocked map nodes
				Text label = new Text(currXCoordinate-(size/3), currYCoordinate+(size/5), id);
				label.setMouseTransparent(true);
				HexagonShape hex = new HexagonShape(currXCoordinate, currYCoordinate, size, id, client);
				hex.setHexType(thisNodeType);
				if(thisNodeType.equals(NodeType.BLOCKED)){
					hex.setVisible(false);
					label.setVisible(false);
				}else if(thisNodeType == NodeType.ALIEN 
						|| thisNodeType == NodeType.ESCAPE_HATCH
						|| thisNodeType == NodeType.HUMAN){
					label.setVisible(false);
				}
				getChildren().addAll(hex, label);
			}
		}
	}
	
	//The location of the local player should be bordered with orange.
	public void updateLocalPlayerLocation(MapNode m){
		for(Node n : this.getChildren()){
			if(n instanceof HexagonShape){
				HexagonShape hex = (HexagonShape)n;
				if(hex.getStroke().equals(Color.ORANGE)){
					hex.setStroke(Color.BLACK);
				}
				if(m.getName().equals(hex.coordinate)){
					hex.setStroke(Color.ORANGE);
				}
			}
		}
	}
}
