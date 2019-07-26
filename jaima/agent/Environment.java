package jaima.agent;

//import java.util.*;

/**
 * The Environment is a passive receptacle for objects.
 * Agents perceive the environment through their sensors,
 * and change the environment by their actions.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Environment
{
	/**
     * Look around! 
     * @param s the sensor perceiving the environment.
     * @return a view of the environment from that sensor.
     */
    public abstract Percept show(Sensor s);
    
    /**
     * Do something!
     * @param a the actuator performing the action
     * @param n the specific action performed
     */
    public abstract void execute(Actuator r, Action n);
}
