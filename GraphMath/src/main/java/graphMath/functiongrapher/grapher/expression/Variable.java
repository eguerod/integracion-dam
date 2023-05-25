package graphMath.functiongrapher.grapher.expression;

/**
 * Cantidad numérica variable.
 */
public class Variable extends Number {

	public Variable() {
		super(0.0);
	}

	/**
	 * Establece el valor numérico de la variable.
	 * 
	 * @param num el nuevo valor numérico a establecer.
	 */
	public void set(double num) {
		this.num = num;
	}
}
