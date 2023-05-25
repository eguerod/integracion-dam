package graphMath.functiongrapher.grapher.expression;

/**
 * Función unitaria 'Seno'.
 */
public class Sine extends Unary {
	
	public Sine(Quantity q) {
		super(q);
	}

	/**
	 * Método para obtener el valor de la función seno.
	 * 
	 * @return Devuelve el seno del parámetro Quantity pasado en el constructor.
	 */
	@Override
	public double getValue() {
		double val = realValue(q);
		return Math.sin(val);
	}
}
