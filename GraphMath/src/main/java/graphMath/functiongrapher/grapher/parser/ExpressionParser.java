package graphMath.functiongrapher.grapher.parser;

import java.util.ArrayList;
import java.util.List;

import graphMath.functiongrapher.grapher.expression.AbsoluteValue;
import graphMath.functiongrapher.grapher.expression.Ceiling;
import graphMath.functiongrapher.grapher.expression.Cosecant;
import graphMath.functiongrapher.grapher.expression.Cosine;
import graphMath.functiongrapher.grapher.expression.Cotangent;
import graphMath.functiongrapher.grapher.expression.Difference;
import graphMath.functiongrapher.grapher.expression.Floor;
import graphMath.functiongrapher.grapher.expression.Function;
import graphMath.functiongrapher.grapher.expression.Log;
import graphMath.functiongrapher.grapher.expression.Modulo;
import graphMath.functiongrapher.grapher.expression.NthRoot;
import graphMath.functiongrapher.grapher.expression.Power;
import graphMath.functiongrapher.grapher.expression.Product;
import graphMath.functiongrapher.grapher.expression.Quantity;
import graphMath.functiongrapher.grapher.expression.Quotient;
import graphMath.functiongrapher.grapher.expression.Secant;
import graphMath.functiongrapher.grapher.expression.Sine;
import graphMath.functiongrapher.grapher.expression.SquareRoot;
import graphMath.functiongrapher.grapher.expression.Sum;
import graphMath.functiongrapher.grapher.expression.Tangent;
import graphMath.functiongrapher.grapher.expression.Value;
import graphMath.functiongrapher.grapher.expression.Variable;

/**
 * @author USUARIO
 *
 */
public class ExpressionParser {

	private Error error;
	private Variable x;
	private Variable y;
	private Variable z;

	/**
	 * Constructor de la clase ExpressionParser. Crea una nueva instancia de Error y
	 * tres nuevas instancias de Variable (x, y, z).
	 */

	public ExpressionParser() {
		error = new Error();
		x = new Variable();
		y = new Variable();
		z = new Variable();
	}

	/**
	 * Analiza y parsea una expresión dada en forma de cadena de texto para crear
	 * una nueva instancia de Function.
	 * 
	 * @param expr la cadena de texto que contiene la expresión a ser analizada y
	 *             parseada
	 * @return una nueva instancia de Function si se pudo realizar el parsing, null
	 *         en caso contrario
	 */

	public Function parse(String expr) {
		TokenString tokens = tokenize(expr);
		if (tokens != null) {
			checkParentheses(tokens);
			substituteUnaryMinus(tokens);
			Quantity root = doOrderOfOperations(tokens);
			if (root == null) {
				error.makeError("Parsing of the function \"" + expr + "\" failed.");
				return null;
			}
			return new Function(root, x, y, z);
		}
		error.makeError("Parsing of the function \"" + expr + "\" failed.");
		return null;
	}

	/**
	 * Analiza la expresión dada en forma de cadena de texto para separarla en
	 * tokens y construir un objeto TokenString.
	 * 
	 * @param expr la cadena de texto que contiene la expresión a ser tokenizada
	 * @return un objeto TokenString con los tokens obtenidos si se pudo realizar la
	 *         tokenización, null en caso contrario
	 */

	private TokenString tokenize(String expr) {
		TokenString tkString = new TokenString();

		String name = "";
		String number = "";
		int numDecimals = 0;
		for (int i = 0; i < expr.length(); i++) {
			char currentChar = expr.charAt(i);
			boolean special = false;

			if (Character.isAlphabetic(currentChar)) {
				if (currentChar == 'x') {
					tkString.addToken(new Token(TokenType.X));
				} else if (currentChar == 'y') {
					tkString.addToken(new Token(TokenType.Y));
				} else if (currentChar == 'z') {
					tkString.addToken(new Token(TokenType.Z));
				} else {
					name += currentChar;
				}
				special = true;
			} else if (name.length() > 0) {
				TokenType type = getTokenTypeByName(name);
				if (type == null) {
					error.makeError("The function name " + name + " is not valid!");
					return null;
				}
				tkString.addToken(new Token(type));
				name = "";
			}

			if (Character.isDigit(currentChar) || currentChar == '.') {
				if (currentChar == '.') {
					if (numDecimals == 0)
						number += currentChar;
					numDecimals++;
				} else {
					number += currentChar;
				}
				special = true;
			} else if (number.length() > 0) {
				tkString.addToken(new Token(TokenType.NUMBER, number));
				number = "";
				numDecimals = 0;
			}

			if (!(special || currentChar == ' ')) {
				if (currentChar == '(')
					tkString.addToken(new Token(TokenType.OPEN_PARENTHESES));
				else if (currentChar == ')')
					tkString.addToken(new Token(TokenType.CLOSE_PARENTHESES));
				else if (currentChar == ',')
					tkString.addToken(new Token(TokenType.COMMA));
				else if (currentChar == '+')
					tkString.addToken(new Token(TokenType.PLUS));
				else if (currentChar == '-')
					tkString.addToken(new Token(TokenType.MINUS));
				else if (currentChar == '*')
					tkString.addToken(new Token(TokenType.TIMES));
				else if (currentChar == '/')
					tkString.addToken(new Token(TokenType.DIVIDED_BY));
				else if (currentChar == '^')
					tkString.addToken(new Token(TokenType.RAISED_TO));
				else if (currentChar == '%')
					tkString.addToken(new Token(TokenType.MODULO));
				else {
					error.makeError("The character '" + currentChar + "' is not allowed!");
					return null;
				}
			}
		}
		if (name.length() > 0) {
			TokenType type = getTokenTypeByName(name);
			if (type == null) {
				error.makeError("The function name '" + name + "' is not valid!");
				return null;
			}
			tkString.addToken(new Token(type));
			name = "";
		}
		if (number.length() > 0) {
			tkString.addToken(new Token(TokenType.NUMBER, number));
			number = "";
			numDecimals = 0;
		}

		return tkString;
	}

	/**
	 * Calcula el resultado de una operación matemática siguiendo las reglas de
	 * orden de operaciones, en sentido inverso. Las operaciones son, en orden:
	 * suma, resta, división, multiplicación, módulo, exponenciación, función,
	 * paréntesis, variables y números (Realizadas de derecha a izquierda).
	 * 
	 * @param tokens una cadena de tokens que representan la expresión matemática a
	 *               evaluar
	 * @return un objeto Quantity con el resultado de la operación
	 */
	private Quantity doOrderOfOperations(TokenString tokens) {
		int location = 0; // Localización del operador
		Quantity ret = new Value(0.0);

		location = scanFromRight(tokens, TokenType.PLUS);
		if (location != -1) {
			TokenString left = tokens.split(0, location);
			TokenString right = tokens.split(location + 1, tokens.getLength());
			ret = new Sum(doOrderOfOperations(left), doOrderOfOperations(right));
		} else {
			location = scanFromRight(tokens, TokenType.MINUS);
			if (location != -1) {
				TokenString left = tokens.split(0, location);
				TokenString right = tokens.split(location + 1, tokens.getLength());
				ret = new Difference(doOrderOfOperations(left), doOrderOfOperations(right));
			} else {
				location = scanFromRight(tokens, TokenType.DIVIDED_BY);
				if (location != -1) {
					TokenString left = tokens.split(0, location);
					TokenString right = tokens.split(location + 1, tokens.getLength());
					ret = new Quotient(doOrderOfOperations(left), doOrderOfOperations(right));
				} else {
					location = scanFromRight(tokens, TokenType.TIMES);
					if (location != -1) {
						TokenString left = tokens.split(0, location);
						TokenString right = tokens.split(location + 1, tokens.getLength());
						ret = new Product(doOrderOfOperations(left), doOrderOfOperations(right));
					} else {
						location = scanFromRight(tokens, TokenType.MODULO);
						if (location != -1) {
							TokenString left = tokens.split(0, location);
							TokenString right = tokens.split(location + 1, tokens.getLength());
							ret = new Modulo(doOrderOfOperations(left), doOrderOfOperations(right));
						} else {
							location = scanFromRight(tokens, TokenType.RAISED_TO);
							if (location != -1) {
								TokenString left = tokens.split(0, location);
								TokenString right = tokens.split(location + 1, tokens.getLength());
								ret = new Power(doOrderOfOperations(left), doOrderOfOperations(right));
							} else {
								location = scanFromRight(tokens, TokenType.FUNCTIONS);
								if (location != -1) {
									int endParams = getFunctionParamsEnd(tokens, location + 2);
									if (endParams != -1) {
										TokenString paramString = tokens.split(location + 2, endParams);
										ret = parseFunctionParams(paramString, tokens.tokenAt(location).type);
									}
								} else if (tokens.getLength() >= 2
										&& tokens.tokenAt(tokens.getLength() - 1).type == TokenType.CLOSE_PARENTHESES
										&& tokens.tokenAt(0).type == TokenType.OPEN_PARENTHESES) {
									TokenString inParentheses = tokens.split(1, tokens.getLength() - 1);
									ret = doOrderOfOperations(inParentheses);
								} else {
									location = scanFromRight(tokens, TokenType.X);
									if (location != -1) {
										ret = x;
									} else {
										location = scanFromRight(tokens, TokenType.Y);
										if (location != -1) {
											ret = y;
										} else {
											location = scanFromRight(tokens, TokenType.Z);
											if (location != -1) {
												ret = z;
											} else {
												location = scanFromRight(tokens, TokenType.NUMBER);
												if (location != -1) {
													ret = new Value(Double.parseDouble(tokens.tokenAt(location).data));
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return ret;
	}

	/**
	 * Calcula los parámetros de una función matemática según el tipo de token
	 * especificado, siguiendo las reglas de orden de operaciones en sentido
	 * inverso. Las operaciones son: suma, resta, división, multiplicación, módulo,
	 * exponenciación, función, paréntesis, variables y números. Todas se realizan
	 * de derecha a izquierda.
	 * 
	 * @param paramString una cadena de tokens que representan los parámetros de la
	 *                    función matemática a evaluar
	 * @param type        el tipo de token que representa la función matemática a
	 *                    evaluar
	 * @return un objeto Quantity con el resultado de la operación
	 */
	private Quantity parseFunctionParams(TokenString paramString, TokenType type) {
		List<TokenString> params = new ArrayList<>();
		int start = 0;
		for (int i = 0; i < paramString.getLength(); i++) {
			Token t = paramString.tokenAt(i);
			if (t.type == TokenType.COMMA) {
				params.add(paramString.split(start, i));
				start = i + 1;
			}
		}
		params.add(paramString.split(start, paramString.getLength()));

		if (params.size() == 0)
			return null;

		if (params.size() == 1) {
			Quantity param1 = doOrderOfOperations(params.get(0));
			switch (type) {
			case ABSOLUTE_VALUE:
				return new AbsoluteValue(param1);
			case CEILING:
				return new Ceiling(param1);
			case FLOOR:
				return new Floor(param1);
			case SINE:
				return new Sine(param1);
			case COSINE:
				return new Cosine(param1);
			case TANGENT:
				return new Tangent(param1);
			case COTANGENT:
				return new Cotangent(param1);
			case SECANT:
				return new Secant(param1);
			case COSECANT:
				return new Cosecant(param1);
			case SQUARE_ROOT:
				return new SquareRoot(param1);
			default:
				return null;
			}
		} else if (params.size() == 2) {
			Quantity param1 = doOrderOfOperations(params.get(0));
			Quantity param2 = doOrderOfOperations(params.get(1));
			switch (type) {
			case NTHROOT:
				return new NthRoot(param1, param2);
			case LOG:
				return new Log(param1, param2);
			default:
				return null;
			}
		}
		return null;
	}

	/**
	 * Método que devuelve la posición final de los parámetros de una función en una
	 * cadena de tokens.
	 * 
	 * @param tokens   Un objeto TokenString que representa la cadena de tokens de
	 *                 la función.
	 * @param location La ubicación del token donde comienzan los parámetros de la
	 *                 función.
	 * @return La posición final de los parámetros de la función o -1 si no se
	 *         encuentra el cierre de paréntesis.
	 */

	private int getFunctionParamsEnd(TokenString tokens, int location) {
		int openParentheses = 0;
		for (int i = location; i < tokens.getLength(); i++) {
			Token t = tokens.tokenAt(i);
			if (t.type == TokenType.OPEN_PARENTHESES) {
				openParentheses++;
			} else if (t.type == TokenType.CLOSE_PARENTHESES) {
				if (openParentheses == 0) {
					return i;
				}
				openParentheses--;
			}
		}
		return -1;
	}

	/**
	 * Método que escanea una cadena de tokens desde la derecha en busca del token
	 * especificado, ignorando los tokens entre paréntesis.
	 * 
	 * @param tokens Un objeto TokenString que representa la cadena de tokens a
	 *               escanear.
	 * @param type   El tipo de token que se está buscando.
	 * @return La posición del último token encontrado o -1 si no se encuentra el
	 *         token especificado.
	 */

	private int scanFromRight(TokenString tokens, TokenType type) {
		int openParentheses = 0;
		for (int i = tokens.getLength() - 1; i >= 0; i--) {
			Token t = tokens.tokenAt(i);
			if (t.type == TokenType.CLOSE_PARENTHESES) {
				openParentheses++;
			} else if (t.type == TokenType.OPEN_PARENTHESES) {
				openParentheses--;
			} else if (t.type == type && openParentheses == 0) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Método que escanea una cadena de tokens desde la derecha en busca de uno de
	 * varios tipos de token especificados, ignorando los tokens entre paréntesis.
	 * 
	 * @param tokens Un objeto TokenString que representa la cadena de tokens a
	 *               escanear.
	 * @param types  Un arreglo de TokenType que contiene los tipos de token que se
	 *               están buscando.
	 * @return La posición del último token encontrado o -1 si no se encuentra
	 *         ninguno de los tipos de token especificados.
	 */

	private int scanFromRight(TokenString tokens, TokenType[] types) {
		int openParentheses = 0;
		for (int i = tokens.getLength() - 1; i >= 0; i--) {
			Token t = tokens.tokenAt(i);
			if (t.type == TokenType.CLOSE_PARENTHESES) {
				openParentheses++;
			} else if (t.type == TokenType.OPEN_PARENTHESES) {
				openParentheses--;
			} else {
				if (openParentheses == 0) {
					for (int j = 0; j < types.length; j++) {
						if (t.type == types[j]) {
							return i;
						}
					}
				}
			}
		}
		return -1;
	}

	/**
	 * Método que realiza una sustitución para convertir un operador de resta unaria
	 * en una multiplicación por -1. Esto es, por ejemplo, que -x pasa a ser (0-1)*x
	 * 
	 * @param tokens Un objeto TokenString que representa la cadena de tokens a
	 *               escanear y modificar.
	 */

	private void substituteUnaryMinus(TokenString tokens) {
		Token prev = null;
		for (int i = 0; i < tokens.getLength(); i++) {
			Token t = tokens.tokenAt(i);
			if (t.type == TokenType.MINUS) {
				if (prev == null || !(prev.type == TokenType.NUMBER || prev.type == TokenType.X
						|| prev.type == TokenType.CLOSE_PARENTHESES)) {
					tokens.remove(i);
					tokens.insert(i, new Token(TokenType.TIMES));
					tokens.insert(i, new Token(TokenType.CLOSE_PARENTHESES));
					tokens.insert(i, new Token(TokenType.NUMBER, "1"));
					tokens.insert(i, new Token(TokenType.MINUS));
					tokens.insert(i, new Token(TokenType.NUMBER, "0"));
					tokens.insert(i, new Token(TokenType.OPEN_PARENTHESES));
					i += 6;
				}
			}
			prev = t;
		}
	}

	/**
	 * Método que verifica si hay un número correcto de paréntesis abiertos y
	 * cerrados en una cadena de tokens.
	 * 
	 * @param tokens Un objeto TokenString que representa la cadena de tokens a
	 *               verificar.
	 */

	private void checkParentheses(TokenString tokens) {
		int openParentheses = 0;
		for (int i = 0; i < tokens.getLength(); i++) {
			Token t = tokens.tokenAt(i);
			if (t.type == TokenType.OPEN_PARENTHESES) {
				openParentheses++;
			} else if (t.type == TokenType.CLOSE_PARENTHESES) {
				openParentheses--;
			}
			if (openParentheses < 0) {
				error.makeError("You closed too many parentheses!");
			}
		}
		if (openParentheses > 0) {
			error.makeError("You did not close enough parentheses!");
		}
	}

	/**
	 * Método que busca y devuelve el TokenType correspondiente al nombre
	 * especificado.
	 * 
	 * @param name El nombre del TokenType que se desea buscar.
	 * @return El TokenType correspondiente al nombre especificado, o null si no se
	 *         encuentra ninguno.
	 */

	private TokenType getTokenTypeByName(String name) {
		TokenType[] values = TokenType.FUNCTIONS;
		for (TokenType v : values) {
			if (v.name.equals(name))
				return v;
		}
		return null;
	}
}
