package othello;

import java.util.*;
import jaima.game.*;

/**
 * Othello is played on an 8x8 grid. The String representation of the board will be as follows, with semicolons defining the rows.
 * Here is the initial state of the board, with empty spaces arbitrarily represented as 0, white tokens as 1, black tokens as 2. 
 * Internally, the columns and rows, and hence, the moves are done in terms of array indices. What's more, the contents of the board
 * are in terms of 0's 1's and 2's. However, the output of the board (via prettyPrint()) will convert the column and row labels to
 * letters, and the 0's to Blanks (¥). 
 * 
 * This algorithm is suitable for board sizes 16 (4x4), 36 (6x6), and the traditional 64 (8x8).
 * 
 * "00000000;00000000;00000000;00012000;00021000;00000000;00000000;00000000;1"
 * 
 *                             We will interperet this string as such:
 *                                  
 *                                  8x8 | A || B || C || D || E || F || G || H |
 *                                  ---------------------------------------------
 *                                  | a || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |
 *                                  ---------------------------------------------
 *                                  | b || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |
 *                                  ---------------------------------------------
 *                                  | c || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |
 *                                  ---------------------------------------------
 *                                  | d || 0 || 0 || 0 || 1 || 2 || 0 || 0 || 0 |
 *                                  ---------------------------------------------
 *                                  | e || 0 || 0 || 0 || 2 || 1 || 0 || 0 || 0 |
 *                                  ---------------------------------------------
 *                                  | f || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |
 *                                  ---------------------------------------------
 *                                  | g || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |
 *                                  ---------------------------------------------
 *                                  | h || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |                              
 *                                
 * 
 * 
 * @author (your name) 
 * @version (a version number or date)
 */
public class Board
extends State
{
    protected int[][] board;
    protected Player[] players;
    protected int height, width;
    protected int size;
    protected int turn = 1;
    protected static String [] boardLabels = { "A", "B", "C", "D", "E", "F", "G", "H" } ;
    private static final String blank = "¥"; // option 8 on Mac

    /**
     * @param s This is the string representation of the board.
     * 
     */
    public Board(String s)
    {
        super(s);

        // strip semicolons
        String [] rows = s.split(";");
        width = rows[0].length();
        height = rows.length - 1;
        size = width * height;
        turn = Integer.parseInt(rows[height]);
        board = new int[width][height];
        for(int r = 0; r < height; r++) {
            for(int c = 0; c < width; c++) {
                board[r][c] = rows[r].charAt(c) - '0';
            }
        }

        if(players == null) {
            players = new Opponent[] {
                new Opponent(2),
                new Opponent(1),
            };
        }
    }

    public String toString()
    {
        String sub = "";

        for(int i=0; i<height; i++)
        {
            for(int j=0; j<width; j++)
            {
                sub += board[i][j];
            }
            sub += ";";
        }

        return sub + turn;
    }

    public String prettyPrint()
    {
        int turner = oppositePlayer(Integer.parseInt(players[turn-1].toString()));
        String pretty = turner + "'s Turn" + "\n";
        pretty += " " + height + "x" + width + " ";
        //Column Labels
        for(int a=0; a<width; a++)
        {
            pretty += "|" + " " + boardLabels[a] + " " + "|";
        }

        pretty += "\n";

        // Board Contents with Row Labels
        for(int i=0; i<height; i++)
        {
            // Dashes. Very nice!
            for(int k=1;k<=(width*5 + 5);k++)
            {
                pretty += "-";
            }
            pretty+= "\n";
            pretty += "|" + " " + boardLabels[i].toLowerCase() + " " + "|";
            for(int j=0; j<width; j++)
            {
                pretty += "|" + " " + board[i][j] + " " + "|";
            }
            pretty += "\n";
        }

        // Replace 0's with elegant characters!
        pretty = pretty.replace("0",blank);
        return pretty;
    }

    public Board clone()
    {
        return new Board(toString());
    }

    public Collection<jaima.game.Move> actions()
    {
        Collection<jaima.game.Move> moves 
        = new ArrayList<jaima.game.Move>();
        Player mover = toMove();
        String sHolder = "";
        int playerHolder;
        Horizontal h = new Horizontal(board, mover);
        Vertical v = new Vertical(board, mover);
        Diagonal d = new Diagonal(board, mover);

        sHolder = mover.toString();
        playerHolder = Integer.parseInt(sHolder);
        // Here we're gonna check for available sandwiches on the board
        // for each player's tokens, one at a time.
        // if these check methods return -1, then there is no sandwich.
        for(int i=0; i<height; i++)
        {
            for(int j=0; j<width; j++)
            {
                int holder;
                int count = 0;

                // If we're on the current player's token
                if(board[i][j] == playerHolder)
                {
                    /**
                     * Checking Horizontally
                     */

                    holder = h.checkLeft(board, i, j);
                    if(holder >= 0)
                    {
                        CheckLeft:
                        for(int c=0; c<height; c++)
                        {
                            for(int w=0; w<width; w++)
                            {
                                count++;
                                if(holder == count)
                                {
                                    Move m = new Move((Opponent) mover, c, w);
                                    if(!moves.contains(m))
                                    {
                                        moves.add(m);
                                        break CheckLeft;
                                    }
                                }

                            }   
                        }
                    }
                    count = 0;

                    holder = h.checkRight(board, i, j); 
                    if(holder >= 0)
                    {
                        CheckRight:
                        for(int c=0; c<height; c++)
                        {
                            for(int w=0; w<width; w++)
                            {
                                count++;
                                if(holder == count)
                                {
                                    Move m = new Move((Opponent) mover, c, w);
                                    if(!moves.contains(m))
                                    {
                                        moves.add(m);
                                        break CheckRight;
                                    }
                                }
                            }   
                        }
                    }
                    count = 0;

                    /**
                     * Checking Vertically
                     */

                    holder = v.checkAbove(board,i,j);
                    if(holder >= 0)
                    {
                        CheckUp:
                        for(int c=0; c<height; c++)
                        {
                            for(int w=0; w<width; w++)
                            {
                                count++;
                                if(holder == count)
                                {
                                    Move m = new Move((Opponent) mover, c, w);
                                    if(!moves.contains(m))
                                    {
                                        moves.add(m);
                                        break CheckUp;
                                    }
                                }
                            }   
                        }
                    }
                    count = 0;

                    holder = v.checkBelow(board,i,j);
                    if(holder >= 0)
                    {
                        CheckDown:
                        for(int c=0; c<height; c++)
                        {
                            for(int w=0; w<width; w++)
                            {
                                count++;
                                if(holder == count)
                                {
                                    Move m = new Move((Opponent) mover, c, w);
                                    if(!moves.contains(m))
                                    {
                                        moves.add(m);
                                        break CheckDown;
                                    }
                                }
                            }   
                        }
                    }
                    count = 0;

                    /**
                     * Checking Diagonally
                     */

                    holder = d.checkUpRight(board,i,j);
                    if(holder >= 0)
                    {
                        CheckUR:
                        for(int c=0; c<height; c++)
                        {
                            for(int w=0; w<width; w++)
                            {
                                count++;
                                if(holder == count)
                                {
                                    Move m = new Move((Opponent) mover, c, w);
                                    if(!moves.contains(m))
                                    {
                                        moves.add(m);
                                        break CheckUR;
                                    }
                                }
                            }   
                        }
                    }
                    count = 0;

                    holder = d.checkUpLeft(board,i,j);
                    if(holder >= 0)
                    {
                        CheckUL:
                        for(int c=0; c<height; c++)
                        {
                            for(int w=0; w<width; w++)
                            {
                                count++;
                                if(holder == count)
                                {
                                    Move m = new Move((Opponent) mover, c, w);
                                    if(!moves.contains(m))
                                    {
                                        moves.add(m);
                                        break CheckUL;
                                    }
                                }
                            }   
                        }
                    }
                    count = 0;

                    holder = d.checkDownRight(board,i,j);
                    if(holder >= 0)
                    {
                        CheckDR:
                        for(int c=0; c<height; c++)
                        {
                            for(int w=0; w<width; w++)
                            {
                                count++;
                                if(holder == count)
                                {
                                    Move m = new Move((Opponent) mover, c, w);
                                    if(!moves.contains(m))
                                    {
                                        moves.add(m);
                                        break CheckDR;
                                    }
                                }
                            }   
                        }
                    }
                    count = 0;

                    holder = d.checkDownLeft(board,i,j);
                    if(holder >= 0)
                    {
                        CheckDL:
                        for(int c=0; c<height; c++)
                        {
                            for(int w=0; w<width; w++)
                            {
                                count++;
                                if(holder == count)
                                {
                                    Move m = new Move((Opponent) mover, c, w);
                                    if(!moves.contains(m))
                                    {
                                        moves.add(m);
                                        break CheckDL;
                                    }
                                }
                            }   
                        }
                    }
                    count = 0;
                }
            }
        }
        return moves;
    }

    public Board result(jaima.game.Move jgm)
    {
        Move m = (Move) jgm;
        int r = m.row();
        int c = m.col();
        Board b = clone();
        Opponent p = m.mover();
        b.board[r][c] = p.getMark();

        b.sandwichCorrect(r,c);
        b.turn = oppositePlayer(p.getMark());
        String a = b.toString();
        return b;
    }

    /**
     * Here, if there are no moves left that would create a sandwich, then the game is over.
     * All the methods that check if a player token has a sandwich return -1 if there is no
     * sandwich available. If we add up all the times a token can't make a sandwich, and
     * or not.
     */
    public boolean terminal()
    {
        // Player mover = toMove();
        Player mover = players[oppositePlayer(turn) - 1];
        int failSandwichCheck = 0;
        Horizontal h = new Horizontal(board, mover);
        Vertical v = new Vertical(board, mover);
        Diagonal d = new Diagonal(board, mover);

        for(int i=0; i<height; i++)
        {
            for(int j=0; j<width; j++)
            {
                if(board[i][j] !=0  &&
                h.checkLeft(board, i, j) == -1 &&
                h.checkRight(board, i, j) == -1 &&
                v.checkAbove(board, i, j) == -1 &&
                v.checkBelow(board, i, j) == -1 &&
                d.checkDownLeft(board, i, j) == -1 &&
                d.checkDownRight(board, i, j) == -1 &&
                d.checkUpLeft(board, i, j) == -1 &&
                d.checkUpRight(board, i, j) == -1 )

                    failSandwichCheck++;

            }
        }

        if(failSandwichCheck == tokenCount(1) + tokenCount(2)) 
            return true;
        else
            return false;
    }

    public double utility(Player p)
    {
        int curPlayer = Integer.parseInt(p.toString());

        if(curPlayer == Integer.parseInt(players[0].toString())) {
            return -utility(players[1]);
        }

        if(tokenCount(1) > tokenCount(2))
            return 1;
        else if(tokenCount(2) > tokenCount(1))
            return -1;
        else
        return 0;
    }

    public Player toMove()
    {
        String [] rows = toString().split(";");
        int turn = Integer.parseInt(rows[height]);
        turn = oppositePlayer(turn);
        return players[turn-1];
    }

    private int tokenCount(int m)
    {
        int count = 0;
        int holder;
        holder = m;
        for(int i=0; i<height; i++)
        {
            for(int j=0; j<width; j++)
            {
                if (board[i][j] == holder)
                    count++;
            }
        } 
        return count;
    }

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
     * Here we need to evaluate whether a sandwich exists on each new board, and flip the internal line of tokens if so.
     */
    public void sandwichCorrect(int r, int c)
    {
        horizontalEval(r,c);
        verticalEval(r,c);
        diagonalEval(r,c);
    }

    public void horizontalEval(int r, int c)
    {
        int holder = 0;
        int opHolder = 0;

        holder = board[r][c];
        opHolder = oppositePlayer(holder);

        // Check if neighbor to the right is opposite token, and that one to the right is within bounds
        if (c+1 < width  &&
        board[r][c+1] == opHolder )
        {
            // Look for a sandwich
            for(int k=(c+2); k<width; k++)
            {
                if(board[r][k] == 0)
                    break;
                if(board[r][k] == holder)
                {
                    // Houston, we have a sandwich! Now lets fill it by backtracking to the original token
                    for(int l=k; l>=c; l--)
                    {
                        board[r][l] = holder;
                    }
                }
            }
        }

        // Check if neighbor to the left is opposite token, and that one to the right is within bounds
        if (c-1 >= 0  &&
        board[r][c-1] == opHolder )
        {
            // Look for a sandwich
            for(int k=(c-2); k >= 0; k--)
            {
                if(board[r][k] == 0)
                    break;
                if(board[r][k] == holder)
                {
                    // Houston, we have a sandwich! Now lets fill it by backtracking to the original token
                    for(int l=k; l <= c; l++)
                    {
                        board[r][l] = holder;
                    }
                }
            }
        }

    }

    /**
     * Checks and corrects vertical sandwiches
     */
    public void verticalEval(int r, int c)
    {
        int holder = 0;
        int opHolder = 0;
        // Start by going through the whole board to find a player's token, i.e. non-zero
        // For the vertical check, since we're evaluating from up to down,
        // and looking at the token below the current one,
        // we don't need to check the last row

        holder = board[r][c];
        opHolder = oppositePlayer(holder);

        // Check if neighbor downward is opposite token, and that one to the right is within bounds
        // Also that the downward slot has a token in it
        if (r+1 < height  && 
        board[r+1][c] == opHolder)
        {
            // Look for a sandwich
            for(int k=(r+2); k < height; k++)
            {
                if(board[k][c] == 0)
                    break;
                if(board[k][c] == holder)
                {
                    // Houston, we have a sandwich! Now lets fill it by backtracking to the original token
                    for(int l=k; l > r; l--)
                    {
                        board[l][c] = holder;
                    }
                    //System.out.println("Fixing Sandwiches...");
                    //System.out.println(prettyPrint());
                }
            }
        }

        // Check if neighbor upward is opposite token, and that one to the right is within bounds
        // Also that the upward slot has a token in it
        if (r-1 >= 0  && 
        board[r-1][c] == opHolder)
        {
            // Look for a sandwich
            for(int k=(r-2); k >= 0; k--)
            {
                if(board[k][c] == 0)
                    break;
                if(board[k][c] == holder)
                {
                    // Houston, we have a sandwich! Now lets fill it by backtracking to the original token
                    for(int l=k; l <= r; l++)
                    {
                        board[l][c] = holder;
                    }
                }
            }
        }

    }

    /**
     * Checks and corrects diagonal sandwiches. This is separated by diagonal left (first) and diagonal right (second). 
     */
    public void diagonalEval(int r, int c)
    {
        int holder = 0;
        int opHolder = 0;

        holder = board[r][c];
        opHolder = oppositePlayer(holder);
        // DOWN Diagonal Left Check
        // Check if neighbor downward and left is opposite token, and that
        // one down and one to the right is within bounds
        if (r+1 < height  && 
        c-1 > 0 &&
        board[r+1][c-1] == opHolder)
        {
            // Look for a sandwich
            for(int i=2; (r+i) < height && (c-i) >= 0; i++)
            {
                if(board[r+i][c-i] == 0)
                    break;
                if(board[r+i][c-i] == holder)
                {
                    // Houston, we have a sandwich! Now lets fill it by backtracking to the original token
                    for(int l=i; (l+r) > r; l--)
                    {
                        board[r+l][c-l] = holder;
                    }
                    //System.out.println("Fixing Sandwiches...");
                    //System.out.println(prettyPrint());
                }
            }
        }

        // DOWN Diagonal Right Check
        // Start by going through the whole board to find a player's token, i.e. non-zero

        // Check if neighbor downward is opposite token, and that 
        // one down and one to the right is within bounds
        if(r+1 < height && 
        c+1 < width &&
        board[r+1][c+1] == opHolder)
        {
            //look for the other piece of bread
            for(int i = 2; (r+i) < height && (c+i) < width; i++)
            {
                if(board[r+i][c+i] == 0)
                {
                    break;
                }
                if(board[r+i][c+i] == holder)
                {                                
                    //Houston we have a sandwitch
                    for(int j = i; (r+j) > r; j--)
                    {
                        board[r+j][c+j] = holder;                                    
                    }
                    //System.out.println("Fixing Sandwiches...");
                    //System.out.println(prettyPrint());
                }
            }
        }

        // UP Diagonal Left Check
        // Check if neighbor up and left is opposite token, and that
        // one up and one to the left is within bounds
        if (r-1 >= 0  && 
        c-1 >= 0 &&
        board[r-1][c-1] == opHolder)
        {
            // Look for a sandwich
            for(int i=2; (r-i) >= 0 && (c-i) >= 0; i++)
            {
                if(board[r-i][c-i] == 0)
                    break;
                if(board[r-i][c-i] == holder)
                {
                    // Houston, we have a sandwich! Now lets fill it by backtracking to the original token
                    for(int l=i; (l+r) > r; l--)
                    {
                        board[r-l][c-l] = holder;
                    }
                    //System.out.println("Fixing Sandwiches...");
                    //System.out.println(prettyPrint());
                }
            }
        }

        // UP Diagonal Right Check
        // Start by going through the whole board to find a player's token, i.e. non-zero

        // Check if neighbor upward is opposite token, and that 
        // one up and one to the right is within bounds
        if(r-1 >= 0 && 
        c+1 < width &&
        board[r-1][c+1] == opHolder)
        {
            //look for the other piece of bread
            for(int i = 2; (r-i) >=0  && (c+i) < width; i++)
            {
                if(board[r-i][c+i] == 0)
                {
                    break;
                }
                if(board[r-i][c+i] == holder)
                {                                
                    //Houston we have a sandwitch
                    for(int j = i; (r+j) > r; j--)
                    {
                        board[r-j][c+j] = holder;                                    
                    }
                    //System.out.println("Fixing Sandwiches...");
                    //System.out.println(prettyPrint());
                }
            }
        }

    }

    public void ppTerminal()
    {
        System.out.println(prettyPrint());
    }
}
