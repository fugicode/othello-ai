package jaima.search.strategies;

import java.util.*;
import jaima.util.*;
import jaima.search.*;

public class GraphSQ 
extends SearchQueue 
{
    private Set<State> closed;
    private Map<State, Node> open;
    
    public GraphSQ()
    {
        closed = new TreeSet<State>();
        open = new TreeMap<State, Node>();
    }
    
    protected void addExpandedNodesToFringe(Collection<Node> fringe, 
        Node current, Problem problem) 
    {
//         Metrics.setMax("Open List size:", open.size());
        open.remove(current.getState());
        
        closed.add(current.getState());
        Metrics.setMax("Closed List size:", closed.size());
        
        Collection<Node> nodes = current.expand();
        for(Node node : nodes) 
        {
            if (onClosed(node)) 
                continue;

            Node competitor = checkOpen(node);
            if (competitor == null) {
                fringe.add(node);
                open.put(node.getState(), node);
                continue;
            }
            
            if(node.getG() >= competitor.getG()) {
                continue;
            }
            
            fringe.remove(competitor);
            fringe.add(node);
            open.put(node.getState(), node);
        }
    }
    
    private boolean onClosed(Node node) {
        State s = node.getState();
        return closed.contains(s);
    }
    
    private Node checkOpen(Node node) {
        State s = node.getState();
        return open.get(s);
    }
}
