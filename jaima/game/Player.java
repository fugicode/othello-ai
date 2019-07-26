package jaima.game;


/**
 * Player - represents one Player in a multi-player game.
 * 
 * @author William H. Hooper
 * @version 2012-09-27
 */
public abstract class Player
{
    /**
     * Define a string conversion that identifies
     * each distinct player.
     */
    public abstract String toString();
    
    public boolean equals(Player p) {
        return toString().equals(p.toString());
    }
    
    /**
     * optionally, define a more informative description
     */
    public String prettyPrint() {
        return toString();
    }
}
