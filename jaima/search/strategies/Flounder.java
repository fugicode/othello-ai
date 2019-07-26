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
public class Flounder
extends Strategy
{
    Random gen;
    
    public Flounder()
    {
        Log.record("Strategy: flounder");
        gen = new Random();
    }
    
    /**
     * search for a solution
     * @param p the problem instance
     * @return the list of steps to solve the problem
     */
    public Stack<Node> search(Problem p) 
    {
        Node drift = new Node(p, p.initial());
        
        while (!p.isGoal(drift.getState()))
        {
            Object[] choices = drift.expand().toArray();
            int n = choices.length;
            if(n == 0)
                return (new Stack<Node> ());
                
            int guess = gen.nextInt(n);
            drift = (Node) choices[guess];
        }
           
        return drift.getPathFromRoot();   
    }
}
