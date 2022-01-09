package NeuralNet;

import Settings.Layers.Activation.Activation;
import DataTypes.Matrix;

public interface Layer {
    Matrix getWeights();
    void setWeights();
    int getNodeCount();
    Activation getActivation();
    Matrix setWeightsChange(Matrix c);
    Matrix getWeightsChange();
}
