package othello;
import jaima.game.*;

/**
 * Write a description of class Opponent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Opponent
extends Player
{
    int mark;
    
    /**
     * Choose a mark
     * @param m 1 or 2
     */
    public Opponent(int m)
    {
        mark = m;
    }
    
    public Opponent(String s)
    {
        mark = Integer.parseInt(s.substring(0,1));
    }
    
    public int getMark()
    {
        return mark;
    }
    
    public String toString()
    {
        return mark + "";
    }
}
