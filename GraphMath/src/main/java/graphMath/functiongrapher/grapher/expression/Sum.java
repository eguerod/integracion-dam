package graphMath.functiongrapher.grapher.expression;

/**
 * Operación binaria 'Suma', que devuelve la suma entre dos cantidades.
 */
public class Sum extends Binary {

	public Sum(Quantity q1, Quantity q2) {
		super(q1, q2);
	}

	/**
	 * Método para obtener el valor de la operación suma.
	 * 
	 * @return Devuelve la suma entre los parámetros Quantity pasados en el
	 *         constructor.
	 */
	@Override
	public double getValue() {
		double val1 = realValue(q1);
		double val2 = realValue(q2);
		return val1 + val2;
	}
}
