/*
 * Created on Sep 22, 2004
 *
 */
package jaima.logic.firstorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jaima.logic.firstorder.parsing.FOLParser;
import jaima.logic.firstorder.parsing.ast.ConnectedSentence;
import jaima.logic.firstorder.parsing.ast.Predicate;
import jaima.logic.firstorder.parsing.ast.Sentence;
import jaima.logic.common.SetOps;

/**
 * @author Ravi Mohan
 * 
 */

public class Rule {

	private ConnectedSentence original;

	private List<Clause> clauses;

	Predicate conclusion;

//	private FOLParser parser;

	private List<String> variableNames;

	public Rule(Sentence sentence, FOLParser parser) {
		this.original = (ConnectedSentence) sentence;
//		this.parser = parser;
		this.variableNames = new VariableCollector(parser)
				.getAllVariableNames(sentence);
		clauses = new ArrayList<Clause>();
		conclusion = (Predicate) original.getSecond();
		List predicates = breakIntoPredicates(original);
		for (int i = 0; i < predicates.size(); i++) {
			Predicate predicate = (Predicate) predicates.get(i);

			if (!(predicate.equals(conclusion))) {
				Clause clause = new Clause(predicate, parser);
				clauses.add(clause);
			}

		}

	}

	private List<Predicate> breakIntoPredicates(ConnectedSentence sentence) {
		return new PredicateCollector().getPredicates(sentence);
	}

	public int numClauses() {
		return clauses.size();
	}

	public Clause clause(int i) {
		return clauses.get(i);
	}

	public Predicate conclusion() {
		return conclusion;
	}

	public void initializeAllClauseDomainsFrom(List facts) {
		for (int i = 0; i < clauses.size(); i++) {
			Clause c = clauses.get(i);
			for (int j = 0; j < facts.size(); j++) {
				Fact fact = (Fact) facts.get(j);
				c.populateDomainsFrom(fact);
			}
		}

	}

	public List<Clause> clausesContaining(String variable) {
		List<Clause> containingClauses = new ArrayList<Clause>();

		for (Clause c : clauses) {
			if (c.contains(variable)) {
				containingClauses.add(c);
			}
		}
		return containingClauses;
	}

	public boolean triggerable() {

		for (int i = 0; i < variableNames.size(); i++) {
			String variable = (String) variableNames.get(0);
			boolean occursCheck = variableHasBindingsInContainingClauses(variable);
			boolean atLeastOneCommonValue = variableHasAtLeastOneCommonValue(variable);
			if ((!(occursCheck)) && (!(atLeastOneCommonValue))) {
				return false;
			}
		}
		return true;
	}

	private boolean variableHasAtLeastOneCommonValue(String variable) {
		List containingClauses = clausesContaining(variable);
		return (!(commonValuesInContainingClauses(variable).size() == 0));
	}

	public List<String> commonValuesInContainingClauses(String variable) {
		Set<String> valueSet = null;
		List<Clause> containingClauses = clausesContaining(variable);
		for (int i = 0; i < containingClauses.size(); i++) {
			Clause clause = containingClauses.get(i);
			if (!(clause.valuesFor(variable).isEmpty())) {
				Set<String> set = new java.util.HashSet<String>(clause
						.valuesFor(variable));
				if (valueSet == null) {
					valueSet = set;
				} else {
					valueSet = new SetOps<String>().intersection(valueSet, set);
				}
			}
		}
		if (valueSet == null) {
			return new ArrayList<String>();
		} else {
			return new java.util.ArrayList<String>(valueSet);
		}

	}

	private boolean variableHasBindingsInContainingClauses(String variable) {

		List containingClauses = clausesContaining(variable);
		for (int i = 0; i < containingClauses.size(); i++) {
			Clause clause = (Clause) containingClauses.get(i);
			if (!clause.hasValueFor(variable)) {
				return false;
			}
		}
		return true;
	}
}
