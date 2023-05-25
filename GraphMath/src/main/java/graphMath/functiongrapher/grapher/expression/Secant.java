package graphMath.functiongrapher.grapher.expression;

/**
 * Función unitaria 'Secante'.
 * */
public class Secant extends Unary {
	
	public Secant(Quantity q) {
		super(q);
	}

	/**
	 * Método para obtener el valor de la función secante.
	 * 
	 * @return Devuelve la secante del parámetro Quantity pasado en el constructor.
	 */
	@Override
	public double getValue() {
		double val = realValue(q);
		return 1.0 / Math.cos(val);
	}
}
