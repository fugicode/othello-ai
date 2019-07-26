/*
 * Created on Dec 4, 2004
 *
 */
package jaima.logic.propositional.visitors;

import java.util.HashSet;
import java.util.Set;

import jaima.logic.propositional.parsing.ast.Sentence;
import jaima.logic.propositional.parsing.ast.Symbol;
import jaima.logic.propositional.parsing.ast.UnarySentence;
import jaima.logic.common.SetOps;

/**
 * @author Ravi Mohan
 * 
 */

public class NegativeSymbolCollector extends BasicTraverser {
	@Override
	public Object visitNotSentence(UnarySentence ns, Object arg) {
		Set<Symbol> s = (Set<Symbol>) arg;
		if (ns.getNegated() instanceof Symbol) {
			s.add((Symbol) ns.getNegated());
		} else {
			s = new SetOps<Symbol>().union(s, (Set<Symbol>) ns.getNegated()
					.accept(this, arg));
		}
		return s;
	}

	public Set<Symbol> getNegativeSymbolsIn(Sentence s) {
		return (Set<Symbol>) s.accept(this, new HashSet());
	}

}
