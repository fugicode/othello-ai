package jaima.ga.phenotype;

import java.math.BigInteger;
import static java.math.BigInteger.*;

/**
 * StringPhenotype - encodes a string of 7-bit ASCII characters.
 * 
 * @author William H. Hooper 
 * @version 2014-09-18
 */
public class ASCIIStringPhenotype
implements Phenotype
{
    private int length;
    
    /**
     * @param len the number of characters to encode
     */
    public ASCIIStringPhenotype(int len)
    {
        length = len;
    }
    
    public int width() { return length * 7; }
    public Class<?> returnClass() { return String.class; }
    
    public implementingType returnType() { 
        return implementingType.ASCIIString; 
    }
    
    public Object getValue(BigInteger b)
    {
        return getString(b);
    }
    
    public String getString(BigInteger b)
    {
        String s = "";
        BigInteger mask = new BigInteger("127"); // i.e. 0111111
        
        for(int i = 0; i < length; i++) {
            int shift = i * 7;
            int v = b.shiftRight(shift).and(mask).intValue();
            char c = (char)(v % 96 + 32);
            s += c;
        }
        return s;
    } 
    
    public BigInteger setValue(Object v) 
    { 
        String s = (String) v;
        BigInteger b = ZERO;
        
        for(int i = 0; i < length; i++) {
            char c = s.charAt(i);
            int shift = i * 7;
            int j = ((int)c - 32) % 96;
            BigInteger a = new BigInteger("" + j);
            b = b.or(a.shiftLeft(shift));
        }
        return b;
    }
}
