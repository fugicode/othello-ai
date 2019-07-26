package jaima.nn;

import java.util.ArrayList;
//import java.util.Random;

/**
 * A single Neuron as described in 
 * Russell & Norvig, AIMA, 3rd edition, pp. 727-733.
 * The processes of adjusting weights and
 * back-propagating errors is separated,
 * so they can be demonstrated more clearly.
 * 
 * @author William H. Hooper
 * @version 2012-10-21
 */
public class Neuron
{
    // derived from the most recent firing
    private Double value;   // the instantaneous value
    private Double slope;   // the 1st derivative

    // used for training
    private Double target;          // desired value 
    private Double learningRate;    // adjustment step

    private ActivationFunction activation;
    private ArrayList<Input> inputs;

    private class Input {
        Neuron neuron;
        Double weight;
    }

    public Neuron() {
        inputs = new ArrayList<Input>();
    }

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Double getSlope() {
        return value;
    }

    public Double getTarget() {
        return target;
    }

    public void setTarget(Double target) {
        this.target = target;
    }

    /**
     * Apply the back-propagation algorithm
     * to the target values of the upstream neurons,
     * as described in AIMA, p. 733.
     * The processes of adjusting weights and
     * back-propagating errors is separated,
     * so they can be demonstrated more clearly.
     */
    public void backPropErrors() {
        double errK = target - value;
        double deltaK = errK * slope;
        for(int i = 1; i < inputs.size(); i++) {
            Input upstream = inputs.get(i);
            double summand = upstream.weight * deltaK;
            upstream.neuron.moveTarget(summand);
        }
    }

    /**
     * Apply the back-propagation algorithm
     * to the input weights,
     * as described in Russell & Norvig, AIMA,
     * 3rd edition, p. 733.
     * The processes of adjusting weights and
     * back-propagating errors is separated,
     * so they can be demonstrated more clearly.
     */
    public void backPropWeights() {
        double errK = target - value;
        double deltaK = errK * slope;
        for(Input upstream : inputs) {
            double aJ = upstream.neuron.getValue();
            upstream.weight += learningRate 
            * aJ * deltaK;
        }
    }

    public void moveTarget(double delta) {
        if(target == null) {
            return;
        }
        target += delta;
    }

    public Double getRate() {
        return learningRate;
    }

    public void setRate(double value) {
        this.learningRate = value;
    }

    public ActivationFunction getActivation() {
        return activation;
    }

    public void setActivation(ActivationFunction activation) {
        this.activation = activation;
    }

    public void addConnection(Neuron node)  {
        Input upstream = new Input();
        upstream.neuron = node;
        inputs.add(upstream);
    }

    public int numberOfConnections() {
        return inputs.size();
    }

    public void setWeight(int index, double weight)  {
        Input upstream = inputs.get(index);
        upstream.weight = weight;
    }

    public double getWeight(int index)  {
        Input upstream = inputs.get(index);
        return upstream.weight;
    }

    private double weightedSum() {
        double sum = 0;
        for(Input upstream : inputs) {
            sum += upstream.weight 
            * upstream.neuron.getValue();
        }
        return sum;
    }

    public void fire() {
        double sum = weightedSum();
        value = activation.output(sum);
        target = value;
        slope = activation.deriv(sum);
    }
}
