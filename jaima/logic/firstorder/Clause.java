/*
 * Created on Sep 22, 2004
 *
 */
package jaima.logic.firstorder;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import jaima.logic.firstorder.parsing.FOLParser;
import jaima.logic.firstorder.parsing.ast.Predicate;
import jaima.logic.firstorder.parsing.ast.FOLNode;
//import jaima.logic.firstorder.parsing.ast.Variable;
import jaima.search.csp.Domain;

/**
 * @author Ravi Mohan
 * 
 */

public class Clause {

	private Predicate predicate;

	private Domain domain;

	private FOLParser parser;

	List<String> variableNames;

	public Clause(Predicate predicate, FOLParser parser) {

		this.predicate = predicate;
		this.parser = parser;
		variableNames = new VariableCollector(parser)
				.getAllVariableNames(predicate);

		domain = new Domain(variableNames);
	}

	@Override
	public String toString() {
		return predicate.toString();
	}

	public void populateDomainsFrom(Fact fact) {
		Unifier unifier = new Unifier(parser);
		Hashtable<FOLNode, FOLNode> result 
			= unifier.unify(predicate, fact.predicate(),
				new Hashtable<FOLNode, FOLNode>());
		if (result != null) { // unification succesfull
			Iterator<FOLNode> iter = result.keySet().iterator();
			while (iter.hasNext()) {
				FOLNode key = iter.next();
				String name = key.toString();
				domain.add(name, result.get(key).toString());
			}
		}

	}

	public Domain domain() {

		return domain;
	}

	public boolean contains(String variable) {
		return variableNames.contains(variable);
	}

	public boolean hasValueFor(String variable) {
		return (contains(variable))
				&& (domain.getDomainOf(variable).size() > 0);
	}

	public List<String> valuesFor(String variable) {
		return domain.getDomainOf(variable);
	}
}
