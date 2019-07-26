/*
 * Created on Sep 20, 2004
 *
 */
package jaima.logic.firstorder.parsing.ast;

import jaima.logic.common.ParseTreeNode;
import jaima.logic.firstorder.parsing.FOLVisitor;

/**
 * @author Ravi Mohan
 * 
 */
public interface FOLNode extends ParseTreeNode {
	public Object accept(FOLVisitor v, Object arg);

	public String toString();

	public FOLNode copy();

}
