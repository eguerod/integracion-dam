/**
*Clase que representa una cadena de Tokens. 
*/
package graphMath.functiongrapher.grapher.parser;

import java.util.ArrayList;
import java.util.List;

public class TokenString {
	private List<Token> tokens;

	/**
	 * Constructor predeterminado de la clase TokenString.
	 */
	public TokenString() {
		tokens = new ArrayList<>();
	}

	/**
	 * Constructor sobrecargado de la clase TokenString. Inicializa una nueva
	 * instancia de TokenString con una lista de Tokens dada.
	 * 
	 * @param tokens Lista de Tokens para inicializar el objeto TokenString.
	 */
	private TokenString(List<Token> tokens) {
		this.tokens = tokens;
	}

	/**
	 * Método para agregar un nuevo Token a la cadena de Tokens.
	 * 
	 * @param token Token que se agregará a la cadena de Tokens.
	 */
	public void addToken(Token token) {
		tokens.add(token);
	}

	/**
	 * Devuelve el Token en la posición específicada de la cadena de Tokens.
	 * 
	 * @param i Índice del Token que se quiere obtener.
	 * @return Token en la posición especificada.
	 */
	public Token tokenAt(int i) {
		return tokens.get(i);
	}

	/**
	 * Devuelve la longitud de la cadena de Tokens.
	 * 
	 * @return Longitud de la cadena de Tokens.
	 */
	public int getLength() {
		return tokens.size();
	}

	/**
	 * Método para dividir la cadena de Tokens en un nuevo objeto TokenString.
	 * 
	 * @param start Índice inicial de la división de la cadena de Tokens.
	 * @param stop  Índice final de la división de la cadena de Tokens.
	 * @return Nuevo objeto TokenString con la sección de Tokens especificada.
	 */
	public TokenString split(int start, int stop) {
		start = Math.max(0, start);
		stop = Math.min(tokens.size(), stop);

		List<Token> subList = new ArrayList<>();
		for (int i = start; i < stop; i++) {
			subList.add(tokens.get(i));
		}
		return new TokenString(subList);
	}

	/**
	 * Método para insertar un Token en una posición específica de la cadena de
	 * Tokens.
	 * 
	 * @param i     Índice en el que se insertará el Token.
	 * @param token Token que se insertará en la cadena de Tokens.
	 */
	public void insert(int i, Token token) {
		tokens.add(i, token);
	}

	/**
	 * Método para remover un Token de la cadena de Tokens.
	 * 
	 * @param i Índice del Token que se quiere remover.
	 * @return Token removido de la cadena de Tokens.
	 */
	public Token remove(int i) {
		return tokens.remove(i);
	}

	/**
	 * Sobrescritura del método toString() para imprimir la cadena de Tokens como
	 * una cadena de caracteres.
	 * 
	 * @return Cadena de caracteres que representa la cadena de Tokens.
	 */
	@Override
	public String toString() {
		String line = "";
		for (Token t : tokens) {
			line += t.toString();
			if (t.data.length() > 0)
				line += "<" + t.data + ">";
			line += " ";
		}
		return line;
	}
}