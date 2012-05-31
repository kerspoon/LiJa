/**
 * 
 */
package liJa.types;

/**
 * @author james
 *
 */
public class LispNil extends LispClass {

	public static final LispBase nil = new LispNil();
	
	/*
	 * Singleton - use `LispNil.nil`.
	 */
	protected LispNil() {
		super(null);
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#eval(liJa.types.Environment)
	 */
	@Override
	public LispBase eval(LispEnvironment env) {
		return this;
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#str()
	 */
	@Override
	public String str() {
		return "nil";
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#equ(liJa.types.LispBase)
	 */
	@Override
	public boolean equ(LispBase other) {
		return (this == other);
	}

}
