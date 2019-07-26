package jaima.search.strategies;

import java.util.*;
import jaima.util.*;
import jaima.search.*;

/**
 * @author Ravi Mohan
 *  
 */
public abstract class SearchQueue
{
    public Stack<Node> search(Problem problem, Collection<Node> fringe) 
    {
        String[] longName = this.getClass().getName().split("\\.");
        String shortName = longName[longName.length - 1];
        Log.record("Using " + shortName);
        fringe.add(new Node(problem, problem.initial()));
        Node node;
        
        while (!(fringe.isEmpty())) {
            Metrics.setMax("Max. Queue Length", fringe.size());
            node = remove(fringe);
//             Log.record("Examining node: " + node.getState()
//                 + "(" 
//                 + " f=" + (int)node.getF()
//                 + " g=" + (int)node.getG()
//                 + " h=" + (int)node.getH()
//                 + ")"
//                 );
            if (problem.isGoal(node.getState())) {
                return node.getPathFromRoot();
            }
            addExpandedNodesToFringe(fringe, node, problem);
        }
        
        //Empty Collection indicates Failure
        Log.record("Search Failed.");
        return new Stack<Node>();
    }
    
    /**
     * remove one node from a collection
     * @return the node removed, or null if the collection is empty
     */
    private Node remove(Collection<Node> nodes)
    {
        if(nodes.getClass().getName().contains("Stack"))
        {
            return ((Stack<Node>)nodes).pop();
        }
        
        if(nodes.getClass().getName().contains("Queue"))
        {
            return ((PriorityQueue<Node>)nodes).poll();
        }
        
        // remove the first node from the list
        // even though this looks like a loop, 
        // the body executes at most once
        for (Node n : nodes)
        {
            nodes.remove(n);
            return n;
        }
        
        // if the collection is empty
        return null;
    }
    
    /**
     * This is the Tree-Search algorithm described on p. 72
     */
    protected abstract void addExpandedNodesToFringe(
        Collection<Node> fringe, Node node, Problem problem) ;
}
