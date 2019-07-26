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
public class RDFS
extends Strategy
{
    public RDFS()
    {
        Log.record("Strategy: Recursive Depth-First Search");
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
}
