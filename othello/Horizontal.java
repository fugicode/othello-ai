package othello;
import jaima.game.*;

/**
 * This class checks if horizontal moves will create a sandwich on the gameboard.
 * 
 * @author (Phil Knock) 
 * @version (10.16.14)
 */
public class Horizontal
{
    private int hLimit, wLimit, currPlayer;

    /**
     * Constructor for objects of class Horizontal
     * 
     * @param Hboard    An input gameboard to be checked
     * @param player        Current player being checked 
     */
    public Horizontal(int[][] Hboard, Player player)
    {
        // Defines Height and Width in terms of the boardSize input
        hLimit = Hboard.length;
        wLimit = hLimit;
        String cPlayer = player.toString();
        currPlayer = Integer.parseInt(cPlayer);
    }

    /**
     * Checks to the right of a given token to find a sandwich
     * 
     * @param checkHeight   Height index of the token to be evaluated
     * @param checkWidth    Width index of the token to be evaluated
     * @return      
     */
    public int checkRight(int[][] Hboard, int checkHeight, int checkWidth)
    {
        int holder, opHolder;
        boolean rightCheck;
        // If indexOutput is returned as -1, it will later
        // be disregarded
        int indexOutput = -1;

        holder = Hboard[checkHeight][checkWidth];
        opHolder = oppositePlayer(currPlayer);

        // Check that two to the right is within bounds, 
        // and if neighbor to the right is opposite token
        if (checkWidth+2 < wLimit  &&
        Hboard[checkHeight][checkWidth+1] == opHolder)
        {
            rightCheck = false;
            // Look for a place to top the sandwich
            for(int k=(checkWidth+2); k<wLimit && !rightCheck; k++)
            {
                if(Hboard[checkHeight][k] == 0)
                {
                    indexOutput = index(checkHeight,k);
                    rightCheck = true;
                }
                else if (Hboard[checkHeight][k] == holder)
                    rightCheck = true;

            }
        }

        return indexOutput;
    }

    /**
     * Checks to the left of a given token to find a sandwich
     * 
     * @param checkHeight   Height index of the token to be evaluated
     * @param checkWidth    Width index of the token to be evaluated
     * @return      
     */
    public int checkLeft(int[][] Hboard, int checkHeight, int checkWidth)
    {
        int holder, opHolder;
        boolean leftCheck;
        // If indexOutput is returned as -1, it will later
        // be disregarded
        int indexOutput = -1;

        holder = Hboard[checkHeight][checkWidth];
        opHolder = oppositePlayer(currPlayer);

        // Check that two to the left is within bounds, 
        // and if neighbor to the left is opposite token
        if (checkWidth-2 >= 0  &&
        Hboard[checkHeight][checkWidth-1] == opHolder)
        {
            leftCheck = false;
            // Look for a place to top the sandwich
            for(int k=(checkWidth-2); k>=0 && !leftCheck; k--)
            {
                if(Hboard[checkHeight][k] == 0)
                {
                    indexOutput = index(checkHeight,k);
                    leftCheck = true;
                }
                else if(Hboard[checkHeight][k] == holder)
                    leftCheck = true;
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
