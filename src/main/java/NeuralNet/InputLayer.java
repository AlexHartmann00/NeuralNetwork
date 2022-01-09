package NeuralNet;

import Settings.Layers.Activation.Activation;
import DataTypes.Matrix;
import Settings.Layers.Activation.ActivationFunction;

public class InputLayer implements Layer{
    int size;
    private ActivationFunction activation;
    private Activation activationType;

    public InputLayer(int size, Activation a){
        this.size = size;
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
        return size;
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
