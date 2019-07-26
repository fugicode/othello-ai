package jaima.search.strategies;

import java.util.*;
import jaima.util.*;
import jaima.search.*;

/**
 * queue-based depth-first search
 */
public class QDFS
extends Strategy
{
    SearchQueue searchQueue;
    
    /**
     * create a queue-based search
     * @param sq the type of search queue
     */
    public QDFS(SearchQueue sq) 
    {
        Log.record("Strategy: Queue-Based Depth-First Search");
        searchQueue = sq;
    }

    /**
     * search for a solution
     * @param p the problem instance
     * @return the list of steps to solve the problem
     */
    public Stack<Node> search(Problem p) 
    {
        return searchQueue.search(p, new Stack<Node>());
    }
}
