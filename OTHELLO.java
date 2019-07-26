
import jaima.util.Metrics;
import jaima.game.Player;
import jaima.game.strategies.*;
import othello.*;

/**
 * Write a description of class OTHELLO here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OTHELLO
{
    private OTHELLO() { }

    public static void main(String[] args)
    {
        System.out.print("\f");
        Metrics.clearAll();

        Strategy[] strategies = {
                //             new Guess(),
                //   new Ask("Phil"),
//                 new MiniMax(3),
               //       new AlphaBeta(5),
                //      new Guess(),
     new Ask("James"),
                 //    new MiniMax(6),
                new AlphaBeta(9),
            };

        Board board = new Board("000000;000000;001200;002100;000000;000000;1");
        int turn = 0;
        int failCount = 0;
        Player p = board.toMove();

        while(!board.terminal()) {       
            Strategy s = strategies[turn];
            jaima.game.Move m = s.choose(board);
            if(m == null && failCount < 2)
            {
                turn = 1 - turn; 
                failCount ++;
            }
            else if (m != null){
                System.out.println(board.prettyPrint());
                failCount = 0;
                board = board.result(m);
                String yes = board.toString();
                System.out.println(strategies[turn] + " is Moving......." + "\n");
                System.out.println(m.prettyPrint());
                turn = 1 - turn;      
            }
            else
                break;
        } 
        System.out.println(board.prettyPrint());

        String announce = "Game result: tie";
        if(board.utility(p) > 0) {
            announce = "Winner is " + strategies[0];
        } else if(board.utility(p) < 0) {
            announce = "Winner is " + strategies[1];
        }
        System.out.println(announce);
        System.out.println(Metrics.getAll());
    }
}
