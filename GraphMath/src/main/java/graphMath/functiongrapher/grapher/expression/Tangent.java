package graphMath.functiongrapher.grapher.expression;

/**
 * Función unitaria 'Tangente'.
 */
public class Tangent extends Unary {
	
	public Tangent(Quantity q) {
		super(q);
	}

	/**
	 * Método para obtener el valor de la función tangente.
	 * 
	 * @return Devuelve la tangente del parámetro Quantity pasado en el
	 *         constructor.
	 */
	@Override
	public double getValue() {
		double val = realValue(q);
		return Math.tan(val);
	}
}
