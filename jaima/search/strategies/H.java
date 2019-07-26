package jaima.search.strategies;

import java.util.*;
import jaima.search.*;

/**
 * The comparator for Greedy
 */
public class H
implements Comparator<Node>
{
    Problem problem;
    
    public H(Problem p)
    {
        problem = p;
    }
    
    public int compare(Node a, Node b)
    {
        double ha = a.getH();
        double hb = b.getH();
        return Double.compare(ha, hb);
    }
}
