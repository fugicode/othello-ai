package jaima.search;

import java.util.*;

/**
 * A Search Problem
 * 
 * @author William H. Hooper 
 * @version 2006-12-17
 */
public abstract class Problem
{
    public abstract State initial();
    
    public abstract boolean isGoal(State s);
    
    public abstract Map<SearchAction, State> successors(State s);
    
    public double heuristic(State s)
    {
        return isGoal(s)? 0: 1;
    }
}
