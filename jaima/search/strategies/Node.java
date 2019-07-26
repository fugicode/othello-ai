package jaima.search.strategies;

import java.util.*;
import jaima.util.*;
import jaima.search.*;

public class Node {
    private Problem problem;
    private State state;
    private Node parent;
    private SearchAction action;
    private int depth;
    private double stepCost, pathCost;
    
    public enum Reports { address, parent, action, state, pretty, depth, 
        stepcost, pathcost, f, g, h };
    public static Set<Reports> reporting = new HashSet<Reports>();
    
    /**
     * Create the root node in a search tree
     * @param s the initial problem state
     */
    public Node(Problem pr, State s) {
        Metrics.increment("Node count");
        // Log.record("New Node with " + s);
        problem = pr;
        state = s;
        parent = null;  // default
        action = null;  // default
        depth = 0;
        stepCost = 0;
        pathCost = 0;
    }

    /**
     * Create a leaf node in a search tree
     * @param p the parent node
     * @param s the problem state
     */
    public Node(Node p, SearchAction a, 
        Problem pr, State s) {
        this(pr, s);
        parent = p;
        action = a;
        depth = parent.getDepth() + 1;
        Metrics.setMax("Max Depth", depth);
    }
    
    public String toString()
    {
        String report = "";
        if (reporting.contains(Reports.address))
            report += super.toString() + "\n";
        if (reporting.contains(Reports.parent) && parent != null)
            report += "Parent: " + parent + "\n";
        if (reporting.contains(Reports.action) && action != null)  
            report += "Action: " + action + "\n";
        if (reporting.contains(Reports.state))  
            report += "State: " + state + "\n";
        if (reporting.contains(Reports.pretty))  
            report += "State:\n" + state.prettyPrint();
        if (reporting.contains(Reports.depth))  
            report += "Depth: " + depth + "\n";
        if (reporting.contains(Reports.stepcost))  
            report += "Step Cost: " + stepCost + "\n";
        if (reporting.contains(Reports.pathcost))  
            report += "Path Cost: " + pathCost + "\n";
        if (reporting.contains(Reports.f))  
            report += "f() = " + getF() + "\n";
        if (reporting.contains(Reports.g))  
            report += "g() = " + getG() + "\n";
        if (reporting.contains(Reports.h))  
            report += "h() = " + getH() + "\n";
        
        return report;
    }
//     
//     public String prettyPrint()
//     {
//         return 
//             "State: " + state.prettyPrint() + "\n"
//             + toString();
//     }

    public int getDepth() {
        return depth;
    }

    public boolean isRootNode() {
        return parent == null;
    }

    public Node getParent() {
        return parent;
    }

    public Stack<Node> getPathFromRoot() {
        Node current = this;
        Stack<Node> list = new Stack<Node>();
        while (!(current.isRootNode())) {
            list.push(current);
            current = current.getParent();
        }
        list.push(current); // take care of root node
        return list;
    }

    public List<Node> expand() {
        State current = getState();
 //       double heuristic = problem.heuristic(current);
        
        List<Node> nodes = new ArrayList<Node>();
        Map<SearchAction, State> successors = problem.successors(current);
        Set<SearchAction> actions = successors.keySet();
        
        for (SearchAction act : actions) {
            State successor = successors.get(act);
            
            Node aNode = new Node(this, act, problem, successor);
            aNode.setAction(act);
            double stepCost = act.cost();
            aNode.setStepCost(stepCost);
            aNode.addToPathCost(stepCost);
            nodes.add(aNode);
        }
        
        return nodes;
    }

    public State getState() {
        return state;
    }

    public void setAction(SearchAction a) {
        action = a;
    }

    public SearchAction getAction() {
        return action;
    }

    public void setStepCost(double s) {
        stepCost = s;
    }

    /**
     * @return Returns the stepCost.
     */
    public double getStepCost() {
        return stepCost;
    }

    public void addToPathCost(double s) {
        pathCost = parent.getPathCost() + s;
    }

    /**
     * @return Returns the pathCost.
     */
    public double getPathCost() {
        return pathCost;
    }

    /**
     * @return the value of function f() as described in the 
     * A* section of Russell & Norvig
     */
    public double getF() {
        return getG() + getH();   // replace this line
    }

    /**
     * @return the path cost
     */
    public double getG() {
        return pathCost;
    }
    
    /**
     * @return the heuristic value
     */
    public double getH()
    {
        return problem.heuristic(state);
    }
}
