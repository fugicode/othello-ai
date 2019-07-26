package jaima.game.strategies;

import java.util.*;
import jaima.game.*;

/**
 * Choose the first move available.  
 * If the state returns actions in order of preference,
 * this strategy will choose the preferred action.
 * 
 * @author William H. Hooper
 * @version 2011-02-22
 */
public class Greedy
extends Strategy
{
    public Greedy()
    {
        super();
    }
    
    public Move choose(State s)
    {
        Iterator<Move> it = s.actions().iterator();
        if (!it.hasNext())
            return null;
            
        return it.next();
    }
}
