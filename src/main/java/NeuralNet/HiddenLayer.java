package NeuralNet;

import Settings.Layers.Activation.Activation;
import DataTypes.Matrix;
import Settings.Layers.Activation.ActivationFunction;

public class HiddenLayer implements Layer{
    private ActivationFunction activation;
    private Activation activationType;
    private Matrix weights;

    public HiddenLayer(int size, int previousSize, Activation a){
        this.weights = new Matrix(size,previousSize);
        this.weights.initRandom();
        this.activationType = a;
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
        return this.activationType;
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
