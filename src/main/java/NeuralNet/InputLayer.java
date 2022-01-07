package NeuralNet;

import ActivationFunctions.Activation;
import DataTypes.Matrix;

public class InputLayer implements Layer{
    private Matrix weights;

    public InputLayer(int size, Activation a){
        weights = new Matrix(size,1);
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
