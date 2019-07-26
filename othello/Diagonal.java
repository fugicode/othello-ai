package othello;
import jaima.game.*;

/**
 * This class checks if diagonal moves will create a sandwich on the gameboard.
 * 
 * @author (Phil Knock) 
 * @version (10.16.14)
 */
public class Diagonal
{
    private int hLimit, wLimit, currPlayer;

    /**
     * Constructor for objects of class Diagonal
     * 
     * @param Hboard    An input gameboard to be checked
     * @param player        Current player being checked 
     */
    public Diagonal(int[][] Hboard, Player player)
    {
        // Defines Height and Width in terms of the boardSize input
        hLimit = Hboard.length;
        wLimit = hLimit;
        String cPlayer = player.toString();
        currPlayer = Integer.parseInt(cPlayer);
    }

    /**
     * Checks up and to the right of a given token to find a sandwich
     * 
     * @param checkHeight   Height index of the token to be evaluated
     * @param checkWidth    Width index of the token to be evaluated
     * @return      
     */
    public int checkUpRight(int[][] Hboard, int checkHeight, int checkWidth)
    {
        int holder, opHolder;
        boolean upRightCheck;
        // If indexOutput is returned as -1, it will later
        // be disregarded
        int indexOutput = -1;

        holder = Hboard[checkHeight][checkWidth];
        opHolder = oppositePlayer(currPlayer);

        // Check that two above is within bounds, 
        // two to the right is within bounds,
        // and if that neighbor is opposite token
        if (checkHeight-2 >= 0  &&
        checkWidth +2 < wLimit &&
        Hboard[checkHeight-1][checkWidth+1] == opHolder)
        {
            upRightCheck = false;
            // Look for a place to top the sandwich
            for(int k=2; (checkHeight-k) >= 0 && (checkWidth+k) < wLimit && !upRightCheck; k++)
            {
                if(Hboard[checkHeight-k][checkWidth+k] == 0)
                {
                    indexOutput = index(checkHeight-k,checkWidth+k);
                    upRightCheck = true;
                }
                else if(Hboard[checkHeight-k][checkWidth+k] ==holder)
                    upRightCheck = true;
            }
        }

        return indexOutput;
    }

    /**
     * Checks up and to the left of a a given token to find a sandwich
     * 
     * @param checkHeight   Height index of the token to be evaluated
     * @param checkWidth    Width index of the token to be evaluated
     * @return      
     */
    public int checkUpLeft(int[][] Hboard, int checkHeight, int checkWidth)
    {
        int holder, opHolder;
        boolean upLeftCheck;
        // If indexOutput is returned as -1, it will later
        // be disregarded
        int indexOutput = -1;

        holder = Hboard[checkHeight][checkWidth];
        opHolder = oppositePlayer(currPlayer);

        // Check that two above is within bounds, 
        // two to the left is within bounds,
        // and if that neighbor is opposite token
        if (checkHeight-2 >= 0  &&
        checkWidth -2 >= 0 &&
        Hboard[checkHeight-1][checkWidth-1] == opHolder)
        {
            upLeftCheck = false;
            // Look for a place to top the sandwich
            for(int k=2; (checkHeight-k) >= 0 && (checkWidth-k) >= 0 && !upLeftCheck; k++)
            {
                if(Hboard[checkHeight-k][checkWidth-k] == 0)
                {
                    indexOutput = index(checkHeight-k,checkWidth-k);
                    upLeftCheck = true;
                }
                else if(Hboard[checkHeight-k][checkWidth-k] == holder)
                    upLeftCheck = true;
            }
        }

        return indexOutput;
    }
    
        /**
     * Checks below and to the right of a given token to find a sandwich
     * 
     * @param checkHeight   Height index of the token to be evaluated
     * @param checkWidth    Width index of the token to be evaluated
     * @return      
     */
    public int checkDownRight(int[][] Hboard, int checkHeight, int checkWidth)
    {
        int holder, opHolder;
        boolean downRightCheck;
        // If indexOutput is returned as -1, it will later
        // be disregarded
        int indexOutput = -1;

        holder = Hboard[checkHeight][checkWidth];
        opHolder = oppositePlayer(currPlayer);

        // Check that two below is within bounds, 
        // two to the right is within bounds,
        // and if that neighbor is opposite token
        if (checkHeight+2 < hLimit  &&
        checkWidth+2 < wLimit &&
        Hboard[checkHeight+1][checkWidth+1] == opHolder)
        {
            downRightCheck = false;
            // Look for a place to top the sandwich
            for(int k=2; (checkHeight+k) < hLimit && (checkWidth+k) < wLimit && !downRightCheck; k++)
            {
                if(Hboard[checkHeight+k][checkWidth+k] == 0)
                {
                    indexOutput = index(checkHeight+k,checkWidth+k);
                    downRightCheck = true;
                }
                else if(Hboard[checkHeight+k][checkWidth+k] == holder)
                    downRightCheck = true;
            }
        }

        return indexOutput;
    }

    /**
     * Checks below and to the left of a a given token to find a sandwich
     * 
     * @param checkHeight   Height index of the token to be evaluated
     * @param checkWidth    Width index of the token to be evaluated
     * @return      
     */
    public int checkDownLeft(int[][] Hboard, int checkHeight, int checkWidth)
    {
        int holder, opHolder;
        boolean downRightCheck;
        // If indexOutput is returned as -1, it will later
        // be disregarded
        int indexOutput = -1;

        holder = Hboard[checkHeight][checkWidth];
        opHolder = oppositePlayer(currPlayer);

        // Check that two below is within bounds, 
        // two to the left is within bounds,
        // and if that neighbor is opposite token
        if (checkHeight+2 < hLimit  &&
        checkWidth -2 >= 0 &&
        Hboard[checkHeight+1][checkWidth-1] == opHolder)
        {
            downRightCheck = false;
            // Look for a place to top the sandwich
            for(int k=2; (checkHeight+k) < hLimit && (checkWidth-k) >= 0 && !downRightCheck; k++)
            {
                if(Hboard[checkHeight+k][checkWidth-k] == 0)
                {
                    indexOutput = index(checkHeight+k,checkWidth-k);
                    downRightCheck = true;
                }
                else if(Hboard[checkHeight+k][checkWidth-k] == holder)
                    downRightCheck = true;
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
