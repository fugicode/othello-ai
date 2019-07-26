package jaima.ga;

import jaima.ga.phenotype.*;
import java.math.BigInteger;

/**
 * The test class GenomeTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GenomeTest extends junit.framework.TestCase
{
    private Genome genome1;
    private Phenotype[] phenome1;

    private Genome genome2;
    private Phenotype[] phenome2;

    private Genome genome3;

    /**
     * Default constructor for test class GenomeTest
     */
    public GenomeTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
        genome1 = new Genome(new java.math.BigInteger("25"));
        phenome1 = new Phenotype[] { new RawIntPhenotype() };

        phenome2 = new Phenotype[] { new ASCIIStringPhenotype(5) };
        genome2 = new Genome(phenome2[0].setValue("hello"));

        genome3 = new Genome(50);
    }

    public void testInt()
    {
        Object[] object1 = genome1.getPhenome(phenome1);
        assertNotNull(object1);
        int v = (Integer) object1[0];
        assertTrue(v == 25);
    }

    public void testString()
    {
        Object[] object1 = genome2.getPhenome(phenome2);
        assertNotNull(object1);
        String s = (String) object1[0];
        assertTrue(s.equals("hello"));
    }
}

