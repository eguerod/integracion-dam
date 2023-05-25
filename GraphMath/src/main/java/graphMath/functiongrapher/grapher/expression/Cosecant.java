package graphMath.functiongrapher.grapher.expression;

/**
 * Función unitaria 'Cosecante'.
 * */
public class Cosecant extends Unary {
	
	public Cosecant(Quantity q) {
		super(q);
	}

	/**
	 * Método para obtener el valor de la función cosecante.
	 * 
	 * @return Devuelve la cosecante del parámetro Quantity pasado en el constructor.
	 */
	@Override
	public double getValue() {
		double val = realValue(q);
		return 1.0 / Math.sin(val);
	}
}
