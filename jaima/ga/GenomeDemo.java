package jaima.ga;

import jaima.ga.phenotype.*;
import java.math.BigInteger;

/**
 * The test class GenomeTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GenomeDemo extends junit.framework.TestCase
{
    private Phenotype[] phmix;
    private Object[] valuemix1;
    private Object[] valuemix2;
    private Genome genmix1;
    private Genome genmix2;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
        phmix = new Phenotype[] {
            new RawIntPhenotype(),
            new ASCIIStringPhenotype(4),
            new RawLongPhenotype(),
            new RawFloatPhenotype(),
        };

        valuemix1 = new Object[] {
            25,
            "help",
            1152921505680588801L,  // 2^60 + 2^30 + 1
            5.6,
        };

        valuemix2 = new Object[] {
            18,
            "woot",
            1152921505143717890L, // 2^60 + 2^29 + 2
            6.3,
        };

        genmix1 = new Genome(phmix, valuemix1);
        genmix2 = new Genome(phmix, valuemix2);
    }

    public void testMix1() {   testMix(genmix1, valuemix1); }

    public void testMix2() {   testMix(genmix2, valuemix2); }
    
    private void testMix(Genome genmix, Object valuemix)
    {
        Object[] phenome = genmix1.getPhenome(phmix);
        assertNotNull(phenome);
        for(int i = 0; i < phmix.length; i++) {
            assertTrue(
                phenome[i].equals(valuemix1[i])
            );            
        }
    }

    public void testMask()
    {
        BigInteger mask = genmix1.getMask(0.01);
        Genome genmix3 = genmix1.crossover(genmix2, mask);
        Genome genmix4 = genmix3.mutate(0.02);
        showStrings(new BigInteger[] { mask, genmix1.getBits(), 
            genmix2.getBits(), genmix3.getBits(), genmix4.getBits() 
        });
    }

    private String numberString(int i)
    {
        String s = "   " + i;
        int b = s.length() - 4;
        return s.substring(b);
    }
    
    /**
     * test code for getMask()
     */
    private void showStrings(BigInteger[] biggies)
    {
        String[] digits = new String[biggies.length];
        int max = 0;
        for(int i = 0; i < biggies.length; i++) {
            digits[i] = biggies[i].toString(16);
            max = Math.max(max, digits[i].length());
        }
        
        max = max + (4 - max % 4) % 4;  // round up to 4th char
        
        String scale = "";
        for (int i = 0; i < max / 4; i++)
            scale = numberString(i*4) + scale;
            
        System.out.println("\f");
        System.out.println(scale);
        System.out.println(
            ( "---+---+---+---+---+---+---+---+---+---+" 
                + "---+---+---+---+---+---+---+---+---+---+"
                + "---+---+---+---+---+---+---+---+---+---+"
                + "---+---+---+---+---+---+---+---+---+---+"
            ).substring(0, max)
        );

        for(int i = 0; i < biggies.length; i++) {
            String output = digits[i];
            while(output.length() < max) {
                output = " " + output;
            }
            System.out.println(output);
        }
    }
}

