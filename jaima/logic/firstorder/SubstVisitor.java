/*
 * Created on Sep 20, 2004
 *
 */
package jaima.logic.firstorder;

import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import jaima.logic.firstorder.parsing.AbstractFOLVisitor;
import jaima.logic.firstorder.parsing.FOLParser;
import jaima.logic.firstorder.parsing.ast.QuantifiedSentence;
import jaima.logic.firstorder.parsing.ast.Sentence;
import jaima.logic.firstorder.parsing.ast.Variable;
import jaima.logic.common.LogicUtils;
import jaima.logic.common.SetOps;

/**
 * @author Ravi Mohan
 * 
 */

public class SubstVisitor extends AbstractFOLVisitor {
	Sentence substitutedSentence = null;

	Sentence originalSentence = null;

	private FOLParser parser;

	public SubstVisitor(FOLParser parser) {
		super(parser);
	}

	@Override
	public Object visitVariable(Variable variable, Object arg) {
		String value = variable.getValue();
		Properties substs = (Properties) arg;
		if (substs.keySet().contains(value)) {
			String key = variable.getValue();
			return new Variable(substs.getProperty(key));
		}
		return variable;

	}

	@Override
	public Object visitQuantifiedSentence(QuantifiedSentence sentence,
			Object arg) {
		// TODO - change properties for hashtable
		Hashtable<String, String> props = (Hashtable<String, String>) arg;
		Sentence quantified = sentence.getQuantified();
		Sentence quantifiedAfterSubs = (Sentence) quantified.accept(this, arg);
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// Set<String> sentenceVariables = new
		// java.util.HashSet<String>(sentence
		// .getVariablesAsString());
		// Set unmatchedVariables = Util.difference(sentenceVariables, props
		// .keySet());
		// **********************************
		Set<String> sentenceVariablesStr = new java.util.HashSet<String>(sentence.getVariablesAsString());
		Set<Variable> sentenceVariables = LogicUtils
				.stringsToVariables(sentenceVariablesStr);
		Set<Variable> propKeysVariables = LogicUtils.stringsToVariables(props
				.keySet());
		Set<Variable> unmatchedVariables = new SetOps<Variable>().difference(
				sentenceVariables, propKeysVariables);
		//		
		// *******************************************************
		// System.out.println("senArs = "+sentenceVariables);
		// System.out.println("props = "+props.keySet());
		// System.out.println("umatched = "+unmatchedVariables+"\n");

		if (!(unmatchedVariables.isEmpty())) {
			List<Variable> variables = new java.util.ArrayList<Variable>(unmatchedVariables);
			QuantifiedSentence sen = new QuantifiedSentence(sentence
					.getQuantifier(), variables, quantifiedAfterSubs);
			// System.out.println(sen);
			return sen;
		} else {
			return recreate(quantifiedAfterSubs);

		}

	}

	public Sentence getSubstitutedSentence(Sentence beforeSubst, Properties p) {
		// System.out.println(beforeSubst.toString());
		Sentence sen = (Sentence) beforeSubst.accept(this, p);
		// System.out.println(sen.toString());
		Sentence afterSubst = recreate(sen);
		// System.out.println(afterSubst.toString());
		// System.out.println("***");
		return afterSubst;

	}

}
