package jaima.logic.propositional.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jaima.logic.propositional.parsing.PEParser;
import jaima.logic.propositional.parsing.ast.Sentence;
import jaima.logic.propositional.parsing.ast.Symbol;
import jaima.logic.propositional.visitors.SymbolCollector;
import jaima.logic.propositional.visitors.CNFTransformer;
import jaima.logic.common.SetOps;
import jaima.logic.common.LogicUtils;
import jaima.util.Util;

/**
 * @author Ravi Mohan
 */

public class KnowledgeBase {
    private List<Sentence> sentences;
    private PEParser parser;
    private boolean verbose;
    
    public KnowledgeBase(boolean v) {
        sentences = new ArrayList<Sentence>();
        parser = new PEParser();
        verbose = v;
    }

    public KnowledgeBase() {
        this(false);
    }

    public void tell(String aSentence) {
        Sentence sentence = (Sentence) parser.parse(aSentence);
        if (!(sentences.contains(sentence))) {
            sentences.add(sentence);
        }
    }

    public void tellAll(String[] percepts) {
        for (int i = 0; i < percepts.length; i++) {
            tell(percepts[i]);
        }

    }

    public int size() {
        return sentences.size();
    }

    public Sentence asSentence() {
        return LogicUtils.chainWith("AND", sentences);
    }

    @Override
    public String toString() {
        if (sentences.size() == 0) {
            return "";
        } else
            return asSentence().toString();
    }
    
    public String prettyString() {
        String output = "";
        String separator = "    ";
        for(Sentence s: sentences) {
            output += separator + s;
            separator = "\n    ";
        }
        return output;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }
}
