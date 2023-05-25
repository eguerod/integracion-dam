package graphMath.functiongrapher.grapher.expression;

/**
 * Función binaria 'Logaritmo', que devuelve el logaritmo de una cantidad en
 * base a otra.
 */
public class Log extends Binary {

	public Log(Quantity x, Quantity base) {
		super(x, base);
	}

	/**
	 * Método para obtener el valor de la función logaritmo.
	 * 
	 * @return Devuelve el logaritmo del primer parámetro Quantity pasado en el
	 *         constructor, con el segundo como base.
	 */
	@Override
	public double getValue() {
		double val1 = realValue(q1);
		double val2 = realValue(q2);
		return Math.log(val1) / Math.log(val2);
	}
}
