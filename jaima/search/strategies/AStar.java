package jaima.search.strategies;

import java.util.*;
import jaima.util.*;
import jaima.search.*;

/**
 * breadth-first search
 */
public class AStar
extends Strategy
{
    SearchQueue searchQueue;
    
    /**
     * create a queue-based search
     * @param sq the type of search queue
     */
    public AStar(SearchQueue sq) 
    {
        Log.record("Strategy: A* Search");
        searchQueue = sq;
    }

    /**
     * search for a solution
     * @param p the problem instance
     * @return the list of steps to solve the problem
     */
    public Stack<Node> search(Problem p) 
    {
        F combined = new F();
        return searchQueue.search(p, new PriorityQueue<Node>(100, combined));
    }
}
