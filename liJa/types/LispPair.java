/**
 * 
 */
package liJa.types;

/**
 * @author james
 *
 */
public class LispPair extends LispClass {
	
	private LispBase first;
	private LispBase second;

	/*
	 * Constructor
	 */
	public LispPair(LispBase first, LispBase second) {
		super(null);
        this.setFirst(first);
        this.setSecond(second);
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#eval(liJa.types.Environment)
	 */
	@Override
	public LispBase eval(LispEnvironment env) {
		
		System.out.format("eval pair %d %s\n", id, this.str());
		
		LispBase evald = this.getFirst().eval(env);
		if (evald instanceof LispCallable) {
			LispCallable func = (LispCallable)evald;
			return func.apply(this.getSecond(), env);
		} else {
			throw new RuntimeException("expected a callable expression");
		}
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#str()
	 */
	@Override
	public String str() {
		String text = "( ";
		LispBase sexp = this;
		while (sexp instanceof LispPair) {
			LispPair sexpPair = (LispPair)sexp;
			text += sexpPair.getFirst().str() + " ";
			sexp = sexpPair.getSecond();
		}
		if (sexp != LispNil.nil) {
			text += ". " + sexp.str() + " ";
		}
		text += ")";
		return text;
	}
	
	/* (non-Javadoc)
	 * @see liJa.types.LispBase#equ(liJa.types.LispBase)
	 */
	@Override
	public boolean equ(LispBase other) {
		if (other instanceof LispPair) {
			LispPair otherPair = (LispPair)other;
			if ((this.getFirst() == otherPair.getFirst()) &&
				(this.getSecond() == otherPair.getSecond())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the first
	 */
	public LispBase getFirst() {
		return first;
	}

	/**
	 * @param first the first to set
	 */
	public void setFirst(LispBase first) {
		this.first = first;
	}

	/**
	 * @return the second
	 */
	public LispBase getSecond() {
		return second;
	}

	/**
	 * @param second the second to set
	 */
	public void setSecond(LispBase second) {
		this.second = second;
	}

}
