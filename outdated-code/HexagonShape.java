//package view;
//
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Polygon;
//import javafx.scene.shape.StrokeType;
//import model.map.MapNode;
//import model.map.NodeType;
//import model.GameClientController;
//
//
///* Mathematics of Hexagons (for personal reference). When creating a hexagon centered at x, y with a distance of
// * n between each of its corners:
// * recall that a 30-60-90 triangle with a hypotenuse of length n, the length of the side opposite 30 degrees
// * is n/2. The length of the side opposite to 60 degrees is sqrt(3)*n/2
// * */
//
///**
// * @author Daniel Cimento & Tristan Shoemaker
// * Huge thanks to Tristan for helping me debug my geometry calculations!
// * */
//public class HexagonShape extends Polygon{
//	private static final Color DARKESTGREY = Color.rgb(100, 100, 100);
//
//	String coordinate;
//
//	public HexagonShape(double xCoordinate, double yCoordinate, double size, String coordinateName, GameClientController client){
//		super(calculateCoordinates(xCoordinate, yCoordinate, size));
//
//		setFill(Color.WHITE);
//		setDefaultStroke();
//
//		coordinate = coordinateName;
//		setOnMouseClicked(e -> client.mapNodeSelected(MapNode.get(coordinate)));
//	}
//
//	public void setHexType(NodeType t){
//		switch (t) {
//		case ALIEN:
//			setFill(Color.RED);
//			break;
//		case HUMAN:
//			setFill(Color.PINK);
//			break;
//		case DANGEROUS:
//			setFill(Color.SILVER);
//			break;
//		case SECURE:
//			setFill(Color.WHITE);
//			break;
//		case ESCAPE_HATCH:
//			setFill(Color.GREEN);
//			break;
//		default:
//			setFill(Color.WHITE);
//			break;
//		}
//	}
//
//	public void setDefaultStroke(){
//		setStroke(DARKESTGREY);
//		setStrokeWidth(2.0);
//		setStrokeType(StrokeType.CENTERED);
//	}
//
//	public void setSelectedStroke(){
//		setStroke(Color.ORANGE);
//		setStrokeWidth(3.0);
//		setStrokeType(StrokeType.INSIDE);
//	}
//
//	public boolean isSelectedStroke(){
//		return getStroke().equals(Color.ORANGE) && getStrokeType().equals(StrokeType.INSIDE) && getStrokeWidth() == 3.0;
//	}
//
//	/**
//	 * With a hypotenuse of length n, this function returns the length of the longer side in a 30-60-90 triangle
//	 * */
//	private static double calculateLongSide(double n){
//		return ((Math.sqrt(3)*(n))/2);
//	}
//
//	private static double calculateShortSide(double n){
//		return n/2;
//	}
//
//	private static double[] calculateCoordinates(double xCoordinate, double yCoordinate, double n){
//		//hexagon has 6 coordinates, therefore 12 points are necessary
//		double[] coordinates = new double[12];
//		//first point is n to the right of the center
//		coordinates[0] = xCoordinate + n;
//		coordinates[1] = yCoordinate;
//		//second point is n/2 to the right and sqrt(3)n/2 up
//		coordinates[2] = xCoordinate + calculateShortSide(n);
//		coordinates[3] = yCoordinate + calculateLongSide(n);
//		//third point is " left and " up
//		coordinates[4] = xCoordinate - calculateShortSide(n);
//		coordinates[5] = yCoordinate + calculateLongSide(n);
//		//fourth point is n to the left
//		coordinates[6] = xCoordinate - n;
//		coordinates[7] = yCoordinate;
//
//		coordinates[8] = xCoordinate - calculateShortSide(n);
//		coordinates[9] = yCoordinate - calculateLongSide(n);
//
//		coordinates[10] = xCoordinate + calculateShortSide(n);
//		coordinates[11] = yCoordinate - calculateLongSide(n);
//
//		return coordinates;
//	}
//}
