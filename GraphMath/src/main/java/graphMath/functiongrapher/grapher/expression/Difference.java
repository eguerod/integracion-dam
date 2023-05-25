package graphMath.functiongrapher.grapher.expression;

/**
 * Operación binaria 'Diferencia', que devuelve la resta entre dos cantidades.
 */
public class Difference extends Binary {
	
	public Difference(Quantity q1, Quantity q2) {
		super(q1, q2);
	}

	/**
	 * Método para obtener el valor de la operación resta.
	 * 
	 * @return Devuelve la diferencia entre los parámetros Quantity pasados en el
	 *         constructor.
	 */
	@Override
	public double getValue() {
		double val1 = realValue(q1);
		double val2 = realValue(q2);
		return val1 - val2;
	}
}
