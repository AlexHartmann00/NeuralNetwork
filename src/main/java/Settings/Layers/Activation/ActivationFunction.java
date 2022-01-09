package Settings.Layers.Activation;

import DataTypes.Matrix;

public interface ActivationFunction {
    Matrix activate(Matrix m);
    Matrix gradient(Matrix m);
}
