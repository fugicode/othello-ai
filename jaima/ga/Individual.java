package jaima.ga;

/**
 * Write a description of class Individual here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Individual
{
    private Genome genome;
//     private Environment cache;

    /**
     * Constructor for objects of class Individual
     */
    public Individual(Genome g)
    {
        genome = g;
    }

    public Genome getGenome()
    {
        return genome.clone();
    }

    /**
     * ensure that no two individuals evaluate exactly the same way,
     * by adding a very small adjustment to the evaluation
     */
    public double eval(Environment e)
    {
        double value = e.eval(genome);
        long base = Double.doubleToRawLongBits(value);
        long adj = hashCode() % (1L << 20);
        /*
         * future work--make 20 a variable instead:
         * if 0, all invividuals with the same eval are indistinguishable
         * if 32, all individuals are unique
         * this will affect the number of slots in the Niche filled
         * by invividuals who are essentially clones.
         */
        return Double.longBitsToDouble(base + adj);
    }

    public Individual procreate(Environment e, Individual father)
    {
        // RESUME HERE
        double cp = e.getCrossoverProbability();
        double mp = e.getMutationProbability();
        Genome childGenome = genome
            .crossover(father.getGenome(), cp)
            .mutate(mp);
        return new Individual(childGenome);
    }

    public String getString(Environment e)
    {
        return genome.getString(e.getTypes());
    }

    public String toString()
    {
//         if(cache == null) {
            return genome.toString();
//         }
//         return getString(cache);
    }
}
