package jaima.nn;

/**
 * @author Ravi Mohan
 * 
 */
public class Linear 
extends ActivationFunction {

	public double activation(double parameter) {
		return parameter;
	}

	public double deriv(double parameter) {

		return 1;
	}
}
