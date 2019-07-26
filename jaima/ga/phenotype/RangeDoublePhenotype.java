package jaima.ga.phenotype;

import java.math.BigInteger;
import static java.lang.Math.*;

/**
 * Write a description of class RangeDouble here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RangeDoublePhenotype
implements Phenotype
{
    double min, max, prec;
    private RangeIntPhenotype range;

    /**
     * Constructor for objects of class RangeIntPhenotype
     * @param mn
     * @param mx endpoints of the range of x
     */
    public RangeDoublePhenotype(double mn, double mx, double pr)
    {
        min = min(mn, mx);
        max = max(mn, mx);
        prec = pr;
        int steps = (int) ((max - min) / prec);
        range = new RangeIntPhenotype(steps);
    }
    
    public int width() { return range.width(); }
    public Class<?> returnClass() { return Double.class; }
    
    public implementingType returnType() { 
        return implementingType.RangeDouble; 
    }
    
    public Object getValue(BigInteger b)
    {
        int index = (Integer) range.getValue(b);
        return min + index * prec;
    } 
    
    public String getString(BigInteger b)
    {
        int index = (Integer) range.getValue(b);
        return (min + index * prec) + "";
    } 
    
    public BigInteger setValue(Object v) 
    {
        double d = (Double) v;
        int index = (int) ((d - min) / prec);
        return range.setValue(index);
    }
}
