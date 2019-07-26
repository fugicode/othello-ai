/*
 * Created on Sep 15, 2003 by Ravi Mohan
 *  
 */
package jaima.logic.propositional.parsing.ast;

import jaima.logic.propositional.parsing.PLVisitor;

/**
 * @author Ravi Mohan
 * 
 */

public class FalseSentence extends AtomicSentence {
	@Override
	public String toString() {
		return "FALSE";
	}

	@Override
	public Object accept(PLVisitor plv, Object arg) {
		return plv.visitFalseSentence(this, arg);
	}
}
