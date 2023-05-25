package graphMath.functiongrapher.grapher.expression;

/**
 * Función binaria 'Raíz n-ésima', que devuelve la raíz de una cantidad, usando
 * otra como exponente.
 */
public class NthRoot extends Binary {

	public NthRoot(Quantity q, Quantity root) {
		super(q, root);
	}

	/**
	 * Método para obtener el valor de la función raíz n-ésima.
	 * 
	 * @return Devuelve la raíz del primer parámetro Quantity pasado en el
	 *         constructor con respecto del segundo.
	 */
	@Override
	public double getValue() {
		double val1 = realValue(q1);
		double val2 = realValue(q2);
		return Math.pow(val1, 1.0 / val2);
	}

}
