package graphMath.functiongrapher.grapher.expression;

public class Value extends Quantity{
	
	protected double d;
	
	public Value(double d) {
		this.d = d;
	}

	@Override
	public double getValue() {
		return d;
	}

}
