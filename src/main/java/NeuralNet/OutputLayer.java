package NeuralNet;

import Settings.Layers.Activation.Activation;
import DataTypes.Matrix;
import Settings.Layers.Activation.ActivationFunction;

public class OutputLayer implements Layer{
    private Matrix weights;
    private ActivationFunction activation;
    private Activation activationType;

    public OutputLayer(int outputSize,int prevSize, Activation a){
        weights = new Matrix(outputSize,prevSize);
        weights.initRandom();
    }

    @Override
    public Matrix getWeights() {
        return null;
    }

    @Override
    public void setWeights() {

    }

    @Override
    public int getNodeCount() {
        return 0;
    }

    @Override
    public Activation getActivation() {
        return null;
    }

    @Override
    public Matrix setWeightsChange(Matrix c) {
        return null;
    }

    @Override
    public Matrix getWeightsChange() {
        return null;
    }
}
