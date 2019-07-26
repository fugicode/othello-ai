package jaima.ga;

import jaima.ga.phenotype.*;
import java.util.Random;

/**
 * Abstract class Environment.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Environment
{
    protected Random gen;
    private double pMutation;
    private double pCrossover;
    public boolean maximizing;
    
    public Environment()
    {
        gen = new Random();
        pMutation = 0.0001;
        pCrossover = 0.01;
        maximizing = true;
    }
    
    /**
     * A list of the types to be extracted from the 
     * Genome:
     * @return an array of Phenotypes
     */
    public abstract Phenotype[] getTypes();
// for example
//    {
//        return new Phenotype[] {
//            new RawIntPhenotype(),
//            new RangeDoublePhenotype(5, 10, 0.1),
//            new ASCIIStringPhenotype(10),
//        };
//    }
    
    /**
     * @return the minimum number of bits in the genome
     */
    public int minWidth()
    {
        int w = 0;
        for(Phenotype p : getTypes()) {
            w += p.width();
        }
        return w;
    }
    
    /**
     * Set the probability that any given bit will mutate
     * @param p a probability, 0 <= p <= 1
     */
    public void setMutationProbability(double p)
    {
        pMutation = p;
    }

    /**
     * get the probability that any given bit will mutate
     * return a probability, 0 <= p <= 1
     */
    public double getMutationProbability()
    {
        return pMutation;
    }
    
    /**
     * set crossover probability
     * @param p a probability 0 <= p < 1.0
     */
    public void setCrossoverProbability(double p)
    {
        pCrossover = p;
    }
    
    /**
     * set crossover probability
     * @param n the average number of crossover points
     */
    public void setCrossoverProbability(int n)
    {
        pCrossover = ((double) n) / minWidth();
    }
    
    public double getCrossoverProbability()
    {
        return pCrossover;
    }
    
    public Random getRandom()
    {
        return gen;
    }
    
    public double eval(Genome g)
    {
        return 0;
    }
    
    public void setMaximizing()
    {
        maximizing = true;
    }
    
    public void setMinimizing()
    {
        maximizing = false;
    }
}
