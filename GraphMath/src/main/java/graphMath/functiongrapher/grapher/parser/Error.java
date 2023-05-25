package graphMath.functiongrapher.grapher.parser;

/**
 * Objeto para manejar los errores del parseador de expresiones
 */
public class Error {

	/**
	 * Constructor por defecto de la clase Error.
	 */
	public Error() {
	}

	/**
	 * Imprime un mensaje de error.
	 * 
	 * @param msg El mensaje de error a imprimir.
	 */
	public void makeError(String msg) {
		System.out.println(msg);
	}
}