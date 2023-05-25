package graphMath.functiongrapher.grapher.expression;

/**
 * Cantidad binaria, con dos subcantidades q1 y q2.
 */
public abstract class Binary extends Quantity {

	Quantity q1;
	Quantity q2;

	public Binary(Quantity q1, Quantity q2) {
		this.q1 = q1;
		this.q2 = q2;
	}

	/**
	 * Obtiene el valor numérico de la cantidad binaria.
	 * 
	 * @return El valor numérico de la cantidad binaria.
	 */
	@Override
	public abstract double getValue();
}
