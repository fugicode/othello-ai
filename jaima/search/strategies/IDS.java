package jaima.search.strategies;

import java.util.*;
import jaima.util.*;
import jaima.search.*;

/**
 * Iterative Deepening Depth-First Search
 * 
 * @author William H. Hooper
 * @version 2012-09-18
 */
public class IDS
extends Strategy
{
    int min, max;

    public IDS()
    {
        this(1, Integer.MAX_VALUE);
    }

    public IDS(int max)
    {
        this(1, max);
    }

    public IDS(int min, int max)
    {
        Log.record("Strategy: Iterative Deepening"
            + " Depth-First Search");
        this.min = min;
        this.max = max;
    }

    /**
     * search for a solution
     * @param p the problem instance
     * @return the list of steps to solve the problem
     */
    public Stack<Node> search(Problem p) 
    {
//        Node root = new Node(p, p.initial());    
        for(int level = min; level <= max; level++) {
            DLS interim = new DLS(level);
            Stack<Node> solution = interim.search(p);
            if(solution.size() > 0)
                return solution;

            if(reporting.contains(Reports.level))
                Log.record("DLS (" + level + ") failed.");
        }
        
        // search failed
        return new Stack<Node>();
    }

    public enum Reports { level };
    public static Set<Reports> reporting = new HashSet<Reports>();
}
