package graphMath.functiongrapher.grapher.expression;

/**
 * Operación binaria 'Cociente', que devuelve la división entre dos cantidades.
 */
public class Quotient extends Binary {

	public Quotient(Quantity q1, Quantity q2) {
		super(q1, q2);
	}

	/**
	 * Método para obtener el valor de la operación división.
	 * 
	 * @return Devuelve el cociente entre los parámetros Quantity pasados en el
	 *         constructor.
	 */
	@Override
	public double getValue() {
		double val1 = realValue(q1);
		double val2 = realValue(q2);
		return val1 / val2;
	}
}
