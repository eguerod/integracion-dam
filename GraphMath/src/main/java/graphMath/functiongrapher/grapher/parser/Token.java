package graphMath.functiongrapher.grapher.parser;

/**
 * Token utilizado por el analizador léxico del graficador de funciones.
 */
public class Token {
	public final TokenType type;
	public final String data;

	/**
	 * Constructor para Token con datos.
	 * 
	 * @param type Tipo de token.
	 * @param data Datos asociados al token.
	 */
	public Token(TokenType type, String data) {
		this.type = type;
		this.data = data;
	}

	/**
	 * Constructor para Token sin datos.
	 * 
	 * @param type Tipo de token.
	 */
	public Token(TokenType type) {
		this(type, "");
	}

	/**
	 * Devuelve una representación en cadena del objeto Token.
	 * 
	 * @return Representación en cadena del tipo de token.
	 */
	@Override
	public String toString() {
		return type.toString();
	}
}