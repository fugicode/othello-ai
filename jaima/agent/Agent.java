package jaima.agent;

import java.util.ArrayList;

/**
 * Write a description of class Agent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Agent
extends Thread
{
    protected Environment world;
    protected Sensor[] sensors;
    protected Actuator[] actuators;
    private ArrayList<Percept> percepts;
    private boolean isAlive;

    /**
     * Create an Agent
     * @param e the environment, possibly shared with other agents
     * @param s the available sensors
     * @param a the available actuators
     */
    public Agent(Environment e, Sensor[] s, Actuator[] a)
    {
        world = e;
        sensors = s;
        actuators = a;
        percepts = new ArrayList<Percept>();
    }
    
    /**
     * A template for a running agent.  
     * DO NOT USE THIS without modification!
     */
     public final void run()
     {
         isAlive = true;
         while(isAlive)
         {
    		act();
         }
     }
    
    public void act()
    {
    	for(Sensor s : sensors) {
    		percepts.add(world.show(s));
    	}

//    	for(Actuator a : actuators) {
//    		world.execute(a, new Action());
//    	}
    }
    
    /**
     * signal the agent to exit its run() method
     */
    public final void quit()
    {
        isAlive = false;
    }
}
