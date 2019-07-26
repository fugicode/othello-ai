package jaima.nn;

import java.util.Random;
import java.util.Collection;

public class FeedForward 
extends NeuralNet {
    protected Neuron[][] nodes;
    protected Threshold threshold;
    protected Sigmoid sigmoid;
    protected Random generator;
    protected double weightLimit;
    protected double learnRate;

    public static class TrainingCase {
        public double[] input;
        public double[] target;
    }

    /**
     * Construct a multi-layer feed-forward network.
     * @param dimensions An array specifying the number of neurons
     * in each layer.  For example, the array {3, 4, 2} specifies
     * a feed-forward network with 3 input nodes, a hidden layer 
     * with 4 nodes, and an output layer with 2 nodes.
     */
    public FeedForward(int[] dimensions) {
        this(dimensions, new Random()); 
    }

    public FeedForward(int[] dimensions, Random gen) {
        super();
        generator = gen;
        weightLimit = 1;
        learnRate = 0.1;
        makeNodes(dimensions);
        reset();
    }

    public int nodesWide() {
        return nodes.length;
    }

    public int nodesTall() {
        int max = nodes[0].length;
        for(Neuron[] layer : nodes) {
            if(layer.length > max) {
                max = layer.length;
            }
        }
        return max;
    }

    private void makeNodes(int[] dimensions) {
        // create the activation functions
        threshold = new Threshold();
        sigmoid = new Sigmoid();

        // create the matrix
        nodes = new Neuron[dimensions.length][];
        for(int i = 0; i < dimensions.length; i++) {
            nodes[i] = new Neuron[dimensions[i] + 1];
        }

        // insert the bias node at the top of each column;
        Neuron bias = new Neuron(); 
        bias.setActivation(threshold);
        for(int i = 0; i < nodes.length; i++) {
            nodes[i][0] = bias;
        }

        // insert the input neurons in the first column
        for(int i = 1; i < nodes[0].length; i++) {
            Neuron node = new Neuron();
            node.setActivation(threshold);
            nodes[0][i] = node;
        }

        // fill the rest of the network
        for(int i = 1; i < nodes.length; i++) {
            for(int j = 1; j < nodes[i].length; j++) {
                Neuron node = new Neuron();
                node.setActivation(sigmoid);
                nodes[i][j] = node;
            }
        }

        // make the feed-forward connections
        for(int i = 1; i < nodes.length; i++) {
            int previous = i - 1;
            for(int j = 1; j < nodes[i].length; j++) {
                for(int k = 0; k < nodes[previous].length; k++) {
                    nodes[i][j].addConnection(nodes[previous][k]);
                }
            }
        }
    }

    public void reset() {  
        resetWeights();
        clearValues();
        clearTargets();
    }

    public void resetWeights() {    
        // make the feed-forward connections
        for(int i = 1; i < nodes.length; i++) {
            int previous = i - 1;
            for(int j = 1; j < nodes[i].length; j++) {
                for(int k = 0; k < nodes[previous].length; k++) {
                    double weight = 2 * generator.nextDouble() - 1;
                    weight *= weightLimit;
                    nodes[i][j].setWeight(k, weight);
                }
                nodes[i][j].setRate(learnRate);
            }
        }
    }

    public void clearValues() {    
        // empty the nodes
        for(int i = 0; i < nodes.length; i++) {
            nodes[i][0].setValue(1.0);
            for(int j = 1; j < nodes[i].length; j++) {
                nodes[i][j].setValue(0.0);
            }
        }
    }

    public void clearTargets() {    
        // empty the nodes
        for(int j = 0; j < nodes[0].length; j++) {
            nodes[0][j].setTarget(null);
        }
        for(int i = 0; i < nodes.length; i++) {
            nodes[i][0].setTarget(null);
        }
        for(int i = 1; i < nodes.length; i++) {
            for(int j = 1; j < nodes[i].length; j++) {
                nodes[i][j].setTarget(0.0);
            }
        }
    }

    /**
     * Set the values of each input node in the network.
     * @param values An array of input values.
     * The length of this array must equal the original length
     * of layer 0, specified when the network was created.
     */
    public void setInput(double[] values) {
        for(int i = 0; i < values.length; i++) {
            nodes[0][i+1].setValue(values[i]);
        }
    }

    /**
     * Set the target values for each output node in the network,
     * then propagate the errors back to the hidden layers.
     * @param t An array of target values.
     * The length of this array must equal the original length
     * of the last (output) layer, 
     * specified when the network was created.
     */
    public void setTarget(double[] t) {
        int oL = nodes.length - 1;      // output layer
        int oN = nodes[oL].length - 1;  // # output nodes
        if(t.length !=  oN) {
            System.out.println(
                "Error: Target has " + t.length 
                + " values, but output has " + oN + " nodes."
            );
            return;
        }
        // set the output layer targets
        for(int j = 1; j < nodes[oL].length; j++) {
            nodes[oL][j].setTarget(t[j - 1]);
        }
        // set the hidden layer targets
        for(int L = oL; L > 1; L--) {
        for(int j = 1; j < nodes[L].length; j++) {
            nodes[L][j].backPropErrors();
        }
    }
    }

    /**
     * Set the target values for each output node in the network.
     * @param values An array of output values.
     * The length of this array must equal the original length
     * of the last (output) layer, 
     * specified when the network was created.
     */
    public double[] getTarget() {
        int last = nodes.length - 1;
        int visible = nodes[last].length - 1;
        double[] values = new double[visible];
        for(int i = 0; i < visible; i++) {
            values[i] = nodes[last][i+1].getTarget();
        }
        return values;
    }

    /**
     * Fire all the nodes in the network, 
     * feeding forward from input to output.
     */
    public void fire()
    {
        for(int i = 1; i < nodes.length; i++) {
            for(int j = 1; j < nodes[i].length; j++) {
                nodes[i][j].fire();
            }
        }
    }

    /**
     * Adjust all the weights in the neural network,
     * propagating errors backward from output to input.
     */
    public void backPropagate()
    {
        int last = nodes.length - 1;
        for(int b = last; b > 0; b--) {
            for(int j = 1; j < nodes[b].length; j++) {
                nodes[b][j].backPropWeights();
            }
        }
    }

    public void train(TrainingCase t) {
        train(t.input, t.target);
    }

    /**
     * Adjust all the weights in the neural network,
     * propagating errors backward from output to input.
     */
    public void train(double[] input, double[] target)
    {
        setInput(input);
        fire();
        setTarget(target);
        backPropagate();
    }

    public void train(Collection<TrainingCase> sets) {
        train(sets, 1);
    }

    public void train(Collection<TrainingCase> sets, int repetitions) {
        for(int i = 0; i < repetitions; i++) {
            for(TrainingCase t : sets) {
                train(t);
            }
        }
    }

    /**
     * Adjust all the weights in the neural network,
     * propagating errors backward from output to input.
     */
    public double testOutput(double[] input, double[] target)
    {
        setInput(input);
        fire();
        double[] output = getOutput();
        double maxError = 0;
        for (int i = 0; i < target.length; i++) {
            double error = Math.abs(target[i] - output[i]);
            if(error > maxError) {
                maxError = error;
            }
        }
        return maxError;
    }

    public double[] getOutput() {
        return getLayer(nodes.length - 1);
    }

    /**
     * Retrieve the values of all the nodes 
     * in one layer of the network.
     * @param layer The layer to retrieve.  
     * 0 specifies the input layer, 
     * the last layer is the output layer.
     * @return An array of node values.
     */
    public double[] getLayer(int layer) {
        int length = nodes[layer].length;
        double[] output = new double[length - 1];
        for(int j = 1; j < length; j++) {
            output[j - 1] = nodes[layer][j].getValue();
        }
        return output;
    }

    /**
     * @param layer The layer in which the neuron resides.  
     * 0 specifies the input layer, 
     * the last layer is the output layer.
     * @param position The position of the node within the layer.
     * 0 specifies the bias node, whose value is always 1.
     * @return the value of a single neuron
     */
    public double getValue(int layer, int position) {
        return nodes[layer][position].getValue();
    }

    /**
     * @param fP The position of the first neuron within its layer.
     * @param sP The position of the second neuron within its layer.
     * @param sL The layer in which the second neuron resides.  
     * 1 &le; sL &le; 
     * the last (output) layer.
     * @param position the position of the node within the layer.
     * @return the value of a single neuron
     */
    public double getWeight(int fP, int sP, int sL) {
        return nodes[sL][sP].getWeight(fP);
    }
}
