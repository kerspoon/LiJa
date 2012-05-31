/**
 * 
 */
package liJa.types;

/**
 * @author james
 *
 */
public interface LispBase {
	LispBase eval(LispEnvironment env);
	String str();
	boolean equ(LispBase other);
}
