package jaima.ga.phenotype;

import java.math.BigInteger;

/**
 * Each implementation of PhenotypeMethod overrides one pair of methods 
 * to get- and set- values for a string of bits.  
 * Each pair of methods handles a single type of value, 
 * so that the compiler can check that the correct phenotype is used
 * to extract a matching value.
 */
public abstract class PhenotypeMethod
{
    private void perror(String name)
    {
        System.err.println("Error: the " +  name
            + "() method is not defined for the class " 
            + this.getClass() + ".");
    }
    
    public Integer getInt(BigInteger bits) { perror("getInt"); return null; }
    public BigInteger setInt(int value) { perror("setInt"); return null; }
    
    public Long getLong(BigInteger bits) { perror("getLong"); return null; }
    public BigInteger setLong(long value) { perror("setLong"); return null; }
    
    public Float getFloat(BigInteger bits) { perror("getFloat"); return null; }
    public BigInteger setFloat(float value) { perror("setFloat"); return null; }
    
    public Double getDouble(BigInteger bits) { perror("getDouble"); return null; }
    public BigInteger setDouble(double value) { perror("setDouble"); return null; }
    
    public String getString(BigInteger bits) { perror("getString"); return null; }
    public BigInteger setString(String value) { perror("setString"); return null; }
}
