package graphMath.functiongrapher.grapher.expression;

/**
 * Función unitaria 'Valor Absoluto'.
 * */
public class AbsoluteValue extends Unary {

	public AbsoluteValue(Quantity q) {
		super(q);
	}

	/**
	 * Método para obtener el valor de la función valor absoluto.
	 * 
	 * @return Devuelve el valor absoluto del parámetro Quantity pasado en el
	 *         constructor.
	 */
	@Override
	public double getValue() {
		double val = realValue(q);
		return Math.abs(val);
	}
}
