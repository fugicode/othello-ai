/*
 * Created on Sep 15, 2003 by Ravi Mohan
 *  
 */
package jaima.logic.propositional.parsing.ast;

import jaima.logic.common.ParseTreeNode;
import jaima.logic.propositional.parsing.PLVisitor;

/**
 * @author Ravi Mohan
 * 
 */

public abstract class Sentence implements ParseTreeNode {

	public abstract Object accept(PLVisitor plv, Object arg);

}
