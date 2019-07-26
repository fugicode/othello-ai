package jaima.game.strategies;

import jaima.game.*;

public abstract class Strategy 
{
    private String label;
    
    public abstract Move choose(State s);
    
    public Strategy() {
        String[] path = super.toString().split("\\.");
        String[] label = path[path.length - 1].split("@");
        this.label = label[0];
    }
    
    public Strategy(int d) {
        this();
        label += "(" + d + ")";
    }

    public String toString()
    {
        return label;
    }
}
