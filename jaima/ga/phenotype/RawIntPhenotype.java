package jaima.ga.phenotype;

import java.math.BigInteger;

/**
 * Encodes a complete integer, 
 * with the range of the native int type.
 */
public class RawIntPhenotype
implements Phenotype
{
    public int width() { return 32; }
    public Class<?> returnClass() { return Integer.class; }
    
    public implementingType returnType() { 
        return implementingType.RawInt; 
    }
    
    public Object getValue(BigInteger b)
    {
        return b.intValue();
    } 
    
    public String getString(BigInteger b)
    {
        return ((Integer) b.intValue()).toString();
    } 
    
    public BigInteger setValue(Object v) 
    { 
        return new BigInteger("" + (Integer)v);
    }
}
