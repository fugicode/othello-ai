package jaima.search.strategies;

import java.util.*;
import jaima.util.*;
import jaima.search.*;

/**
 * breadth-first search
 */
public class BreadthFS
extends Strategy
{
    SearchQueue searchQueue;
    
    /**
     * create a queue-based search
     * @param sq the type of search queue
     */
    public BreadthFS(SearchQueue sq) 
    {
        Log.record("Strategy: Breadth-First Search");
        searchQueue = sq;
    }

    /**
     * search for a solution
     * @param p the problem instance
     * @return the list of steps to solve the problem
     */
    public Stack<Node> search(Problem p) 
    {
        return searchQueue.search(p, new LinkedList<Node>());
    }
}
