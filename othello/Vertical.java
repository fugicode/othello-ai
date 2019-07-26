package othello;
import jaima.game.*;

/**
 * This class checks if vertical moves will create a sandwich on the gameboard.
 * 
 * @author (Phil Knock) 
 * @version (10.16.14)
 */
public class Vertical
{
    private int hLimit, wLimit, currPlayer;

    /**
     * Constructor for objects of class Vertical
     * 
     * @param Hboard    An input gameboard to be checked
     * @param player        Current player being checked 
     */
    public Vertical(int[][] Hboard, Player player)
    {
        // Defines Height and Width in terms of the boardSize input
        hLimit = Hboard.length;
        wLimit = hLimit;
        String cPlayer = player.toString();
        currPlayer = Integer.parseInt(cPlayer);
    }

    /**
     * Checks below a given token to find a sandwich
     * 
     * @param checkHeight   Height index of the token to be evaluated
     * @param checkWidth    Width index of the token to be evaluated
     * @return      
     */
    public int checkBelow(int[][] Hboard, int checkHeight, int checkWidth)
    {
        int holder, opHolder;
        boolean upCheck;
        // If indexOutput is returned as -1, it will later
        // be disregarded
        int indexOutput = -1;

        holder = Hboard[checkHeight][checkWidth];
        opHolder = oppositePlayer(currPlayer);

        // Check that one below is within bounds, 
        // and if neighbor below is opposite token
        if (checkHeight+2 < hLimit  &&
        Hboard[checkHeight+1][checkWidth] == opHolder)
        {
            upCheck = false;
            // Look for a place to top the sandwich
            for(int k=(checkHeight+2); k<wLimit && !upCheck; k++)
            {
                if(Hboard[k][checkWidth] == 0)
                {
                    indexOutput = index(k,checkWidth);
                    upCheck = true;
                }
                else if (Hboard[k][checkWidth] == holder)
                    upCheck = true;
            }
        }

        return indexOutput;
    }

    /**
     * Checks above a a given token to find a sandwich
     * 
     * @param checkHeight   Height index of the token to be evaluated
     * @param checkWidth    Width index of the token to be evaluated
     * @return      
     */
    public int checkAbove(int[][] Hboard, int checkHeight, int checkWidth)
    {
        int holder, opHolder;
        boolean upCheck;
        // If indexOutput is returned as -1, it will later
        // be disregarded
        int indexOutput = -1;

        holder = Hboard[checkHeight][checkWidth];
        opHolder = oppositePlayer(currPlayer);

        // Check that one above is within bounds, 
        // and if neighbor above is opposite token
        if (checkHeight-2 >= 0  &&
        Hboard[checkHeight-1][checkWidth] == opHolder)
        {
            upCheck = false;
            // Look for a place to top the sandwich
            for(int k=(checkHeight-2); k>=0 && !upCheck; k--)
            {
                if(Hboard[k][checkWidth] == 0)
                {
                    indexOutput = index(k,checkWidth);
                    upCheck = true;
                }
                else if (Hboard[k][checkWidth] == holder)
                    upCheck = true;
            }
        }

        return indexOutput;
    }

    /**
     * Determines the opposite Player
     */
    private int oppositePlayer(int input)
    {
        int output = 0;
        if(input == 1)
            output = 2;
        else if(input == 2)
            output = 1;
        return output;
    }

    /**
     * Determines index based on row and column
     */
    private int index(int r, int c)
    {
        return (r * hLimit) + (c + 1) ;
    }
}
