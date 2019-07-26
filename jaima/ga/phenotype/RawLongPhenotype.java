package jaima.ga.phenotype;

import java.math.BigInteger;

/**
 * Encodes a complete integer, 
 * with the range of the native int type.
 */
public class RawLongPhenotype
implements Phenotype
{
    public int width() { return 64; }
    public Class<?> returnClass() { return Long.class; }
    
    public implementingType returnType() { 
        return implementingType.RawLong; 
    }
    
    public String getString(BigInteger b)
    {
        return ((Long) b.longValue()).toString();
    } 
    
    public Object getValue(BigInteger b)
    {
        return b.longValue();
    } 
    
    public BigInteger setValue(Object v) 
    { 
        return new BigInteger("" + (Long)v);
    }
}
