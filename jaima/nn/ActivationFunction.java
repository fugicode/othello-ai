package jaima.nn;

/**
 * @author Ravi Mohan
 * @author revised by William H. Hooper
 * @version 2012-10-21
 */
public abstract class ActivationFunction {
    private double max, min, range;
    
    public ActivationFunction() {
        this(1, -1);
    }
    
    public ActivationFunction(double max, double min) {
        this.max = max;
        this.min = min;
    	range = this.max - this.min;
    }
    
    /**
     * @return a value between 0 and 1.
     */
    public abstract double activation(double parameter);

    /**
     * @return the 1st derivtive of the activation function.
     */
    public abstract double deriv(double parameter);
    
    public double output(double input) {
        return range * activation(input) + min;
    }
    
    public double slope(double input) {
        return range * deriv(input);
    }
}