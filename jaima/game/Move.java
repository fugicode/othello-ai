package jaima.game;

/**
 * Move - represents one Move in a multi-player game.
 * 
 * @author William H. Hooper
 * @version 2012-09-27
 */
public abstract class Move
implements Comparable<Move>
{
    /**
     * You must define a string conversion that identifies
     * each distinct state.
     */
    public abstract String toString();
    
    /**
     * You may overload this method
     * to define a version for game display
     */
    public String prettyPrint()
    {
        return toString();
    }
    
    /**
     * You may define a more efficient comparison
     */
    public int compareTo(Move s)
    {
        return toString().compareTo(s.toString());
    }
    
    /**
     * You may define a more efficient comparison
     */
    public boolean equals(Object s)
    {
        if(!(s instanceof Move))
            return false;
            
        return toString().equals(s.toString());
    }
}
