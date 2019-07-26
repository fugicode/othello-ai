package jaima.search.strategies;

import java.util.*;
import jaima.util.*;
import jaima.search.*;

/**
 * Depth-Limited Search
 * 
 * @author William H. Hooper
 * @version 2007-01-20
 */
public class DLS
extends Strategy
{
    int bound;

    public DLS(int b)
    {
        Log.record("Strategy: Depth Limited Search("
            + b + ")");
        bound = b;
    }

    /**
     * search for a solution
     * @param p the problem instance
     * @return the list of steps to solve the problem
     */
    public Stack<Node> search(Problem p) 
    {
        Node root = new Node(p, p.initial());
        Stack<Node> solution = search(p, root, bound);
        if (solution != null)
            return solution;

        return new Stack<Node>();
    }

    public Stack<Node> search(Problem p, Node root, int depth) 
    {
        if (p.isGoal(root.getState()))
            return root.getPathFromRoot();   

        if(depth <= 0)
            return null;

        if (alreadySeen(root))
            return null;

        for (Node node : root.expand())
        {
            Stack<Node> solution 
            = search(p, node, depth - 1);
            if (solution != null)
                return solution;
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
                return true;

            ancestor = ancestor.getParent();
        }
        return false;
    }
}
