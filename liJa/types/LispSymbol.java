/**
 * 
 */
package liJa.types;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author james
 *
 */
public class LispSymbol extends LispClass {
	
	private static Map<String, LispSymbol> symMap = new Hashtable<String, LispSymbol>();
	
	public static LispSymbol constructSymbol(String name) {
		if (symMap.containsKey(name)) {
			return symMap.get(name);
		} else {
			return new LispSymbol(name);
		}
	}
	
	private String name;

	/*
	 * Constructor
	 * Do not call this. LispSymbol must be unique so use the factory.
	 */
	public LispSymbol(String name) {
		super(null);
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#eval(liJa.types.Environment)
	 */
	@Override
	public LispBase eval(LispEnvironment env) {
		return env.get(this.name);
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#str()
	 */
	@Override
	public String str() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#equ(liJa.types.LispBase)
	 */
	@Override
	public boolean equ(LispBase other) {
		return (this == other);
	}

}
