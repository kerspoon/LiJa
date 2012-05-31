/**
 * 
 */
package liJa.main;

import static liJa.parse.Lexer.toTokens;
import static liJa.parse.Parse.doParse;

import java.util.List;

import liJa.parse.Lexer.Token;
import liJa.types.LispBase;
import liJa.types.LispEnvironment;

/**
 * @author james
 *
 * TODO: add `begin` so we don't get an error when evaluating lambda.
 * TODO: make function to operator for classes.
 */
public class Main {
	
	public static String rep(String text) {
		List<Token> tokens = toTokens(text);
		LispBase sexp = doParse(tokens);
		LispBase evaldSexp = sexp.eval(LispEnvironment.baseEnvironment);
		return evaldSexp.str();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
    	System.out.println(rep("(if true 3 4)"));
    	System.out.println(rep("()"));
    	System.out.println(rep("(define x 3)"));
    	System.out.println(rep("(lambda (x) 3)"));
    	System.out.println(rep("x"));
    	System.out.println(rep("(define bob (lambda (t) 5))"));
    	System.out.println(rep("bob"));
    	System.out.println(rep("(bob)"));
	}
}
