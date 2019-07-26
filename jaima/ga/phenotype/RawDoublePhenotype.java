package jaima.ga.phenotype;

import java.math.BigInteger;

/**
 * Encodes a complete double precision value, 
 * with the range of the native int type.
 */
public class RawDoublePhenotype
implements Phenotype
{
    public int width() { return 64; }
    public Class<?> returnClass() { return Double.class; }
    
    public implementingType returnType() { 
        return implementingType.RawDouble; 
    }
    
    public Object getValue(BigInteger b)
    {
        long el = b.longValue();
        return Double.longBitsToDouble(el);
    } 
    
    public String getString(BigInteger b)
    {
        long el = b.longValue();
        return Double.longBitsToDouble(el) + "";
    } 
    
    public BigInteger setValue(Object v) 
    { 
        double d = (Double) v;
        long el = Double.doubleToRawLongBits(d);
        return new BigInteger("" + (Long)el);
    }
}
