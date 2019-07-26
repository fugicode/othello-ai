package jaima.ga;

import java.math.BigInteger;
import static java.math.BigInteger.*;
import static java.lang.Math.*;
import java.util.Random;
import jaima.ga.phenotype.*;

/**
 * Write a description of class Genome here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Genome
implements Cloneable
{
    private Random gen;
    private BigInteger bits;

    /**
     * the simplest way to construct a genome,
     * this method constructs a random bitstring
     * @param len the number of bits in the new genome
     */
    public Genome(int len)
    {
        this(len, new Random());
    }

    /**
     * a better way to construct multiple genomes,
     * this method constructs a random bitstring
     * using a common Random number generator
     * @param len the number of bits in the new genome
     * @param gen a Random number generator
     */
    public Genome(int len, Random g)
    {
        this(new BigInteger(len, g), g);
    }

    /**
     * for debugging, construct a genome 
     * from one list of phenotypes and another list of values
     * @param b the bitstring stored as a BigInteger
     */
    public Genome(Phenotype[] types, Object[ ] values)
    {
        this(ZERO, new Random());
        int shift = 0;

        if(types.length != values.length) {
            System.err.println("Error: mismatch between types["
                + types.length + "] and values["
                + values.length + "]");
        }

        for(int i = 0; i < values.length; i++) {
            BigInteger append = types[i].setValue(values[i]);
            bits = bits.or(append.shiftLeft(shift));
            shift += types[i].width();
        }
    }

    /**
     * for debugging, construct a genome with a fixed string of bits
     * @param b the bitstring stored as a BigInteger
     */
    public Genome(BigInteger b)
    {
        this(b, new Random());
    }

    /**
     * for very precise debugging, 
     * construct a genome with a fixed string of bits
     * and a pre-seeded Random number generator
     * @param b the bitstring stored as a BigInteger
     * @param gen a Random number generator
     */
    public Genome(BigInteger b, Random g)
    {
        bits = b;
        gen = g;
    }

    public Genome clone()
    {
        return new Genome(bits, gen);
    }

    protected BigInteger getBits()
    {
        return bits;
    }

    /**
     * Flip bits at random in a genome.
     * @param mp the uniform point probability 
     * of a random mutation.
     * @return the mutant child.
     */
    public Genome mutate(Double mp)
    {
        BigInteger child = bits.add(ZERO);
        for(int i = 0; i < bits.bitLength(); i++) {
            if(signal(mp)) {
                child = child.flipBit(i);
            }
        }
        return new Genome(child);
    }

    /**
     * Create a new genome from the cross of two parents.
     * @param g the other parent, besides this one.
     * @param cp the probability that any single bit location 
     * is a crossover point.
     * @return the child created by crossing the two parents,
     * both of which are undisturbed.
     */
    public Genome crossover(Genome g, Double cp)
    {
        BigInteger mom = bits;
        BigInteger dad = g.getBits();
        BigInteger diff = mom.xor(dad);
        int len = max(mom.bitLength(), dad.bitLength());
        BigInteger mask = getMask(len, cp);
        BigInteger flip = diff.and(mask);
        BigInteger child = mom.xor(flip);
        return new Genome(child);
    }

    /**
     * Create a new genome from the cross of two parents.
     * @param g the other parent, besides this one.
     * @param mask a bitstring 011000... , 
     * where 0 selects the this parent
     * and 1 selects the parent passed as a parameter.
     * @return the child created by crossing the two parents,
     * both of which are undisturbed.
     */
    public Genome crossover(Genome g, BigInteger mask)
    {
        BigInteger mom = bits;
        BigInteger dad = g.getBits();
        BigInteger diff = mom.xor(dad);
        BigInteger flip = diff.and(mask);
        BigInteger child = mom.xor(flip);
        return new Genome(child);
    }

    /**
     * Produce a mask for efficient crossover between two genomes,
     * i.e. a bitstring of the form ... 1...10...01...1 ...
     * This method has computational complexity O(length / cp)
     * @param length the number of bits in the mask
     * @param p the probability that any single bit location 
     * is a crossover point.
     * @return the bitstring as a BigInteger
     */
    private BigInteger getMask(int length, double cp)
    {
        BigInteger mask = ZERO;
        int shift = -1;
        double q = 1 - cp;
        double lnq = log(q);

        while(shift < length) {
            int limit = length - shift;
            double minP = pow(q, limit - 1);
            double p = gen.nextDouble();
            if (p < minP) break;

            int advance = (int)(log(p) / lnq) + 1;
            shift += advance;
            mask = mask.xor(ONE.shiftLeft(shift).subtract(ONE));
        }

        if(gen.nextBoolean())
            mask = mask.xor(ONE.shiftLeft(length).subtract(ONE));
        return mask;
    }

    /**
     * Produce a mask for efficient crossover between two genomes,
     * i.e. a bitstring of the form ... 1...10...01...1 ...
     * This method has computational complexity O(length / cp)
     * @param p the probability that any single bit location 
     * is a crossover point.
     * @return the bitstring as a BigInteger
     */
    public BigInteger getMask(double cp)
    {
        BigInteger mask = ZERO;
        int shift = -1;
        double q = 1 - cp;
        double lnq = log(q);

        int length = bits.toString(2).length();
        while(shift < length) {
            int limit = length - shift;
            double minP = pow(q, limit - 1);
            double p = gen.nextDouble();
            if (p < minP) break;

            int advance = (int)(log(p) / lnq) + 1;
            shift += advance;
            mask = mask.xor(ONE.shiftLeft(shift).subtract(ONE));
        }

        if(gen.nextBoolean())
            mask = mask.xor(ONE.shiftLeft(length).subtract(ONE));
        return mask;
    }

    /**
     * an equivalent but deprecated implementation of getMask(), 
     * this one is O(length).
     */
    //     private BigInteger getMask(int length, double p)
    //     {
    //         BigInteger mask = ZERO;
    //         for(int i = 0; i < length; i++) {
    //             if(signal(p)) {
    //                 printNumber(i);
    //                 mask = mask.xor(ONE.shiftLeft(i).subtract(ONE));
    //             }
    //         }
    //         System.out.println();
    //         if(gen.nextBoolean())
    //             mask = mask.xor(ONE.shiftLeft(length).subtract(ONE));
    //         return mask;
    //     }

    /**
     * @return true with probability p, false otherwise 
     */
    private boolean signal(double p)
    {
        double r = gen.nextDouble();
        return r < p;
    }

    /**
     * Extract the values from the Genome.
     * @param phenotypes an array of Phenotypes,
     * e.g. { RawInt, ASCIIString, etc. }
     * @return an array of objects, 
     * e.g. { 25, "Bill" }
     * These objects have to be cast back into 
     * their original types before they can be evaluated.
     */
    public Object[] getPhenome(Phenotype[] phenotypes)
    {
        Object[] values = new Object[phenotypes.length];
        int right = 0;
        for(int i = 0; i < values.length; i++) {
            Phenotype p = phenotypes[i];
            values[i] = extract(right, p);
            right += p.width();
        }
        return values;
    }

    /**
     * Extract one value from the genome.
     * @param r starting point within bit string
     * @param p the Phenotype of the object, e.g. RawInt...
     * @return a object, e.g. 25
     * This objects has to be cast back into 
     * its original type before it can be evaluated.
     */
    public Object extract(int r, Phenotype p)
    {
        BigInteger shifted = bits.shiftRight(r);
        return p.getValue(shifted);
    }

    /**
     * @param phenotypes an array of Phenotypes,
     * e.g. { RawInt, ASCIIString, etc. }
     * @return a string representing the values contained in the 
     * genome.
     * e.g. "{ 25, Bill }"
     * 
     * UNDER CONSTRUCTION
     */
    public String getString(Phenotype[] phenotypes)
    {
        String s = "{";
        int right = 0;
        for(int i = 0; i < phenotypes.length; i++) {
            if(i > 0) s += ",";
            Phenotype p = phenotypes[i];
            s += getString(right, p);
            right += p.width();
        }
        return s + "}";
    }

    public String getString(int r, Phenotype p)
    {
        BigInteger shifted = bits.shiftRight(r);
        return p.getString(shifted);
    }

    public String toString()
    {
        return bits.toString(16).toUpperCase();
    }

    public String toString(int base)
    {
        return bits.toString(base).toUpperCase();
    }
}
