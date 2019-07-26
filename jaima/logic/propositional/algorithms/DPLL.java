/*
 * Created on Dec 4, 2004
 *
 */
package jaima.logic.propositional.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jaima.logic.propositional.parsing.PEParser;
import jaima.logic.propositional.parsing.ast.Sentence;
import jaima.logic.propositional.parsing.ast.Symbol;
import jaima.logic.propositional.parsing.ast.UnarySentence;
import jaima.logic.propositional.visitors.CNFClauseGatherer;
import jaima.logic.propositional.visitors.CNFTransformer;
import jaima.logic.propositional.visitors.SymbolClassifier;
import jaima.logic.propositional.visitors.SymbolCollector;
import jaima.logic.common.LogicUtils;
import jaima.logic.common.SetOps;

/**
 * @author Ravi Mohan
 * 
 */

public class DPLL {
    private boolean verbose;
    private PEParser parser;
    
    /**
     * Constructor for objects of class TTEntails
     */
    public DPLL()
    {
        this(false);
    }
    
    public DPLL(boolean v)
    {
        parser = new PEParser();
        verbose = v;
    }

    public boolean ask(KnowledgeBase kb, String queryString) {
        Sentence query = null, cnfForm = null;
        try {
            // just a check to see that the query is well formed
            query = (Sentence) parser.parse(queryString);
        } catch (Exception e) {
            System.out.println("error parsing query" + e.getMessage());
        }

        Sentence kbSentence = kb.asSentence();
        Sentence kbPlusQuery = null;
        if (kbSentence != null) {
            kbPlusQuery = (Sentence) parser.parse(" ( " + kbSentence.toString()
                    + " AND " + queryString + " )");
        } else {
            kbPlusQuery = query;
        }
        try {
            cnfForm = new CNFTransformer().transform(kbPlusQuery);
            // System.out.println(cnfForm.toString());
        } catch (Exception e) {
            System.out.println("error converting kb +  query to CNF"
                    + e.getMessage());

        }
        return new DPLL().dpllSatisfiable(cnfForm);
    }
    
	public boolean dpllSatisfiable(Sentence s) {

		return dpllSatisfiable(s, new Model());
	}

	public boolean dpllSatisfiable(String string) {
		Sentence sen = (Sentence) new PEParser().parse(string);
		return dpllSatisfiable(sen, new Model());
	}

	public boolean dpllSatisfiable(Sentence s, Model m) {
		Set<Sentence> clauses = new CNFClauseGatherer()
				.getClausesFrom(new CNFTransformer().transform(s));
		List<Symbol> symbols = new java.util.ArrayList<Symbol>(new SymbolCollector()
				.getSymbolsIn(s));
		// System.out.println(" numberOfSymbols = " + symbols.size());
		return dpll(clauses, symbols, m);
	}

	private boolean dpll(Set<Sentence> clauses, List<Symbol> symbols, Model model) {
		// List<Sentence> clauseList = asList(clauses);
		List<Sentence> clauseList = new java.util.ArrayList<Sentence>(clauses);
		// System.out.println("clauses are " + clauses.toString());
		// if all clauses are true return true;
		if (areAllClausesTrue(model, clauseList)) {
			// System.out.println(model.toString());
			return true;
		}
		// if even one clause is false return false
		if (isEvenOneClauseFalse(model, clauseList)) {
			// System.out.println(model.toString());
			return false;
		}
		// System.out.println("At least one clause is unknown");
		// try to find a unit clause
		SymbolValuePair svp = findPureSymbolValuePair(clauseList, model,
				symbols);
		if (svp.notNull()) {
			List<Symbol> newSymbols = new ArrayList<Symbol>(symbols);
			newSymbols.remove(new Symbol(svp.symbol.getValue()));
			Model newModel = model.extend(new Symbol(svp.symbol.getValue()),
					svp.value.booleanValue());
			return dpll(clauses, newSymbols, newModel);
		}

		SymbolValuePair svp2 = findUnitClause(clauseList, model, symbols);
		if (svp2.notNull()) {
			List<Symbol> newSymbols = new ArrayList<Symbol>(symbols);
			newSymbols.remove(new Symbol(svp2.symbol.getValue()));
			Model newModel = model.extend(new Symbol(svp2.symbol.getValue()),
					svp2.value.booleanValue());
			return dpll(clauses, newSymbols, newModel);
		}

		Symbol symbol = (Symbol) symbols.get(0);
		// System.out.println("default behaviour selecting " + symbol);
		List<Symbol> newSymbols = new ArrayList<Symbol>(symbols);
		newSymbols.remove(0);
		return (dpll(clauses, newSymbols, model.extend(symbol, true)) || dpll(
				clauses, newSymbols, model.extend(symbol, false)));
	}

	private boolean isEvenOneClauseFalse(Model model, List<Sentence> clauseList) {
		for (int i = 0; i < clauseList.size(); i++) {
			Sentence clause = (Sentence) clauseList.get(i);
			if (model.isFalse(clause)) {
				// System.out.println(clause.toString() + " is false");
				return true;
			}

		}

		return false;
	}

	private boolean areAllClausesTrue(Model model, List<Sentence> clauseList) {

		for (int i = 0; i < clauseList.size(); i++) {
			Sentence clause = (Sentence) clauseList.get(i);
			// System.out.println("evaluating " + clause.toString());
			if (!isClauseTrueInModel(clause, model)) { // ie if false or
				// UNKNOWN
				// System.out.println(clause.toString()+ " is not true");
				return false;
			}

		}
		return true;
	}

	private boolean isClauseTrueInModel(Sentence clause, Model model) {
		List<Symbol> positiveSymbols = new java.util.ArrayList<Symbol>(
				new SymbolClassifier().getPositiveSymbolsIn(clause));
		List<Symbol> negativeSymbols = new java.util.ArrayList<Symbol>(
				new SymbolClassifier().getNegativeSymbolsIn(clause));

		for (Symbol symbol : positiveSymbols) {
			if ((model.isTrue(symbol))) {
				return true;
			}
		}
		for (Symbol symbol : negativeSymbols) {
			if ((model.isFalse(symbol))) {
				return true;
			}
		}
		return false;

	}

	public Set<Sentence> findUnitClauses(Set<Sentence> clauses, Symbol symbol, Model m) {
		return null;
	}

	public List<Sentence> clausesWithNonTrueValues(List<Sentence> clauseList,
			Model model) {
		List<Sentence> clausesWithNonTrueValues = new ArrayList<Sentence>();
		for (int i = 0; i < clauseList.size(); i++) {
			Sentence clause = clauseList.get(i);
			if (!(isClauseTrueInModel(clause, model))) {
				if (!(clausesWithNonTrueValues.contains(clause))) {// defensive
					// programming not really necessary
					clausesWithNonTrueValues.add(clause);
				}
			}

		}
		return clausesWithNonTrueValues;
	}

	public SymbolValuePair findPureSymbolValuePair(List<Sentence> clauseList,
			Model model, List<Symbol> symbols) {
		List<Sentence> clausesWithNonTrueValues = clausesWithNonTrueValues(clauseList,
				model);
		Sentence nonTrueClauses = LogicUtils.chainWith("AND",
				clausesWithNonTrueValues);
		// System.out.println("Unsatisfied clauses = "
		// + clausesWithNonTrueValues.size());
		Set<Symbol> symbolsAlreadyAssigned = model.getAssignedSymbols();

		// debug
		// List symList = asList(symbolsAlreadyAssigned);
		//
		// System.out.println(" assignedSymbols = " + symList.size());
		// if (symList.size() == 52) {
		// System.out.println("untrue clauses = " + clausesWithNonTrueValues);
		// System.out.println("model= " + model);
		// }

		// debug
		List<Symbol> purePositiveSymbols = new java.util.ArrayList<Symbol>(
				new SetOps<Symbol>().difference(
						new SymbolClassifier()
								.getPurePositiveSymbolsIn(nonTrueClauses),
						symbolsAlreadyAssigned));

		List<Symbol> pureNegativeSymbols = new java.util.ArrayList<Symbol>(
				new SetOps<Symbol>().difference(
						new SymbolClassifier()
								.getPureNegativeSymbolsIn(nonTrueClauses),
						symbolsAlreadyAssigned));
		// if none found return "not found
		if ((purePositiveSymbols.size() == 0)
				&& (pureNegativeSymbols.size() == 0)) {
			return new SymbolValuePair();// automatically set to null values
		} else {
			if (purePositiveSymbols.size() > 0) {
				Symbol symbol = new Symbol((purePositiveSymbols.get(0))
						.getValue());
				if (pureNegativeSymbols.contains(symbol)) {
					throw new RuntimeException("Symbol " + symbol.getValue()
							+ "misclassified");
				}
				return new SymbolValuePair(symbol, true);
			} else {
				Symbol symbol = new Symbol((pureNegativeSymbols.get(0))
						.getValue());
				if (purePositiveSymbols.contains(symbol)) {
					throw new RuntimeException("Symbol " + symbol.getValue()
							+ "misclassified");
				}
				return new SymbolValuePair(symbol, false);
			}
		}
	}

	private SymbolValuePair findUnitClause(List<Sentence> clauseList, Model model,
			List<Symbol> symbols) {
		for (int i = 0; i < clauseList.size(); i++) {
			Sentence clause = (Sentence) clauseList.get(i);
			if ((clause instanceof Symbol)
					&& (!(model.getAssignedSymbols().contains(clause)))) {
				// System.out.println("found unit clause - assigning");
				return new SymbolValuePair(new Symbol(((Symbol) clause)
						.getValue()), true);
			}

			if (clause instanceof UnarySentence) {
				UnarySentence sentence = (UnarySentence) clause;
				Sentence negated = sentence.getNegated();
				if ((negated instanceof Symbol)
						&& (!(model.getAssignedSymbols().contains(negated)))) {
					// System.out.println("found unit clause type 2 -
					// assigning");
					return new SymbolValuePair(new Symbol(((Symbol) negated)
							.getValue()), false);
				}
			}

		}

		return new SymbolValuePair();// failed to find any unit clause;

	}

	public class SymbolValuePair {
		public Symbol symbol;// public to avoid unnecessary get and set

		// accessors

		public Boolean value;

		public SymbolValuePair() {
			// represents "No Symbol found with a boolean value that makes all
			// its literals true
			symbol = null;
			value = null;
		}

		public SymbolValuePair(Symbol symbol, boolean bool) {
			// represents "Symbol found with a boolean value that makes all
			// its literals true
			this.symbol = symbol;
			value = new Boolean(bool);
		}

		public boolean notNull() {
			return (symbol != null) && (value != null);
		}

		@Override
		public String toString() {
			String symbolString, valueString;
			if (symbol == null) {
				symbolString = "NULL";
			} else {
				symbolString = symbol.toString();
			}
			if (value == null) {
				valueString = "NULL";
			} else {
				valueString = value.toString();
			}
			return symbolString + " -> " + valueString;
		}
	}

}
