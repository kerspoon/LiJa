/**
 * 
 */
package liJa.types;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import liJa.functions.Functions;

/**
 * @author james
 * 
 * Binds symbols to values. Separate frame can mean bindings can 
 * shadow others. You cannot re-bind something in the same frame though 
 * you can set it to something else.
 */
public class LispEnvironment extends LispClass {

	public static final LispEnvironment baseEnvironment;

    static {
		List<String> keys = new LinkedList<String>();
		List<LispBase> values = new LinkedList<LispBase>();
		
		keys.add("nil");
		values.add((LispBase) LispNil.nil);
		keys.add("true");
		values.add((LispBase) LispBool.boolTrue);
		keys.add("true");
		values.add((LispBase) LispBool.boolFalse);
		keys.add("lambda");
		values.add((LispBase) new Functions.FuncLambda());
		keys.add("define");
		values.add((LispBase) new Functions.FuncDefine());
		keys.add("if");
		values.add((LispBase) new Functions.FuncIf());
		keys.add("quote");
		values.add((LispBase) new Functions.FuncQuote());
		keys.add("begin");
		values.add((LispBase) new Functions.FuncBegin());
		
		baseEnvironment = new LispEnvironment(keys, values, null);
    }
    
	/*
	 * Constructor
	 */
	public LispEnvironment(List<String> keys, List<LispBase> values, List<LispClass> parents) {
		super(parents);

		assert (keys.size() == values.size());
		Iterator<String> iterKeys = keys.iterator();
		for (Iterator<LispBase> iterValues = values.iterator(); iterValues.hasNext();) {
			LispBase value = iterValues.next();
			String key = iterKeys.next();
			this.add(key, value);
		}
	}    	

	/*
	 * Constructor
	 */
	public LispEnvironment() {
		super(null);
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispClass#str()
	 */
	@Override
	public String str() {
		return String.format("<#environment-%d#>", this.id);
	}
}
