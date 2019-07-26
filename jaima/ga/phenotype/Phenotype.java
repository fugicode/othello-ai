package jaima.ga.phenotype;

import java.math.BigInteger;

/** 
 * A Phenotype is a template for storing and extracting bits into a Genome.
 * Each class that implements Phenotype should also extend PhenotypeMethod,
 * as explained in the PhenotypeMethod documentation.
 */
public interface Phenotype 
{
    /**
     * @return the number of bits to store or retrieve
     * the phenotype
     */
    public abstract int width();
    
    /**
     * the class this Phenotype represents, 
     * i.e., String, Double, Int, etc.
     */
    public abstract Class<?> returnClass();
    
    /**
     * extract a value from raw bits
     * @param b the raw bits
     */
    public abstract Object getValue(BigInteger b);
    
    /**
     * extract a Strng representing the raw bits
     * @param b the raw bits
     */
    public abstract String getString(BigInteger b);
    
    /**
     * return the bits corresponding to the value
     * @param v the value to be represented
     */
    public abstract BigInteger setValue(Object v);
    
    public static enum implementingType {
        RawInt, RawLong, RawFloat, RawDouble, ASCIIString,
        RangeInt, RangeDouble, CollectionBased,
    }; 
    
    public abstract implementingType returnType();
}
