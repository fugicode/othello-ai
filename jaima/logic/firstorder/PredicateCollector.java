/*
 * Created on Sep 22, 2004
 *
 */
package jaima.logic.firstorder;

import java.util.ArrayList;
import java.util.List;

import jaima.logic.firstorder.parsing.FOLVisitor;
import jaima.logic.firstorder.parsing.ast.ConnectedSentence;
import jaima.logic.firstorder.parsing.ast.Constant;
import jaima.logic.firstorder.parsing.ast.Function;
import jaima.logic.firstorder.parsing.ast.NotSentence;
import jaima.logic.firstorder.parsing.ast.ParanthizedSentence;
import jaima.logic.firstorder.parsing.ast.Predicate;
import jaima.logic.firstorder.parsing.ast.QuantifiedSentence;
import jaima.logic.firstorder.parsing.ast.Sentence;
import jaima.logic.firstorder.parsing.ast.TermEquality;
import jaima.logic.firstorder.parsing.ast.Variable;

/**
 * @author Ravi Mohan
 * 
 */

public class PredicateCollector implements FOLVisitor {

	public PredicateCollector() {

	}

	public Object visitPredicate(Predicate p, Object arg) {

		List<Predicate> predicates = (List<Predicate>) arg;
		predicates.add(p);
		return predicates;
	}

	public Object visitTermEquality(TermEquality equality, Object arg) {
		return arg;
	}

	public Object visitVariable(Variable variable, Object arg) {

		return arg;
	}

	public Object visitConstant(Constant constant, Object arg) {
		return arg;
	}

	public Object visitFunction(Function function, Object arg) {
		return arg;
	}

	public Object visitNotSentence(NotSentence sentence, Object arg) {
		sentence.getNegated().accept(this, arg);
		return arg;
	}

	public Object visitConnectedSentence(ConnectedSentence sentence, Object arg) {
		sentence.getFirst().accept(this, arg);
		sentence.getSecond().accept(this, arg);
		return arg;
	}

	public Object visitParanthizedSentence(ParanthizedSentence sentence,
			Object arg) {
		sentence.getParanthized().accept(this, arg);
		return arg;
	}

	public Object visitQuantifiedSentence(QuantifiedSentence sentence,
			Object arg) {
		sentence.getQuantified().accept(this, arg);
		return arg;
	}

	public List<Predicate> getPredicates(Sentence s) {
		return (List) s.accept(this, new ArrayList<Predicate>());
	}

}
