package graphMath.functiongrapher.grapher.expression;

/**
 * Cantidad numérica fija.
 */
public class Number extends Quantity {

	protected double num;

	public Number(double num) {
		this.num = num;
	}

	/**
	 * Obtiene el valor numérico de esta instancia.
	 * 
	 * @return el valor numérico asociado a esta instancia de Number.
	 */
	@Override
	public double getValue() {
		return num;
	}
}
