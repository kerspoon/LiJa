/**
 * 
 */
package liJa.types;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static liJa.util.util.listToBase;

/**
 * @author james
 * TODO: could add __slots__, __dict__, __size__ to the specials.
 */
public class LispClass implements LispBase {

	private static int currentId = 0;
	public static LispClass topParent = new LispClass(null);
	protected int id;
	protected List<LispClass> parents;
	protected Map<String, LispBase> slots;
	
	/*
	 * Constructor
	 */
	public LispClass(List<LispClass> parents) {
		this.id = currentId;
		currentId += 1;
		if (parents == null) {
			this.parents = new LinkedList<LispClass>();
		} else {
			this.parents = parents;
		}
		this.slots = new Hashtable<String, LispBase>();

    	// System.out.format("created class %d (%d)\n", id, this.parents.size());
	}
	
	/* (non-Javadoc)
	 * @see liJa.types.LispBase#str()
	 */
	@Override
	public String str() {
		return String.format("<#class-%d#>", this.id);
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#equ(liJa.types.LispBase)
	 */
	@Override
	public boolean equ(LispBase other) {
		return this == other;
	}

	/* (non-Javadoc)
	 * @see liJa.types.LispBase#eval(liJa.types.LispBase)
	 */
	@Override
	public LispBase eval(LispEnvironment env) {
		return LispSymbol.constructSymbol(this.str());
	}

	public LispBase get(String key) {
		assert(key != null);
		if (frameContains(key)) {
			return frameGet(key);
		}

		if(this == topParent || parents == null){
			throw new RuntimeException("missing symbol " + key);
		}

		for (LispClass parent : parents) {
			assert(parent != null);
			if (parent.contains(key)) {
				return parent.get(key);
			}
		}
		throw new RuntimeException("missing symbol " + key);
	}

	private boolean contains(String key) {
		if (frameContains(key)) {
			return true;
		}
		for (LispClass parent : parents) {
			if (parent.contains(key)) {
				return parent.contains(key);
			}
		}
		return false;
	}

	/*
	 * add only works when the key is not already defined in this
	 * don't really care if it is defined in its parents.
	 */
	public void add(String key, LispBase value) {
		assert !frameContains(key);
		framePut(key, value);
	}

	/*
	 * add only works when the key is already defined.
	 */
	public void set(String key, LispBase value) {
		if (frameContains(key)) {
			framePut(key, value);
			return;
		}
		for (LispClass parent : parents) {
			if (parent.contains(key)) {
				parent.set(key, value);
				return;
			}
		}
		throw new RuntimeException("missing symbol " + key);
	}
	
	private boolean frameContains(String key) {
		if (key == "__parents__") {
			return true;
		}
		return this.slots.containsKey(key);
	}
	
	private LispBase frameGet(String key) {
		if (key == "__parents__") {
			LinkedList<LispBase> baseParents = new LinkedList<LispBase>();
			for (LispBase parent : parents) {
				baseParents.add((LispBase)parent);
			}
			return listToBase(baseParents);
		}
		return this.slots.get(key);
	}

	private LispBase framePut(String key, LispBase value) {
		if (key == "__parents__") {
			throw new RuntimeException("cannot change parents");
		}
		return this.slots.put(key, value);
	}
}
