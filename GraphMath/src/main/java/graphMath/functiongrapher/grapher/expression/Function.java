package graphMath.functiongrapher.grapher.expression;

/**
 * Función matemática con hasta tres variables.
 */
public class Function {

	private Quantity root;
	private Variable x;
	private Variable y;
	private Variable z;

	/**
	 * Construye un objeto de tipo Function a partir de los parámetros dados.
	 * 
	 * @param root La raíz de la función.
	 * @param x    La variable 'x' de la función.
	 * @param y    La variable 'y' de la función.
	 * @param z    La variable 'z' de la función.
	 */
	public Function(Quantity root, Variable x, Variable y, Variable z) {
		this.root = root;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Evalúa la función en los valores de las variables especificados y devuelve el
	 * resultado.
	 * 
	 * @param x El valor de la variable 'x'.
	 * @param y El valor de la variable 'y'.
	 * @param z El valor de la variable 'z'.
	 * @return El resultado de evaluar la función en los valores de las variables
	 *         especificados.
	 */
	public double evaluateAt(double x, double y, double z) {
		this.x.set(x);
		this.y.set(y);
		this.z.set(z);
		return root.getValue();
	}
}
