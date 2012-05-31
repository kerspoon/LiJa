/**
 * 
 */
package liJa.types;

import java.util.LinkedList;
import java.util.List;
import static liJa.util.util.baseToList;

/**
 * @author james
 *
 */
public class LispLambda extends LispClass implements LispCallable {
	
	private List<String> keys;
	private LispBase body;
	
	public LispLambda(List<String> keys, LispBase body) {
		super(null);
		this.keys = keys;
		this.body = new LispPair(LispSymbol.constructSymbol("begin"), body);
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
		return String.format("<#procedure-%d#>", this.id);
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#equ(liJa.types.LispBase)
	 */
	@Override
	public boolean equ(LispBase other) {
		return (this == other);
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispCallable#apply(liJa.types.LispBase, liJa.types.LispEnvironment)
	 * 
	 * eval all the args.
	 * extend the Environment with the new frame.
	 * eval the body in the new Environment.
	 * 
	 */
	@Override
	public LispBase apply(LispBase values, LispEnvironment env) {	
		
		LinkedList<LispBase> newValues = new LinkedList<LispBase>();
		for (LispBase arg : baseToList(values)) {
			newValues.add(arg.eval(env));
		}
		
		List<LispClass> parents = new LinkedList<LispClass>();
		parents.add(env);
		LispEnvironment newEnvironment = new LispEnvironment(keys, newValues, parents);
		
		return this.body.eval(newEnvironment);
	}

}
