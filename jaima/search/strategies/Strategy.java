package jaima.search.strategies;

import java.util.*;

import jaima.util.*;
import jaima.search.*;

public abstract class Strategy 
{
    public abstract Stack<Node> search(Problem p);
    
    protected static String NODES_EXPANDED = "nodesExpanded";

    public void clearInstrumentation() {
        Metrics.set(NODES_EXPANDED, 0);
    }
}
