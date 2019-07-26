package jaima.ga.phenotype;

import java.math.BigInteger;

/**
 * Encodes a complete double precision value, 
 * with the range of the native int type.
 */
public class RawFloatPhenotype
implements Phenotype
{
    public int width() { return 32; }
    public Class<?> returnClass() { return Float.class; }
    
    public implementingType returnType() { 
        return implementingType.RawFloat; 
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
