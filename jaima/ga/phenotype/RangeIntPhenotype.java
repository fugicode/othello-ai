package jaima.ga.phenotype;

import java.math.BigInteger;
import static java.lang.Math.*;

/**
 * Write a description of class RangeDouble here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RangeIntPhenotype
implements Phenotype
{
    int min, max;
    int width, steps;
    
    /**
     * Constructor for objects of class RangeIntPhenotype
     * @param max range of 0..max
     */
    public RangeIntPhenotype(int max)
    {
        this(0, max);
    }
    
    /**
     * Constructor for objects of class RangeIntPhenotype
     * @param mn
     * @param mx endpoints of the range of x
     */
    public RangeIntPhenotype(int mn, int mx)
    {
        min = min(mn, mx);
        max = max(mn, mx);
        
        steps = max - min + 1;
        width = (int) ceil(log(steps) / log(2));        
    }
    
    public int width() { return width; }
    public Class<?> returnClass() { return Integer.class; }
    
    public implementingType returnType() { 
        return implementingType.RangeInt; 
    }
    
    public Object getValue(BigInteger b)
    {
        int el = (b.intValue() % steps + steps) % steps;
        return el + min;
    } 
    
    public String getString(BigInteger b)
    {
        int el = (b.intValue() % steps + steps) % steps;
        return (el + min) + "";
    } 
    
    public BigInteger setValue(Object v) 
    { 
        int d = (Integer) v;
        assert(min <= d && d <= max);
        int bits = (int) (d - min);
        assert(bits <= steps);
        return new BigInteger("" + bits);
    }
}
