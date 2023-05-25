package graphMath.functiongrapher.grapher.expression;

/**
 * Función binaria 'Potencia', que devuelve la potencia de una cantidad elevada
 * a otra.
 */
public class Power extends Binary {

	public Power(Quantity x, Quantity exp) {
		super(x, exp);
	}

	/**
	 * Método para obtener el valor de la función potencia.
	 * 
	 * @return Devuelve la potencia primer parámetro Quantity pasado en el
	 *         constructor elevada al segundo.
	 */
	@Override
	public double getValue() {
		double val1 = realValue(q1);
		double val2 = realValue(q2);
		return Math.pow(val1, val2);
	}
}
