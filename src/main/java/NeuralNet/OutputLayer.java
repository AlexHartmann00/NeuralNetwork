package NeuralNet;

import ActivationFunctions.Activation;
import DataTypes.Matrix;

public class OutputLayer implements Layer{
    private Matrix weights;

    public OutputLayer(int prevSize, int outputSize, Activation a){
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
}
