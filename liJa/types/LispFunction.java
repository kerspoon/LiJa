/**
 * 
 */
package liJa.types;


/**
 * @author james
 * 
 */
public abstract class LispFunction extends LispClass implements LispCallable {

	/*
	 * Constructor
	 */
	protected LispFunction() {
		super(null);
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#eval(liJa.types.Environment)
	 */
	@Override
	public LispBase eval(LispEnvironment env) {
		return LispSymbol.constructSymbol(this.str());
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#str()
	 */
	@Override
	public String str() {
		return String.format("<#built-in#>");
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispCallable#apply(liJa.types.LispBase, liJa.types.LispEnvironment)
	 */
	@Override
	public abstract LispBase apply(LispBase values, LispEnvironment env);
}
