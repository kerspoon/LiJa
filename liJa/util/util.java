package liJa.util;

import java.util.LinkedList;
import java.util.List;

import liJa.types.LispBase;
import liJa.types.LispNil;
import liJa.types.LispPair;

public class util {

	/*
	 * note that a proper list will have a nil at the end
	 */
	public static List<LispBase> baseToList(LispBase values) {
		LinkedList<LispBase> result = new LinkedList<LispBase>();
		
		if (values == LispNil.nil) {
			return result;
		} else if (values instanceof LispPair) {
			LispBase sexp = values;
			while(sexp instanceof LispPair) {
				result.add(((LispPair)sexp).getFirst());
				sexp = ((LispPair)sexp).getSecond();
			}
			result.add(sexp); // in a proper list this will be nil.
			return result;
		} else {
			// I could error here
			return result;
		}
	}
	
	/*
	 * convert to a proper list or nil.
	 */
	public static LispBase listToBase(List<LispBase> values) {
		if (values.size() == 0) {
			return LispNil.nil;
		} else if (values.size() == 1) {
			return new LispPair(values.get(0), LispNil.nil);
		} else {
			LispBase first = values.remove(0);
			return new LispPair(first, listToBase(values));
		}
	}
	

	public static String removeQuotes(String txt) {
		return txt.substring(1, txt.length()-1);
	}
	
}
