package jaima.game.strategies;

import static java.lang.Math.*;
import jaima.game.*;
import jaima.util.Metrics;

/**
 * Write a description of class AlphaBeta here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AlphaBeta
extends Strategy
{
    private int depth;
    private Move cache;
    private Player player;
    
    public AlphaBeta(int d)
    {
        super(d);
        depth = d;
    }
    
    public Move choose(State s)
    {
        player = s.toMove();
        cache = null;
        // double v = 
        maxValue(s, 
            Double.NEGATIVE_INFINITY, 
            Double.POSITIVE_INFINITY, depth);
        return cache;
    }
    
    private double maxValue(State s, double a, double b, int d)
    {
        Metrics.increment("Alpha-Beta nodes");
        if(s.terminal()) {
            return s.utility(player);
        }
        
        if(d == 0) {
            return s.utility(player);
        }
        
        double v = Double.NEGATIVE_INFINITY;
        
        Move best = null;
        for(Move m : s.actions()) {
            double mv = minValue(s.result(m), a, b, d-1);
            if(mv > v) {
                best = m;
                v = mv;
            }
            if(v >= b)
                return b;
            a = max(a, v);
        }
        cache = best;
        return v;
    }
    
    private double minValue(State s, double a, double b, int d)
    {
        Metrics.increment("Alpha-Beta nodes");
        if(s.terminal()) {
            return s.utility(player);
        }
        
        if(d == 0) {
            return s.utility(player);
        }
        
        double v = Double.POSITIVE_INFINITY;
        
        for(Move m : s.actions()) {
            v = min(v, maxValue(s.result(m), a, b, d-1));
            if(v <= a)
                return v;
                b = min(b, v);
        }
        return v;
    }
}
