package jaima.game;

import java.util.*;

/**
 * A class to hold all the information about 
 * the state of a Problem
 * 
 * @author William H. Hooper
 * @version 2006-12-23
 */
public abstract class State
implements Comparable<State>, Cloneable
{    
    /**
     * You MUST define a compact string conversion. 
     * @return a string that identifies the state.
     * If s1 and s2 are two different states,
     * !s1.toString().equals(s2.toString()) .
     * If s1 and s2 are equivalent, though,
     * s1.toString().equals(s2.toString()) .
     */
    public abstract String toString();
    
    /**
     * Optionally, you may redefine this method.
     * @return an ASCII art representation of the state.
     */
    public String prettyPrint(String indent)
    {
        return indent + toString();
    }
    
    /**
     * Optionally, you may redefine this method.
     * @return an ASCII art representation of the state.
     */
    public String prettyPrint()
    {
        return prettyPrint("");
    }
    
    /**
     * You MUST overload this method
     * with a method that produces a valid State.
     * @param fromString a compact, 
     * precise description of the state.
     * It MUST be true that:
     * <pre>
        String v = "...";   // a valid state representation
        State s = new State(v);
        String w = s.toString();
        assertEquals(v, w);
       </pre>
     */
    public State(String fromString)
    {
//         Metrics.increment("State count");
        //...
    }

    /**
     * A state created from an empty string should have
     * a fresh game board, with an arbitrary player
     * chosen to move first.
     */
    public State()
    {
        this("");
    }
    
    /**
     * Most of you should implement the clone() method.
     * A state will not work properly in a search tree 
     * unless clone() returns a deep copy.
     * @return an identical State
     */
    public abstract State clone();
//     {
//         return new State(toString());
//     }
    
    /**
     * You must return a collection of all legal moves 
     * from this state, with no duplicates.
     * @return a collection of moves
     */
    public abstract Collection<Move> actions();
    
    /**
     * You must correctly determine whether a state is
     * final (a win, tie, draw) or not.
     * @return true if a state is final, false if not
     */
    public abstract boolean terminal();
    
    /**
     * @param p the player who is applying the strategy
     * @return the true value of a terminal state,
     * or the estimates value of a nonterminal
     * positive values are winning
     */
    public abstract double utility(Player p);
    
    /**
     * @return the player who has the next move
     */
    public abstract Player toMove();
    
    /**
     * @return the state resulting from a move
     */
    public abstract State result(Move m);
    
    /**
     * This method is required, so that States can be stored in
     * an ordered structure such as a TreeMap.
     * You may overload it for efficiency,
     * but the method below should work as it is.
     */
    public int compareTo(State s)
    {
        return toString().compareTo(s.toString());
    }
    
    /**
     * This method is required, so that States can be stored in
     * an ordered structure such as a TreeMap.
     * You may overload it for efficiency,
     * but the method below should work as it is.
     */
    public boolean equals(Object s)
    {
        if(!(s instanceof State))
            return false;
            
        return toString().equals(s.toString());
    }
    
    public int hashCode()
    {
        return toString().hashCode();
    }
    
    private void printSuccessors(String prefix, int depth, Player p) {
        System.out.print(toString());
        System.out.print(" (" + utility(p) + ")");
        if(terminal()) 
            System.out.print("*");
        System.out.println();
        
        if(depth <= 0) return;
        if(terminal()) return;
        
        for(Move m : actions()) {
            State next = result(m);
            System.out.print(prefix + m + " -> ");
            next.printSuccessors(prefix + "    ", depth - 1, p);
        }
    }
    
    public void printSuccessors(int depth) {
        printSuccessors("    ", depth, toMove());
    }
    
    public void printSuccessors() {
        printSuccessors(1);
    }
    
}
