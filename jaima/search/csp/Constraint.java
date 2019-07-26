/*
 * Created on Sep 21, 2004
 *
 */
package jaima.search.csp;

/**
 * @author Ravi Mohan
 * 
 */

public interface Constraint {
	boolean isSatisfiedWith(Assignment assignment, String variable, Object value);

}
