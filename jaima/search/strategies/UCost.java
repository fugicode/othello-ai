package jaima.search.strategies;

import java.util.*;
import jaima.util.*;
import jaima.search.*;

/**
 * Uniform Cost search
 */
public class UCost
extends Strategy
{
    SearchQueue searchQueue;
    
    /**
     * create a queue-based search
     * @param sq the type of search queue
     */
    public UCost(SearchQueue sq) 
    {
        Log.record("Strategy: Uniform Cost Search");
        searchQueue = sq;
    }

    /**
     * search for a solution
     * @param p the problem instance
     * @return the list of steps to solve the problem
     */
    public Stack<Node> search(Problem p) 
    {
        G cost = new G();
        return searchQueue.search(p, new PriorityQueue<Node>(100, cost));
    }
}
