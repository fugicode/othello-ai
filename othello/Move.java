package othello;

import jaima.game.Player;

/**
 * Write a description of class TTTMove here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Move
extends jaima.game.Move
{
    private Opponent mover;
    private int row, col;
    protected static String [] boardLabels = { "A", "B", "C", "D", "E", "F", "G", "H" } ;

    public Move(Opponent o, int r, int c)
    {
        super();
        mover = o;
        row = r;
        col = c;
    }

    public Opponent mover()
    {
        return mover;
    }

    public int row()
    {
        return row;
    }

    public int col()
    {
        return col;
    }

    public String toString()
    {
        return mover.toString() + row + col;
    }

    public String prettyPrint()
    {
        return "Place an " + mover 
        + " in " + boardLabels[row].toLowerCase()
        + ", " + boardLabels[col];
    }
}
