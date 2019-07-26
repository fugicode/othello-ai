package jaima.search;

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
    public String prettyPrint()
    {
        return toString();
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
        //...
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
}
