package jaima.game.strategies;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import jaima.game.State;
import jaima.game.Move;

/**
 * Write a description of class Ask here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ask
extends Strategy
{
    private Scanner in;
    private PrintStream out;
    private boolean newbie;
    private String name;
    
    public Ask(InputStream i, PrintStream o, String n)
    {
        super();
        in = new Scanner(i);
        out = o;
        newbie = true;
        name = n;
    }
    
    public Ask(String n)
    {
        this(System.in, System.out, n);
    }
    
    public Move choose(State s)
    {
//         out.println(s.prettyPrint());
        
        Collection<Move> moves = s.actions();
        
        if (moves.size() == 0) {
            out.println("No moves available.");
            return null;
        }
        
        if(newbie) {
            out.println("Press Enter for help.\n");
        }
        
        ArrayList<Move> matches;
        do {
            out.print("Your move? ");
            String input = in.nextLine().trim();
            String choice = input.toUpperCase();
            
            matches = new ArrayList<Move>();

            if(input.length() == 0) {
                out.println("Board is:");
                out.println(s.prettyPrint());
                out.println("Valid moves are: ");
                printMoves(moves);
                continue;
            }

            for(Move m : moves) {
                if(m.toString().toUpperCase().contains(choice)) {
                        matches.add(m);
                }
            }

            if(matches.size() == 0) {
                out.println("\n'" + input + "' is not a valid move.\n");
                out.println("Valid moves are: ");
                printMoves(moves);
                continue;
            }

            if(matches.size() > 1) {
                out.println("\n'" + input + "' is ambiguous.\n");
                out.println("Matching moves are: ");
                printMoves(matches);
            }
        } while(matches.size() != 1);
        
        newbie = false;
        return matches.get(0);
    }
    
    private void printMoves(Collection<Move> moves)
    {
        for(Move move : moves) {
            out.print("    " + move);
            if(!move.prettyPrint().equals(move.toString()))
                out.print(": " + move.prettyPrint());
            out.println();
        }
        out.println();
    }
    
    public String toString()
    {
        return name;
    }
}
