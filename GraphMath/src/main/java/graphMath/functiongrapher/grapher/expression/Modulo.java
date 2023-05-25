package graphMath.functiongrapher.grapher.expression;

/**
 * Función binaria 'Módulo', que devuelve el resto de la división entre dos cantidades.
 */
public class Modulo extends Binary {
	
	public Modulo(Quantity q1, Quantity q2) {
		super(q1, q2);
	}

	/**
	 * Método para obtener el valor de la función módulo.
	 * 
	 * @return Devuelve el módulo entre los parámetros Quantity pasados en el
	 *         constructor.
	 */
	@Override
	public double getValue() {
		double val1 = realValue(q1);
		double val2 = realValue(q2);
		return val1 % val2;
	}
}
