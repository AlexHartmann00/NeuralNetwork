package Settings.Layers.Activation;

import DataTypes.Matrix;

public class Softmax implements ActivationFunction{
    @Override
    public Matrix activate(Matrix m) {
        int maxDim = Math.max(m.rows(), m.cols());
        float[] vals = new float[maxDim];
        float[] softmaxVals = new float[maxDim];
        float total = 0;
        for(int i = 0; i < vals.length; i++)
        {
            vals[i] = m.get(i,0);
            total += Math.exp(vals[i]);
        }
        for(int i = 0; i < vals.length; i++)
        {
            softmaxVals[i] = (float)Math.exp(vals[i]) / total;
        }
        Matrix mat = new Matrix(maxDim,1);
        for(int i = 0; i < maxDim; i++)
        {
            mat.set(i, 0, softmaxVals[i]);
        }
        return mat;
    }

    @Override
    public Matrix gradient(Matrix m) {
        return null;
    }
}
