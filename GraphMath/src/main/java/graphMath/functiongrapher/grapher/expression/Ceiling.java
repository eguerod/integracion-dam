package graphMath.functiongrapher.grapher.expression;

/**
 * Función unitaria 'Techo' (Redondeo a la alta).
 */
public class Ceiling extends Unary {

	public Ceiling(Quantity q) {
		super(q);
	}

	/**
	 * Método para obtener el valor de la función techo.
	 * 
	 * @return Devuelve el valor del parámetro Quantity pasado en el constructor
	 *         redondeado a la alta.
	 */
	@Override
	public double getValue() {
		double val = realValue(q);
		return Math.ceil(val);
	}
}
