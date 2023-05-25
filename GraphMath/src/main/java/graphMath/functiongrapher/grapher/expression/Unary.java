package graphMath.functiongrapher.grapher.expression;

/**
 * Cantidad unitaria.
 */
public abstract class Unary extends Quantity {

	protected Quantity q;
	
	public Unary(Quantity q) {
		this.q = q;
	}
	
	/**
	 * Obtiene el valor numérico de la cantidad unitaria.
	 * 
	 * @return El valor numérico de la cantidad unitaria.
	 */
	@Override
	public abstract double getValue();
}
