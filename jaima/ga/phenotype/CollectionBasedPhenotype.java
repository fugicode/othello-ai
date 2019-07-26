package jaima.ga.phenotype;

import java.util.Collection;
import java.util.ArrayList;
import java.math.BigInteger;

/**
 * Write a description of class RangeDouble here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CollectionBasedPhenotype <Base>
implements Phenotype
{
    private final ArrayList<Base> list;
    private RangeIntPhenotype range;
    private Base sample;
    
    /**
     * Constructor for objects of class RangeIntPhenotype
     * @param mn
     * @param mx endpoints of the range of x
     */
    public CollectionBasedPhenotype(Collection<Base> stuff)
    {
        list = new ArrayList<Base> (stuff);
        range = new RangeIntPhenotype(0, stuff.size() - 1);
    }
    
    public int width() { return range.width(); }
    public Class<?> returnClass() { return sample.getClass(); }
    
    public implementingType returnType() { 
        return implementingType.CollectionBased; 
    }
    
    public Object getValue(BigInteger b)
    {
        int index = (Integer) range.getValue(b);
        return list.get(index);
    } 
    
    public String getString(BigInteger b)
    {
        int index = (Integer) range.getValue(b);
        return list.get(index).toString();
    } 
    
    public BigInteger setValue(Object v) 
    {
        int index = list.indexOf(v);
        return range.setValue(index);
    }
}
