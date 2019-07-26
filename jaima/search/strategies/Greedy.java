package jaima.search.strategies;

import java.util.*;
import jaima.util.*;
import jaima.search.*;

/**
 * breadth-first search
 */
public class Greedy
extends Strategy
{
    SearchQueue searchQueue;
    
    /**
     * create a queue-based search
     * @param sq the type of search queue
     */
    public Greedy(SearchQueue sq) 
    {
        Log.record("Strategy: Greedy Search");
        searchQueue = sq;
    }

    /**
     * search for a solution
     * @param p the problem instance
     * @return the list of steps to solve the problem
     */
    public Stack<Node> search(Problem p) 
    {
        H heuristic = new H(p);
        return searchQueue.search(p, 
            new PriorityQueue<Node>(100, heuristic));
    }
}
