package jaima.search.strategies;
import java.util.*;

/**
 * The comparator for Branch and Bound
 */
public class F
implements Comparator<Node>
{
    public int compare(Node a, Node b)
    {
        double aCost = a.getF();
        double bCost = b.getF();
        
        return Double.compare(aCost, bCost);
    }
}
