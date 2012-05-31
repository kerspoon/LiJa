/**
 * 
 */
package liJa.types;

/**
 * @author james
 *
 */
public class LispInteger extends LispClass {
	
	private Object value;
	
	/*
	 * Constructor
	 */
	public LispInteger(int value) {
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
		return this.value.toString();
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#equ(liJa.types.LispBase)
	 */
	@Override
	public boolean equ(LispBase other) {
		if (other instanceof LispInteger) {
			LispInteger otherInteger = (LispInteger)other;
			return this.value == otherInteger.value;
		}
		return false;
	}

}
