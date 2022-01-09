package Settings.Layers.Activation;

import DataTypes.Matrix;

public class ReLu implements ActivationFunction{

    @Override
    public Matrix activate(Matrix m) {
        for(int i = 0; i < m.rows(); i++){
            for(int j = 0; j < m.cols(); j++){
                m.set(i,j,relu(m.get(i,j)));
            }
        }
        return m;
    }

    @Override
    public Matrix gradient(Matrix m) {
        for(int i = 0; i < m.rows(); i++){
            for(int j = 0; j < m.cols(); j++){
                m.set(i,j,relugrad(m.get(i,j)));
            }
        }
        return m;
    }

    private float relu(float x){
        return x > 0 ? x : 0;
    }

    private float relugrad(float x){
        return x > 0 ? 1 : 0;
    }
}
