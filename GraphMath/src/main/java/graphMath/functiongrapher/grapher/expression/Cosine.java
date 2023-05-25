package graphMath.functiongrapher.grapher.expression;

/**
 * Función unitaria 'Coseno'.
 */
public class Cosine extends Unary {

	public Cosine(Quantity q) {
		super(q);
	}

	/**
	 * Método para obtener el valor de la función coseno.
	 * 
	 * @return Devuelve el coseno del parámetro Quantity pasado en el constructor.
	 */
	@Override
	public double getValue() {
		double val = realValue(q);
		return Math.cos(val);
	}
}
