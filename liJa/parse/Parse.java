/**
 * 
 */
package liJa.parse;

import java.util.LinkedList;
import java.util.List;

import liJa.parse.Lexer.Token;
import liJa.parse.Lexer.TokenType;
import liJa.types.LispBase;
import liJa.types.LispBool;
import liJa.types.LispInteger;
import liJa.types.LispNil;
import liJa.types.LispPair;
import liJa.types.LispString;
import liJa.types.LispSymbol;
import static liJa.util.util.removeQuotes;
import static liJa.util.util.listToBase;

/**
 * @author james
 *
 */
public class Parse {
	
	static LispSymbol quoteSymbol = LispSymbol.constructSymbol("quote");

	/*
	 * TODO: deal with comments 
	 */
	public static LispBase doParse(List<Token> tokens) {
		
		if(tokens.size() == 0) {
			throw new RuntimeException("no symbols");
		}
		
	    Token tok = tokens.remove(0);
	    
	    if (tok.type == TokenType.CLOSEBRACKET) {
	    	throw new RuntimeException("found ')' mismatched bracket");
	    } else if (tok.type == TokenType.QUOTE) {
	    	return new LispPair(quoteSymbol, doParse(tokens));
	    } else if (tok.type == TokenType.STRING) {
	    	return new LispString(removeQuotes(tok.text));
	    } else if (tok.type == TokenType.NUMBER) {
	    	return new LispInteger(Integer.parseInt(tok.text));
	    } else if (tok.type == TokenType.SYMBOL) {
	    	if (tok.text == "nil") {
				return LispNil.nil;
			} else if (tok.text == "true") {
				return LispBool.boolTrue;
			} else if (tok.text == "false") {
				return LispBool.boolFalse;
			} else{
		    	return LispSymbol.constructSymbol(tok.text);
			}
	    } else if (tok.type == TokenType.OPENBRACKET) {
		    if (tokens.get(0).type == TokenType.CLOSEBRACKET) {
		    	tokens.remove(0);
		    	return LispNil.nil;
		    }
		    
		    LinkedList<LispBase> tokenList = new LinkedList<LispBase>();
		    tokenList.add(doParse(tokens));
		    while (tokens.get(0).type != TokenType.CLOSEBRACKET) {
		    	tokenList.add(doParse(tokens));
			}
	    	tokens.remove(0); // remove the close bracket
	    	return listToBase(tokenList);
	    } else {
	    	throw new RuntimeException("unexpected token");
	    }
	}
}