package jaima.search;

/**
 * Write a description of class Action here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class SearchAction
implements Comparable<SearchAction>
{
    /**
     * You must define a string conversion that identifies
     * each distinct state.
     */
    public abstract String toString();
    
    public abstract double cost();
    
    public int compareTo(SearchAction s)
    {
        return toString().compareTo(s.toString());
    }
}
