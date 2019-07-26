package jaima.search;

import java.util.*;
import java.lang.reflect.*;
import jaima.util.*;
import jaima.search.strategies.*;

/**
 * A simple Class to test whether a problem has a solution.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Solver
{
    /**
     * Test a solution strategy on a particular problem.
     * @param name the name of a particular strategy.  
     * A class with this name must be defined in the strategies package.
     * @param p the problem to which this strategy is applied.
     * @return true if the strategy finds a solution to this problem.
     */
    public static boolean test(Problem p, String name)
    {        
        String className = packageName() + ".strategies." + name;
        Strategy s;
        try {
            Class<?> theClass = Class.forName(className);
            Constructor<?> construct = theClass.getConstructor();
            s = (Strategy) construct.newInstance();
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
        
        return test(p, s);
    }
    
    /**
     * Test a solution strategy on a particular problem.
     * @param name the name of a particular strategy.  
     * A class with this name must be defined in the strategies package.
     * @param p the problem to which this strategy is applied.
     * @return true if the strategy finds a solution to this problem.
     */
    public static boolean test(Problem p, Strategy s)
    {                
        Log.record("Start: " + p.initial());
        Stack<Node> solution = s.search(p);

        if (solution.size() == 0)
        {
            Log.record("Search failed");
            return false;
        }
        
        while (solution.size() > 0)
        {
            Node node = solution.pop();
            if(!Node.reporting.isEmpty())
                System.out.println(node);
        }
        
        Log.record("Search succeeded");
        return true;
    }
    
    public static String packageName()
    {
        Package p = (new Solver()).getClass().getPackage();
        return p.getName();
    }
}
