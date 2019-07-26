package jaima.search.strategies;
import java.util.*;

/**
 * The comparator for Branch and Bound
 */
public class G
implements Comparator<Node>
{
    public int compare(Node a, Node b)
    {
        double aCost = a.getG();
        double bCost = b.getG();
        
        return Double.compare(aCost, bCost);
    }
}
