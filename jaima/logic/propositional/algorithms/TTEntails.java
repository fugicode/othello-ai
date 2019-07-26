package jaima.logic.propositional.algorithms;

import java.util.List;
import java.util.Set;

import jaima.logic.propositional.parsing.PEParser;
import jaima.logic.propositional.parsing.ast.Sentence;
import jaima.logic.propositional.parsing.ast.Symbol;
import jaima.logic.propositional.visitors.SymbolCollector;
import jaima.logic.common.SetOps;
import jaima.util.Util;

/**
 * Write a description of class TTEntails here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TTEntails
{
    private boolean verbose;
    
    /**
     * Constructor for objects of class TTEntails
     */
    public TTEntails()
    {
        this(false);
    }
    
    public TTEntails(boolean v)
    {
        verbose = v;
    }

    public boolean ask(KnowledgeBase kb, String alpha) {
        Sentence kbSentence = kb.asSentence();
        Sentence querySentence = (Sentence) new PEParser().parse(alpha);
        SymbolCollector collector = new SymbolCollector();
        Set<Symbol> kbSymbols = collector.getSymbolsIn(kbSentence);
        Set<Symbol> querySymbols = collector.getSymbolsIn(querySentence);
        Set<Symbol> symbols = new SetOps<Symbol>().union(kbSymbols,
                querySymbols);
        List<Symbol> symbolList = new java.util.ArrayList<Symbol>(symbols);
        return ttCheckAll(kbSentence, querySentence, symbolList, new Model());
    }

    public boolean ttCheckAll(Sentence kbSentence, Sentence querySentence,
            List<Symbol> symbols, Model model) {
        if (symbols.isEmpty()) {
            boolean result;
            if (model.isTrue(kbSentence)) {
                   // System.out.println("#");
                result = model.isTrue(querySentence);
                if(verbose) {
                    System.out.println(model + ": " + result);
                }
            } else {
                // System.out.println("0");
                result = true;
                if(verbose) {
                    System.out.println(model + ": vacuously true");
                }
            }
            return result;
        } else {
            Symbol symbol = (Symbol) Util.first(symbols);
            List<Symbol> rest = Util.rest(symbols);

            Model trueModel = 
                model.extend(new Symbol(symbol.getValue()), true);
            Model falseModel = 
                model.extend(new Symbol(symbol.getValue()), false);
                
            return (
                ttCheckAll(kbSentence, querySentence, rest, trueModel) 
                && (
                ttCheckAll(kbSentence, querySentence, rest, falseModel)
                ));
        }
    }
}
