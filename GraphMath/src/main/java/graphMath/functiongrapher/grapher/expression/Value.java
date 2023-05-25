package graphMath.functiongrapher.grapher.expression;

/**
 * Valor numérico.
 */
public class Value extends Quantity {

	protected double d;

	public Value(double d) {
		this.d = d;
	}

	/**
	 * Devuelve el valor numérico.
	 * 
	 * @return El valor numérico.
	 */
	@Override
	public double getValue() {
		return d;
	}
}
