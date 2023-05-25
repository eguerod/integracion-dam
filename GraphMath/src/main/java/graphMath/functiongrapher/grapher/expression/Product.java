package graphMath.functiongrapher.grapher.expression;

/**
 * Operación binaria 'Producto', que devuelve la multiplicación entre dos cantidades.
 */
public class Product extends Binary {
	
	public Product(Quantity q1, Quantity q2) {
		super(q1, q2);
	}

	/**
	 * Método para obtener el valor de la operación multiplicación.
	 * 
	 * @return Devuelve el producto entre los parámetros Quantity pasados en el
	 *         constructor.
	 */
	@Override
	public double getValue() {
		double val1 = realValue(q1);
		double val2 = realValue(q2);
		return val1 * val2;
	}
}
