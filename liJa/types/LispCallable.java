/**
 * 
 */
package liJa.types;

/**
 * @author james
 *
 */
public interface LispCallable {
	public LispBase apply(LispBase values, LispEnvironment env);
}
