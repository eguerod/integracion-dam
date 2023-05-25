package graphMath.functiongrapher.grapher.parser;

public enum TokenType {
	OPEN_PARENTHESES("("), // Paréntesis izquierdo
	CLOSE_PARENTHESES(")"), // Paréntesis derecho
	PLUS("+"), // Operador de suma
	MINUS("-"), // Operador de resta
	TIMES("*"), // Operador de multiplicación
	DIVIDED_BY("/"), // Operador de división
	RAISED_TO("^"), // Operador exponencial
	SINE("sin"), // Función seno
	COSINE("cos"), // Función coseno
	TANGENT("tan"), // Función tangente
	COTANGENT("cot"), // Función cotangente
	SECANT("sec"), // Función secante
	COSECANT("csc"), // Función cosecante
	CEILING("ceil"), // Función techo (Redondeo a la alta)
	FLOOR("floor"), // Función piso (Redondeo a la baja)
	LOG("log"), // Función logaritmo
	MODULO("%"), // Operador módulo
	NTHROOT("nthroot"), // el token representa la función raíz n-ésima "nthroot"
	SQUARE_ROOT("sqrt"), // el token representa la función raíz cuadrada "sqrt"
	ABSOLUTE_VALUE("abs"), // el token representa la función valor absoluto "abs"
	COMMA(","), // Coma
	X("x"), // Variable x
	Y("x"), // Variable y
	Z("z"), // Variable z
	NUMBER(""); // Número

	public static final TokenType[] FUNCTIONS = { SINE, COSINE, TANGENT, COTANGENT, SECANT, COSECANT, SQUARE_ROOT,
			CEILING, FLOOR, LOG, MODULO, ABSOLUTE_VALUE, NTHROOT };

	public final String name;

	private TokenType(String name) {
		this.name = name;
	}
}
