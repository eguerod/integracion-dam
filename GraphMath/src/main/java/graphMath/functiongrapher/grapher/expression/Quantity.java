package graphMath.functiongrapher.grapher.expression;

/**
 * Clase abstracta que encapsula una cantidad.
 */
public abstract class Quantity {

	/**
	 * Devuelve el valor de la cantidad.
	 * 
	 * @return el valor de la cantidad.
	 */
	public abstract double getValue();

	/**
	 * Devuelve el valor real de una cantidad, o NaN si la cantidad es nula.
	 * 
	 * @param q la cantidad a obtener su valor real.
	 * @return el valor real de la cantidad o NaN si la cantidad es nula.
	 */
	public static double realValue(Quantity q) {
		return q != null ? q.getValue() : Double.NaN;
	}
}
