/*
 * Created on Sep 18, 2004
 *
 */
package jaima.logic.firstorder.parsing;

import jaima.logic.firstorder.parsing.ast.ConnectedSentence;
import jaima.logic.firstorder.parsing.ast.Constant;
import jaima.logic.firstorder.parsing.ast.Function;
import jaima.logic.firstorder.parsing.ast.NotSentence;
import jaima.logic.firstorder.parsing.ast.ParanthizedSentence;
import jaima.logic.firstorder.parsing.ast.Predicate;
import jaima.logic.firstorder.parsing.ast.QuantifiedSentence;
import jaima.logic.firstorder.parsing.ast.TermEquality;
import jaima.logic.firstorder.parsing.ast.Variable;

/**
 * @author Ravi Mohan
 * 
 */
public interface FOLVisitor {
	public Object visitPredicate(Predicate p, Object arg);

	public Object visitTermEquality(TermEquality equality, Object arg);

	public Object visitVariable(Variable variable, Object arg);

	public Object visitConstant(Constant constant, Object arg);

	public Object visitFunction(Function function, Object arg);

	public Object visitNotSentence(NotSentence sentence, Object arg);

	public Object visitConnectedSentence(ConnectedSentence sentence, Object arg);

	public Object visitParanthizedSentence(ParanthizedSentence sentence,
			Object arg);

	public Object visitQuantifiedSentence(QuantifiedSentence sentence,
			Object arg);

}
