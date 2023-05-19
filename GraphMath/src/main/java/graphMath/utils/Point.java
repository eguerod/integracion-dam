package graphMath.utils;

public class Point {
	private Double x;
	private Double y;
	
	public Point(Double x, Double y) {
		this.x=x;
		this.y=y;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
