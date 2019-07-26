/*
 * Created on Sep 15, 2003 by Ravi Mohan
 *  
 */
package jaima.logic.propositional.parsing;

import jaima.logic.common.Visitor;
import jaima.logic.propositional.parsing.ast.BinarySentence;
import jaima.logic.propositional.parsing.ast.FalseSentence;
import jaima.logic.propositional.parsing.ast.MultiSentence;
import jaima.logic.propositional.parsing.ast.Symbol;
import jaima.logic.propositional.parsing.ast.TrueSentence;
import jaima.logic.propositional.parsing.ast.UnarySentence;

/**
 * @author Ravi Mohan
 * 
 */

public interface PLVisitor extends Visitor {
	public Object visitSymbol(Symbol s, Object arg);

	public Object visitTrueSentence(TrueSentence ts, Object arg);

	public Object visitFalseSentence(FalseSentence fs, Object arg);

	public Object visitNotSentence(UnarySentence fs, Object arg);

	public Object visitBinarySentence(BinarySentence fs, Object arg);

	public Object visitMultiSentence(MultiSentence fs, Object arg);
}
