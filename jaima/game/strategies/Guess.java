package jaima.game.strategies;

import java.util.*;
import jaima.game.*;

/**
 * Write a description of class Random here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Guess
extends Strategy
{
    private Random gen;
    
    public Guess()
    {
        super();
        gen = new Random();
    }
    
    public Move choose(State s)
    {
        Object[] moves = s.actions().toArray();
        
        if (moves.length == 0)
            return null;
        
        int choice = gen.nextInt(moves.length);
        return (Move) moves[choice];
    }
}
