package jaima.game.strategies;

import static java.lang.Math.*;
import jaima.game.*;
import jaima.util.Metrics;

/**
 * Write a description of class MiniMax here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MiniMax
extends Strategy
{
    int depth;
    Move cache;
    Player player;
    
    public MiniMax(int d)
    {
        super(d);
        depth = d;
    }
    
    public Move choose(State s)
    {
        player = s.toMove();
        cache = null;
        //double v = 
        maxValue(s, depth);
        return cache;
    }
    
    private double maxValue(State s, int d)
    {
        Metrics.increment("Minimax nodes");
        if(s.terminal()) {
            return s.utility(player);
        }
        
        if(d == 0) {
            return s.utility(player);
        }
        
        double v = Double.NEGATIVE_INFINITY;
        
        Move best = null;
        for(Move m : s.actions()) {
            double mv = minValue(s.result(m), d-1);
            if(mv > v) {
                best = m;
                v = mv;
            }
        }
        cache = best;
        return v;
    }
    
    private double minValue(State s, int d)
    {
        Metrics.increment("Minimax nodes");
        if(s.terminal()) {
            return s.utility(player);
        }
        
        if(d == 0) {
            return s.utility(player);
        }
        
        double v = Double.POSITIVE_INFINITY;
        
        for(Move m : s.actions()) {
            v = min(v, maxValue(s.result(m), d-1));
        }
        return v;
    }
}
