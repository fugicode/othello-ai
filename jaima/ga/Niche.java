package jaima.ga;

import java.util.Random;
import java.util.TreeMap;
import java.util.Collection;

/**
 * Write a description of class Population here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Niche
extends Thread
{
    private boolean done;
    private TreeMap<Double, Individual> folks;
    private Environment environment;
    private Random gen;
    private long cycles;
    private int size;
    
    public Niche(Environment e)
    {
        done = true;
        folks =
            /*
             * This cast compiles, but throws an exception.
             * I wanted folks to be thread-safe, 
             * so that I can make insertions and deletions into 
             * the TreeMap while the thread is running.
             * However, this code does not work.
             */
//             (TreeMap<Double, Individual>)
//             Collections.synchronizedSortedMap(
                new TreeMap<Double, Individual>()
//             )
            ;
        environment = e;
        gen = e.getRandom();
        cycles = 0;
    }
    
    public Niche(Environment e, int n)
    {
        this(e);
        size = n;
        populate();
    }
    
    public Random getGen()
    {
        return gen;
    }
    
    public int getWidth()
    {
        return environment.minWidth();
    }
    
    public void populate()
    {
        int width = getWidth();
        for(int i = 0; i < size; i++) {
            Genome g = new Genome(width, gen);
            Individual person = new Individual(g);
            addIndividual(person);
        }
    }
    
    public int getSize()
    {
    	return size;
    }
    
    public Collection<Double> getValues()
    {
        return folks.keySet();
    }
    
    public Environment getEnvironment()
    {
        return environment;
    }
    
    public long getCycles()
    {
        return cycles;
    }
    
    public Collection<Individual> getIndividuals()
    {
        return folks.values();
    }
    
    public void addIndividual(Individual i)
    {
        folks.put(i.eval(environment), i);
    }
    
    public void removeIndividual(Individual i)
    {
        folks.remove(i.eval(environment));
    }
    
    /**
     * @return an Individual with a specific fitness
     */
    public Individual getIndividual(Double v)
    {
        return folks.get(v);
    }
    
    /**
     * @return an Individual chosen at random
     */
    public Individual getIndividual()
    {
        synchronized(folks) {
            int size = folks.size();
            Double[] keys = folks.keySet().toArray(new Double[size]);
            int select = gen.nextInt(size);
            Double v = keys[select];
            return folks.get(v);
        }
    }
    
    /**
     * @return two Individuals chosen at random
     */
    public Individual[] getPair()
    {
        synchronized(folks) {
            int size = folks.size();
            Double[] keys = folks.keySet().toArray(new Double[size]);
            int s1 = gen.nextInt(size);
            Double v1 = keys[s1];
            int s2 = (s1 + gen.nextInt(size - 1) + 1) % size;
            Double v2 = keys[s2];
            return new Individual[] {
                folks.get(v1), folks.get(v2),
            };
        }
    }
    
    /**
     * @return an Individual with the greatest fitness
     */
    public Double getBestValue()
    {
        return environment.maximizing
            ? folks.lastKey()
            : folks.firstKey();
    }
    
    /**
     * @return an Individual with the greatest fitness
     */
    public Double getWorstValue()
    {
        return environment.maximizing
            ? folks.firstKey()
            : folks.lastKey();
    }
    
    /**
     * @return an Individual with the greatest fitness
     */
    public Individual getBestIndividual()
    {
        return folks.get(getBestValue());
    }
    
    /**
     * @return an Individual with the greatest fitness
     */
    public Individual getWorstIndividual()
    {
        return folks.get(getWorstValue());
    }
    
    public void run() 
    {
        done = false;
        cycles = 0;
        while(!done) {
            step();
            cycles++;
        }
    }
    
    public void step()
    {
        synchronized(folks) {
            // remove least fit individual
            if(environment.maximizing) 
        	    folks.pollFirstEntry();
            else
        	    folks.pollLastEntry();
            
            do {
	            // replace with new child
	            Individual[] parents = getPair();
	            
	            // RESUME HERE
	            Individual child = parents[0].procreate(
	                environment, parents[1]);
	            folks.put(child.eval(environment), child);
            } while(folks.size() < size);
        }
    }
    
    public void quit()
    {
        done = true;
        try { join();
        } catch (Exception e) { }
    }
    
    public String toString()
    {
        return "best = " + getBestValue() 
            + " after " + cycles + " cycles "
            + " based on individual: " 
            + getBestIndividual();
    }
}
