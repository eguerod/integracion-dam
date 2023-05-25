package graphMath.functiongrapher.grapher.expression;

/**
 * Función unitaria 'Cotangente'.
 */
public class Cotangent extends Unary {

	public Cotangent(Quantity q) {
		super(q);
	}

	/**
	 * Método para obtener el valor de la función cotangente.
	 * 
	 * @return Devuelve la cotangente del parámetro Quantity pasado en el
	 *         constructor.
	 */
	@Override
	public double getValue() {
		double val = realValue(q);
		return 1.0 / Math.tan(val);
	}
}
