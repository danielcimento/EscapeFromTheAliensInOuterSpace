package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import map.NodeType;
import model.GameClient;


/* Mathematics of Hexagons (for personal reference). When creating a hexagon centered at x, y with a distance of
 * n between each of its corners:
 * recall that a 30-60-90 triangle with a hypotenuse of length n, the length of the side opposite 30 degrees
 * is n/2. The length of the side opposite to 60 degrees is sqrt(3)*n/2
 * */

/**
 * @author Daniel Cimento & Tristan Shoemaker
 * Huge thanks to Tristan for helping me debug my geometry calculations!
 * */
public class HexagonShape extends Polygon{
	String coordinate;
	
	public HexagonShape(double xCoordinate, double yCoordinate, double size, String coordinateName){
		super(calculateCoordinates(xCoordinate, yCoordinate, size));
		setFill(Color.WHITE);
		setStroke(Color.BLACK);
		coordinate = coordinateName;
		setOnMouseClicked(e -> System.out.println(coordinate));
	}
	
	public void setHexType(NodeType t){
		switch (t) {
		case ALIEN:
			setFill(Color.RED);
			break;
		case HUMAN:
			setFill(Color.PINK);
			break;
		case DANGEROUS:
			setFill(Color.GRAY);
			break;
		case SECURE:
			setFill(Color.WHITE);
			break;
		case ESCAPE_HATCH:
			setFill(Color.GREEN);
			break;
		}
	}
	
	/**
	 * With a hypotenuse of length n, this function returns the length of the longer side in a 30-60-90 triangle
	 * */
	private static double calculateLongSide(double n){
		return ((Math.sqrt(3)*(n))/2);
	}
	
	private static double calculateShortSide(double n){
		return n/2;
	}
	
	private static double[] calculateCoordinates(double xCoordinate, double yCoordinate, double n){
		//hexagon has 6 coordinates, therefore 12 points are necessary
		double[] coordinates = new double[12];
		//first point is n to the right of the center
		coordinates[0] = xCoordinate + n;
		coordinates[1] = yCoordinate;
		//second point is n/2 to the right and sqrt(3)n/2 up
		coordinates[2] = xCoordinate + calculateShortSide(n);
		coordinates[3] = yCoordinate + calculateLongSide(n);
		//third point is " left and " up
		coordinates[4] = xCoordinate - calculateShortSide(n);
		coordinates[5] = yCoordinate + calculateLongSide(n);
		//fourth point is n to the left
		coordinates[6] = xCoordinate - n;
		coordinates[7] = yCoordinate;
		
		coordinates[8] = xCoordinate - calculateShortSide(n);
		coordinates[9] = yCoordinate - calculateLongSide(n);
		
		coordinates[10] = xCoordinate + calculateShortSide(n);
		coordinates[11] = yCoordinate - calculateLongSide(n);
		
		return coordinates;
	}
}
