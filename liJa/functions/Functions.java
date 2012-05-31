/**
 * 
 */
package liJa.functions;

import java.util.LinkedList;
import java.util.List;

import liJa.types.LispBase;
import liJa.types.LispBool;
import liJa.types.LispEnvironment;
import liJa.types.LispFunction;
import liJa.types.LispLambda;
import liJa.types.LispNil;
import liJa.types.LispPair;
import liJa.types.LispSymbol;
import static liJa.util.util.baseToList;

/**
 * @author james
 *
 */
public class Functions {

	/*
	 * QUOTE
	 * 
	 * (quote <exp>)
	 * 
	 * Return the <exp> without the quote part.
	 * 
	 * example:
	 *   hi      <= (quote hi)
	 *   (+ 3 4) <= (quote (+ 3 4))
	 *   
	 */
    public static class FuncQuote extends LispFunction {
    	@Override
    	public LispBase apply(LispBase values, LispEnvironment env) {
    		LispPair pairValue = (LispPair)values;
    		assert(pairValue.getSecond() == LispNil.nil);
    		return pairValue.getFirst();
    	}
	}
	
	/*
	 *  DEFINITION
	 *  
	 *  1. (define <var> <value>)
	 *  2. (define (<var> <param1> ... <paramN> ) <body1> ... )
	 *  2. (define (<var> . <param1> ) <body1> ... )
	 *  
	 *  1. Add <var> to the environment with the value eval(<value>).
	 *  2. Convert the second form to define a lambda expression.
	 *       (define <var> ( lambda (<param1> ... <paramN>) <body> ))
	 *     process this in the same way as form one.
	 *  
	 *  example:
	 *    ok <= (define m 2)
	 *    ok <= (define (add8 y) (+ 8 y) )
	 *    10 <= (add8 m)
	 *    
	 */
    public static class FuncDefine extends LispFunction {
    	@Override
    	public LispBase apply(LispBase values, LispEnvironment env) {
    		LispPair pairValue = (LispPair)values;
    		LispBase var;
    		LispBase value;
    		if (pairValue.getFirst() instanceof LispSymbol) {
    			// we have the first form
    			var = pairValue.getFirst();
    			value = ((LispPair)pairValue.getSecond()).getFirst();
    		} else if (pairValue.getFirst() instanceof LispPair) {
    			// we have the second form
    			var = ((LispPair)pairValue.getFirst()).getFirst();
    			LispBase param = ((LispPair)pairValue.getFirst()).getSecond();
    			LispBase body = pairValue.getSecond();
    			value = new LispPair(
    						LispSymbol.constructSymbol("lambda"),
    						new LispPair(
    							param,
    							new LispPair(
    								body,
    								LispNil.nil)));
    		} else {
    			throw new RuntimeException("");
    		}
    		assert (var instanceof LispSymbol);
    		LispBase result = value.eval(env);
    		env.add(var.str(), result);
    		return result;
    	}
	}
	
	
	/*
	 * IF
	 * 
	 * (if <pred> <cons> <alt> )
	 * (if <pred> <cons> )
	 * 
	 * evaluate the predicate. If it's true then
	 * evaluate the consequence, otherwise
	 * evaluate the alternative (or nil if there is no alt)
	 * 
	 * example:
	 *   y <= (if true? 'y 'n)
	 *   5 <= (if (= 2 3) (- 3) (+ 2 3) )
	 *   nil <= (if (= 2 3) 'boop)
	 */
    public static class FuncIf extends LispFunction {
    	@Override
    	public LispBase apply(LispBase values, LispEnvironment env) {
    		List<LispBase> valList = baseToList(values);
    		assert(valList.size() == 3 || valList.size() == 4);
    		assert(valList.get(valList.size()-1) == LispNil.nil);
    		
    	    LispBase predicate = valList.get(0);
    	    LispBase consequence = valList.get(1);
    	    LispBase alternative = valList.get(2);

    	    LispBool result = (LispBool)predicate.eval(env);
    	    if (result.isTrue()) {
    	    	return consequence.eval(env);
    	    } else {
    	    	return alternative.eval(env);
    	    }
    	}	
	}
	
	/*
	 * LAMBDA
	 * 
	 * (lambda (<param1> ... <paramN>) <body1> ... )
	 * (lambda <param> <body1> ... )
	 * 
	 * make a procedure, <parameters> can be a symbol, proper-list or 
	 * dotted-list. when evaluated returns the value of (eval <bodyN>) 
	 * in an environment where <parameters> are bound to the arguments.
	 * 
	 * example:
	 *   #FUN <= (lambda (x) (+ 3 x))
	 *   13   <= ((lambda (x) (+ 3 x)) 10)
	 *   222  <= ((lambda (x) (+ 111 x) 222) 333)
	 */
    public static class FuncLambda extends LispFunction {
    	@Override
    	public LispBase apply(LispBase values, LispEnvironment env) {
    		LispPair pairValue = (LispPair)values;	
    		List<String> keys = new LinkedList<String>();
    		for (LispBase key : baseToList(pairValue.getFirst())) {
    			assert(key instanceof LispSymbol);
    			keys.add(key.str());
    		}
    		LispBase body = pairValue.getSecond();
    		return new LispLambda(keys, body);
    	}
	}
    
    /*
     * BEGIN 
     *  
     * (begin <exp1> ... <expN>)
     * 
     * evaluate each expression in turn. Returning the result
     * of that last one.
     * 
     * example:
     *   5   <= (begin 2 3 4 5)
     *   nil <= (begin (+ x 3) nil)  // shouldn't change x
     *   4   <= (begin (set! x 3) 4) // should change x
     *   
     */
    public static class FuncBegin extends LispFunction {
    	@Override
    	public LispBase apply(LispBase values, LispEnvironment env) {
    		List<LispBase> valList = baseToList(values);
    		assert(valList.size() >= 2);
    		assert(valList.get(valList.size()-1) == LispNil.nil);
    		valList.remove(valList.size()-1);
    		LispBase result = LispNil.nil;
    		for (LispBase value : valList) {
    			result = value.eval(env);
			}
    		return result;
    	}
    }

    
}
