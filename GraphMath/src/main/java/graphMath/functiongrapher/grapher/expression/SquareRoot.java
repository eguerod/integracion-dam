package graphMath.functiongrapher.grapher.expression;

/**
 * Función unitaria 'Raíz cuadrada'.
 */
public class SquareRoot extends Unary {

	public SquareRoot(Quantity q) {
		super(q);
	}

	/**
	 * Método para obtener el valor de la función raíz cuadrada.
	 * 
	 * @return Devuelve la raíz cuadrada del parámetro Quantity pasado en el
	 *         constructor.
	 */
	@Override
	public double getValue() {
		double val = realValue(q);
		return Math.sqrt(val);
	}

}
