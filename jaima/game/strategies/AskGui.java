package jaima.game.strategies;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import jaima.util.Timer;
import jaima.game.State;
import jaima.game.Move;

/**
 * Write a description of class Ask here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AskGui
extends Strategy
{
    private JTextField in;
    private JTextArea out;
    private boolean newbie;
    private String name;
    private boolean readyToRead;
    private boolean readCompleted;
    
    public AskGui(JTextField i, JTextArea o, String n)
    {
        super();
        in = i;
        out = o;
        newbie = true;
        name = n;
        readyToRead = false;
        readCompleted = false;
    }
    
    private void print(String s) {
        out.append(s);
    }
    
    private void println(String s) {
        out.append(s + "\n");
    }
    
    private void println() {
        out.append("\n");
    }
    
    public boolean isReady() {
        return readyToRead;
    }
    
    public void signalCompletion() {
        readCompleted = true;
    }
    
    private String nextLine() {
        String s = "Invalid";
        readCompleted = false;
        readyToRead = true;
        do {
            Thread.yield();
        } while(!readCompleted);
        readyToRead = false;
        return s;
    }
    
    public Move choose(State s)
    {
//         println(s.prettyPrint());
        
        Collection<Move> moves = s.actions();
        
        if (moves.size() == 0) {
            println("No moves available.");
            return null;
        }
        
        if(newbie) {
            println("Press Enter for help.\n");
        }
        
        ArrayList<Move> matches;
        do {
            print("Your move? ");
            String input = nextLine().trim();
            String choice = input.toUpperCase();
            
            matches = new ArrayList<Move>();

            if(input.length() == 0) {
                println("Board is:");
                println(s.prettyPrint());
                println("Valid moves are: ");
                printMoves(moves);
                continue;
            }

            for(Move m : moves) {
                if(m.toString().toUpperCase().contains(choice)) {
                        matches.add(m);
                }
            }

            if(matches.size() == 0) {
                println("\n'" + input + "' is not a valid move.\n");
                println("Valid moves are: ");
                printMoves(moves);
                continue;
            }

            if(matches.size() > 1) {
                println("\n'" + input + "' is ambiguous.\n");
                println("Matching moves are: ");
                printMoves(matches);
            }
        } while(matches.size() != 1);
        
        newbie = false;
        return matches.get(0);
    }
    
    private void printMoves(Collection<Move> moves)
    {
        for(Move move : moves) {
            print("    " + move);
            if(!move.prettyPrint().equals(move.toString()))
                print(": " + move.prettyPrint());
            println();
        }
        println();
    }
    
    public String toString()
    {
        return name;
    }
}
