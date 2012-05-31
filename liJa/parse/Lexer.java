/**
 * 
 */
package liJa.parse;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author james
 *
 */
public class Lexer {
	public static enum TokenType { OPENBRACKET, CLOSEBRACKET, QUOTE, COMMENT, STRING, NUMBER, SYMBOL }
	
	public static class Token {
		public TokenType type;
		public String text;
		public Token(TokenType type, String text){
			assert(text.length() != 0);
			this.type = type;
			this.text = text;
		}
	}
	
	public static String asString(List<Token> tokens) {
		String result = "";
		for (Token token : tokens) {
			result += token.text + " ";
		}
		return result;
	}
	
	public static List<Token> toTokens(String input) {
		List<Token> tokens = new LinkedList<Token>();
		input = input.trim();
		while (input.length() != 0) {
			Token token;
			
			if ('(' == input.charAt(0)) {
				token = new Token(TokenType.OPENBRACKET, "(");
			} else if (')' == input.charAt(0)) {
				token = new Token(TokenType.CLOSEBRACKET, ")");
			} else if ('\'' == input.charAt(0)) {
				token = new Token(TokenType.QUOTE, "'");
			} else if (';' == input.charAt(0)) {
				token = new Token(TokenType.COMMENT, input);
			} else if ('"' == input.charAt(0)) {
				token = new Token(TokenType.STRING, readString(input));
			} else if (startWithNumber(input)) {
				token = new Token(TokenType.NUMBER, readNumber(input));	
			} else {
				token = new Token(TokenType.SYMBOL, readSymbol(input));
			}
			
			tokens.add(token);
			input = input.substring(token.text.length());
			input = input.trim();
		}
		return tokens;
	}

	private static boolean startWithNumber(String input) {
	    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	    Matcher matcher = pattern.matcher(input);
		return matcher.lookingAt();
	}
	
	private static String readNumber(String input) {
	    Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	    Matcher matcher = pattern.matcher(input);
	    matcher.lookingAt();
		return matcher.group();
	}

	private static String readString(String input) {
	    Pattern pattern = Pattern.compile("\"[^\"]*\"");
	    Matcher matcher = pattern.matcher(input);
	    matcher.lookingAt();
		return matcher.group();
	}
	
	private static String readSymbol(String input) {
	    try {
		    Pattern pattern = Pattern.compile("[A-Za-z0-9<>%=!^&?*+/-]+");
		    Matcher matcher = pattern.matcher(input);
		    matcher.lookingAt();
			return matcher.group();
		} catch (IllegalStateException e) {
			throw new RuntimeException("Tokenize.readSymbol: cannot understand symbol " + input);
		}
	}
}
