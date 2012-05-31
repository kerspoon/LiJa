/**
 * 
 */
package liJa.types;

/**
 * @author james
 *
 */
public class LispBool extends LispClass {
	
	public static final LispBase boolTrue = new LispBool(true);
	public static final LispBase boolFalse = new LispBool(false);
	
	private boolean value;

	/*
	 * Doubleton - Constructor
	 */
	protected LispBool(boolean value) {
		super(null);
		this.value = value;
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
		if (this.value) {
			return "true";
		} else {
			return "false";
		}
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#equ(liJa.types.LispBase)
	 */
	@Override
	public boolean equ(LispBase other) {
		return this == other;
	}

	public boolean isTrue() {
		return this.value;
	}

}
