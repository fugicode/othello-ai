/*
 * Created on Dec 10, 2004
 *
 */
package jaima.logic.propositional.parsing.ast;

import java.util.Comparator;

/**
 * @author Ravi Mohan
 * 
 */

public class SymbolComparator implements Comparator<Symbol> {

	public int compare(Symbol one, Symbol two) {
		return one.getValue().compareTo(two.getValue());
	}

}
