package jaima.logic.firstorder.parsing;

import java.util.ArrayList;
import java.util.List;

import jaima.logic.firstorder.parsing.ast.ConnectedSentence;
import jaima.logic.firstorder.parsing.ast.Constant;
import jaima.logic.firstorder.parsing.ast.Function;
import jaima.logic.firstorder.parsing.ast.NotSentence;
import jaima.logic.firstorder.parsing.ast.ParanthizedSentence;
import jaima.logic.firstorder.parsing.ast.Predicate;
import jaima.logic.firstorder.parsing.ast.QuantifiedSentence;
import jaima.logic.firstorder.parsing.ast.Sentence;
import jaima.logic.firstorder.parsing.ast.Term;
import jaima.logic.firstorder.parsing.ast.TermEquality;
import jaima.logic.firstorder.parsing.ast.Variable;

/**
 * @author Ravi Mohan
 * 
 */
public class AbstractFOLVisitor implements FOLVisitor {

	private FOLParser parser;

	public AbstractFOLVisitor(FOLParser parser) {
		this.parser = parser;
	}

	protected Sentence recreate(Object ast) {
		return parser.parse(((Sentence) ast).toString());
	}

	public Object visitVariable(Variable variable, Object arg) {

		return null;
	}

	public Object visitQuantifiedSentence(QuantifiedSentence sentence,
			Object arg) {

		return null;
	}

	public Object visitPredicate(Predicate predicate, Object arg) {
		List<Term> terms = predicate.getTerms();
		List<Term> newTerms = new ArrayList<Term>();
		for (int i = 0; i < terms.size(); i++) {
			Term t = (Term) terms.get(i);
			Term subsTerm = (Term) t.accept(this, arg);
			newTerms.add(subsTerm);
		}
		return new Predicate(predicate.getPredicateName(), newTerms);

	}

	public Object visitTermEquality(TermEquality equality, Object arg) {
		Term newTerm1 = (Term) equality.getTerm1().accept(this, arg);
		Term newTerm2 = (Term) equality.getTerm2().accept(this, arg);
		return new TermEquality(newTerm1, newTerm2);
	}

	public Object visitConstant(Constant constant, Object arg) {
		return constant;
	}

	public Object visitFunction(Function function, Object arg) {
		List<Term> terms = function.getTerms();
		List<Term> newTerms = new ArrayList<Term>();
		for (int i = 0; i < terms.size(); i++) {
			Term t = terms.get(i);
			Term subsTerm = (Term) t.accept(this, arg);
			newTerms.add(subsTerm);
		}
		return new Function(function.getFunctionName(), newTerms);
	}

	public Object visitNotSentence(NotSentence sentence, Object arg) {
		return new NotSentence((Sentence) sentence.getNegated().accept(this,
				arg));

	}

	public Object visitConnectedSentence(ConnectedSentence sentence, Object arg) {
		Sentence substFirst = (Sentence) sentence.getFirst().accept(this, arg);
		Sentence substSecond = (Sentence) sentence.getSecond()
				.accept(this, arg);
		return new ConnectedSentence(sentence.getConnector(), substFirst,
				substSecond);

	}

	public Object visitParanthizedSentence(ParanthizedSentence sentence,
			Object arg) {
		return new ParanthizedSentence((Sentence) sentence.getParanthized()
				.accept(this, arg));

	}

}
