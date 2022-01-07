package NeuralNet;

import DataTypes.Matrix;

public interface Layer {
    Matrix getWeights();
    void setWeights();
    int getNodeCount();
}
