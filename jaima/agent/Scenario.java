package jaima.agent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Write a description of class Scenario here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Scenario
extends Thread
{
//	private Environment env;
    protected Collection<Agent> agents;
    
    /**
     * This example will not compile until an environment is instantiated.
     */
    public Scenario()
    {
        // create the environment
//        env = new Environment();

        // initialize the collection of agents
        agents = new ArrayList<Agent>();
//        Sensor[] sensors;
//        Actuator[] actuators;
        
//        Agent smart = new Agent(env, sensors, actuators);
//        agents.add(smart);
    }
    
    /**
     * Start all agents.
     */
    public void run()
    {        
        for (Agent a : agents)
        {
            a.start();
        }
    }
    
    /**
     * Signal all agents to quit, then waits for them to quit, 
     * then returns.
     */
    public void quit()
    {
        for (Agent a : agents)
        {
            a.quit();
        }
        
        for (Agent a : agents)
        {
            try { a.join(1000); }
            catch(Exception e) {
                System.err.println(e);
            }
        }
    }
}
