package jaima.search.strategies;

import java.util.*;
import jaima.util.*;
import jaima.search.*;

/**
 * Write a description of class RDFS here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class R2
extends Strategy
{
    public R2()
    {
        Log.record("Strategy: RDFS w/loop-checking");
    }
    
    /**
     * search for a solution
     * @param p the problem instance
     * @return the list of steps to solve the problem
     */
    public Stack<Node> search(Problem p) 
    {
        Node root = new Node(p, p.initial());
        Stack<Node> solution = search(p, root);
        if (solution == null)
            solution = new Stack<Node>();
        return solution;
    }

    public Stack<Node> search(Problem p, Node root)  
    {
//         Log.record("Searching: " + root.getState());
        if (alreadySeen(root))
        {
            return null;
        }
        
        if (p.isGoal(root.getState()))
        {
            return root.getPathFromRoot(); 
        }
        
//         Log.record("Expanding node: " + root.getState());
        for (Node node : root.expand())
        {
            Stack<Node> solution = search(p, node);
            if (solution != null)
            {
                return solution;
            }
        }
        
        return null;
    }
   
    private boolean alreadySeen(Node node) {
        State current = node.getState();
        Node ancestor = node.getParent();
        while (ancestor != null)
        {
            State prior = ancestor.getState();
            if (current.equals(prior))
            {
                return true;
            }
            ancestor = ancestor.getParent();
        }
        return false;
    }
}
