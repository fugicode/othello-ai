package jaima.search.strategies;

import java.util.*;
import jaima.search.*;

public class TreeSQ extends SearchQueue 
{
    protected void addExpandedNodesToFringe(Collection<Node> fringe, 
        Node current, Problem problem) 
    {
        fringe.addAll(current.expand());
    }
    
}
