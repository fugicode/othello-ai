/*
 * Created on Sep 20, 2004
 *
 */
package jaima.logic.firstorder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jaima.logic.firstorder.parsing.AbstractFOLVisitor;
import jaima.logic.firstorder.parsing.FOLParser;
import jaima.logic.firstorder.parsing.ast.FOLNode;
import jaima.logic.firstorder.parsing.ast.Sentence;
import jaima.logic.firstorder.parsing.ast.Variable;

/**
 * @author Ravi Mohan
 * 
 */

public class VariableCollector extends AbstractFOLVisitor {

	private FOLParser parser;

	public VariableCollector(FOLParser parser) {

		super(parser);
	}

	@Override
	public Object visitVariable(Variable var, Object arg) {
		Set<Variable> variables = (Set<Variable>) arg;
		variables.add(var);
		return var;
	}

	public Set collectAllVariables(Sentence sentence) {
		Set variables = new HashSet();
		Object sen = sentence.accept(this, variables);
		// recreate(sen);
		return variables;
	}

	public List<String> getAllVariableNames(Sentence sentence) {
		Set variables = collectAllVariables(sentence);
		List<String> names = new ArrayList<String>();
		Iterator iter = variables.iterator();
		while (iter.hasNext()) {
			Variable v = (Variable) iter.next();
			names.add(v.getValue());
		}
		return names;
	}

}
