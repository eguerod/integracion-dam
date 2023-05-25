package graphMath.functiongrapher.grapher.expression;

/**
 * Función unitaria 'Suelo' (Redondeo a la baja).
 */
public class Floor extends Unary {
	
	public Floor(Quantity q) {
		super(q);
	}

	/**
	 * Método para obtener el valor de la función suelo.
	 * 
	 * @return Devuelve el valor del parámetro Quantity pasado en el constructor
	 *         redondeado a la baja.
	 */
	@Override
	public double getValue() {
		double val = realValue(q);
		return Math.floor(val);
	}
}
