/**
 * 
 */
package liJa.types;


/**
 * @author james
 *
 */
public class LispString extends LispClass {

	private String text;
	
	/*
	 * Ctor
	 */
	public LispString(String text) {
		super(null);
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#eval(liJa.types.LispEnvironment)
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
		return this.text;
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#equ(liJa.types.LispBase)
	 */
	@Override
	public boolean equ(LispBase other) {
		if (other instanceof LispString) {
			return (LispString)other == this;
		}
		return false;
	}

}
