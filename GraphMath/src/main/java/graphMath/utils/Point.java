package graphMath.utils;

/**
 * Punto con dos coordenadas, 'x' e 'y'
 */
public class Point {
	private Double x;
	private Double y;

	/**
	 * Constructor de la clase Point.
	 * 
	 * @param x La coordenada x del punto.
	 * @param y La coordenada y del punto.
	 */
	public Point(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Devuelve una cadena que representa las coordenadas x e y del punto.
	 * 
	 * @return Una cadena que representa las coordenadas x e y del punto.
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
