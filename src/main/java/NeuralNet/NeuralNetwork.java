package NeuralNet;

import ActivationFunctions.Activation;
import DataTypes.Matrix;
import Maths.Mathf;

public class NeuralNetwork
{

    private Matrix theta1, theta2, theta3;
    private Matrix inputs;
    private int hiddenSize;
    private int outputNum;
    private Matrix z1, a1, z2, a2, z3, a3, input;
    private Matrix[] theta_change, theta_change_previous;
    private float accuracy = 0;
    private Activation[] activations;
    private Layer[] layers;

    public NeuralNetwork()
    {
        //inputs = new Matrix(1,Malspiel.rows*Malspiel.columns + 1);
        inputs.initRandom();
        //theta1 = new Matrix(hiddenSize, Malspiel.rows*Malspiel.columns + 1);
        theta1.initRandom();
        theta1_change = new Matrix(theta1.rows(), theta1.cols());
        theta1_change_previous = new Matrix(theta1.rows(), theta1.cols());
        theta2 = new Matrix(hiddenSize, hiddenSize + 1);
        theta2.initRandom();
        theta2_change = new Matrix(theta2.rows(), theta2.cols());
        theta2_change_previous = new Matrix(theta2.rows(), theta2.cols());
        theta3 = new Matrix(outputNum, hiddenSize + 1);
        theta3.initRandom();
        theta3_change = new Matrix(theta3.rows(), theta3.cols());
        theta3_change_previous = new Matrix(theta3.rows(), theta3.cols());
    }

    public NeuralNetwork(){

    }

    public Matrix predict(Matrix ins)
    {
        ins = ins.transpose();
        ins.addOnesColumn();
        input = ins;
        z1 = Matrix.dot(ins, theta1.transpose());
        a1 = activation(z1);
        a1.addOnesColumn();
        z2 = Matrix.dot(theta2,a1.transpose());
        a2 = activation(z2);
        a2 = a2.transpose();
        a2.addOnesColumn();
        a2 = a2.transpose();
        z3 = Matrix.dot(theta3, a2);
        a3 = softmax(z3);
        return a3;
    }

    private Matrix softmax(Matrix output)
    {
        int maxDim = Math.max(output.rows(), output.cols());
        float[] vals = new float[maxDim];
        float[] softmaxVals = new float[maxDim];
        float total = 0;
        for(int i = 0; i < vals.length; i++)
        {
            vals[i] = output.get(i,0);
            total += Math.exp(vals[i]);
        }
        for(int i = 0; i < vals.length; i++)
        {
            softmaxVals[i] = (float)Math.exp(vals[i]) / total;
        }
        Matrix m = new Matrix(maxDim,1);
        for(int i = 0; i < maxDim; i++)
        {
            m.set(i, 0, softmaxVals[i]);
        }
        return m;
    }

    private Matrix activation(Matrix m)
    {
        Matrix mout = new Matrix(m.rows(), m.cols());
        for(int i = 0; i < m.rows(); i++)
        {
            for(int j = 0; j < m.cols(); j++)
            {
                mout.set(i, j, activationScalar(m.get(i,j)));
            }
        }
        return mout;
    }

    private Matrix activationGradient(Matrix m)
    {
        Matrix mout = new Matrix(m.rows(), m.cols());
        for (int i = 0; i < m.rows(); i++)
        {
            for (int j = 0; j < m.cols(); j++)
            {
                mout.set(i, j, activationScalarGradient(m.get(i, j)));
            }
        }
        return mout;
    }

    public void backprop(Matrix inputGrid, float[] y, float alpha)
    {
        Matrix[] delta;
        Matrix[] grad;
        float lambda = 0.1f;
        Matrix pred = predict(inputGrid);
        Matrix delta4 = new Matrix(1, y.length);
        for (int i = 0; i < y.length; i++)
        {
            delta4.set(0, i, Mathf.clamp(pred.get(i, 0), 0, 1) - y[i]);
        }

        //Debug.Log("delta3: " + delta3.rows() + "," + delta3.cols() + "), theta2: (" + theta2.rows() + "," + theta2.cols() + ")");
        Matrix delta3 = Matrix.dot(delta4, theta3).skipFirstColumn();
        //Debug.Log("Delta2: " + delta2.rows() + ", "+ delta2.cols() + "), z1:" + z1.rows() + ", " + z1.cols());
        delta3 = Matrix.multiply(delta3,activationGradient(z2.transpose()));
        Matrix delta2 = Matrix.dot(delta3, theta2).skipFirstColumn();
        delta2 = Matrix.multiply(delta2,activationGradient(z1));
        //Debug.Log("Delta3: (" + delta3.rows() + "," + delta3.cols() + "), delta2: (" + delta2.rows() + "," + delta2.cols() + ")");
        //Debug.Log("Delta2 transpose: " + delta2.cols() + "," + delta2.rows() + "; a1: " + a1.rows() + "," + a1.cols());
        Matrix grad1 = Matrix.dot(delta2.transpose(), input);
        //Debug.Log("grad1: " + grad1.rows() + "," + grad1.cols() + "; theta1: " + theta1.rows() + "," + theta1.cols());
        Matrix grad2 = Matrix.dot(delta3.transpose(), a1);
        Matrix grad3 = Matrix.dot(delta4.transpose(), a2.transpose());
        //Debug.Log("grad2: " + grad2.shape());

        //theta1 += grad1.scalarTimes(-alpha) + theta1.scalarTimes(-lambda) + theta1_change.scalarTimes(momentum);
        //theta2 += grad2.scalarTimes(-alpha) + theta2.scalarTimes(-lambda) + theta2_change.scalarTimes(momentum);
        //theta3 += grad3.scalarTimes(-alpha) + theta3.scalarTimes(-lambda) + theta3_change.scalarTimes(momentum);
        for(int i = 0; i < theta_change.length; i++){
            theta_change[i] = Matrix.add(theta_change[i],grad[i])
        }
        theta1_change = Matrix.add(theta1_change,grad1.timesScalar(-alpha));
        theta2_change += grad2.timesScalar(-alpha);
        theta3_change += grad3.timesScalar(-alpha);
    }

    private float UpdateThetas(Matrix[] features, float[][] outputs, float alpha, float lambda, float momentum)
    {
        accuracy = 0;
        float losses = 0;
        int[] instanceIDs = Malspiel.randomIDs(features.Length);
        theta1_change = theta1_change.scalarTimes(1 / (float)features.Length);
        theta2_change = theta2_change.scalarTimes(1 / (float)features.Length);
        theta3_change = theta3_change.scalarTimes(1 / (float)features.Length);
        foreach (int j in instanceIDs)
        {
            backprop(features[j], outputs[j], alpha);
        }
        theta1 += theta1_change + theta1.scalarTimes(-lambda) + theta1_change_previous.scalarTimes(momentum);
        theta2 += theta2_change + theta2.scalarTimes(-lambda) + theta2_change_previous.scalarTimes(momentum);
        theta3 += theta3_change + theta3.scalarTimes(-lambda) + theta3_change_previous.scalarTimes(momentum);
        theta1_change_previous = theta1_change;
        theta2_change_previous = theta2_change;
        theta3_change_previous = theta3_change;
        foreach(int i in instanceIDs)
        {
            accuracy += correct(features[i], outputs[i])/(float)instanceIDs.Length;
            losses += loss(features[i], outputs[i]);
        }
        return losses;
    }

    public IEnumerator Train(int MAX_EPOCHS, TrainingController c)
    {
        c.log("Initializing Optimizer...");
        float momentum = 0.9f;
        float lambda = 0.001f;
        float alpha0 = 0.001f;
        float alpha = alpha0;
        Matrix bestTheta1 = theta1, bestTheta2 = theta2, bestTheta3 = theta3;
        float bestLoss = 99999999;
        string[] trainingSet = File.ReadAllLines(Application.persistentDataPath + "/trainingData.txt");
        Matrix[] inputMatrices = new Matrix[trainingSet.Length];
        float[][] outputVectors = new float[trainingSet.Length][];
        string[] current;
        float prevLoss = 9999999;
        int maxPatience = 3;
        int patience = maxPatience;
        c.log("Building Training Set...");
        for(int i = 0; i < trainingSet.Length; i++)
        {
            current = new string[] { trainingSet[i] };
            inputMatrices[i] = Matrix.fromStringArray(current, 1, Malspiel.columns * Malspiel.rows).transpose();
            string[] outstring = current[0].Split(';');
            outputVectors[i] = new float[Malspiel.categories.Length];
            int id = 0;
            for (int ii = outstring.Length - Malspiel.categories.Length; ii < outstring.Length; ii++)
            {
                Debug.Log("ii = " + ii + " id = " + id + " outstring length = " + outstring.Length);
                outputVectors[i][id] = float.Parse(outstring[ii], NumberStyles.Any, CultureInfo.InvariantCulture.NumberFormat);
                id++;
            }
        }
        c.log("Running Epochs...");
        for (int i = 0; i < MAX_EPOCHS; i++)
        {
            c.logSub("Learning rate " + alpha);
            float losses = UpdateThetas(inputMatrices, outputVectors, alpha, lambda, momentum);
            Debug.Log("Loss_T : " + losses);
            c.log("Epoch " + i + " of " + MAX_EPOCHS + ", Entropy: " + losses.ToString("F4") + ", Accuracy: " + accuracy.ToString("F2"));
            if(losses < bestLoss)
            {
                bestTheta1 = theta1;
                bestTheta2 = theta2;
                bestTheta3 = theta3;
                bestLoss = losses;
            }
            if (losses / prevLoss >= 1f)
            {
                patience--;
                c.log("No improvement, patience : " + patience);
                if (patience <= 0)
                {
                    theta1 = bestTheta1;
                    theta2 = bestTheta2;
                    theta3 = bestTheta3;
                    c.log("Restored best weights and saving...");
                    break;
                }
            }
            else patience = maxPatience;

            if (float.IsNaN(losses))
            {
                theta1 = bestTheta1;
                theta2 = bestTheta2;
                theta3 = bestTheta3;
                c.log("Loss is naN, restored best weights and saving...");
                break;
            }
            prevLoss = losses;
            alpha = updateLearningRate(alpha0, 0.01f, 0.8f, i);
            yield return null;
        }
        saveToFile();
        c.log("Saved... You can close the game now.");
        float lossN = 0;
        for(int i = 0; i < inputMatrices.Length; i++)
        {
            lossN += loss(inputMatrices[i], outputVectors[i]);
        }
        c.logSub("Restored best loss: " + lossN);
    }

    float updateLearningRate(float a0, float gamma, float p, int epoch)
    {
        return a0 / Maths.Pow((1 + gamma * epoch), p);
    }

    private float activationScalarGradient(float x)
    {
        if(x < 0)
        {
            return 0;
        }
        else{
            return 1;
        }
    }

    private float activationScalar(float x)
    {
        if(x < 0)
        {
            return 0;
        }
        else
        {
            return x;
        }
    }

    private int correct(Matrix inputMat, float[] y)
    {
        Matrix y_hat = predict(inputMat);
        float max = 0;
        int id = 0;
        for(int i = 0; i < y_hat.rows(); i++)
        {
            if(y_hat.get(i,0) > max)
            {
                max = y_hat.get(i, 0);
                id = i;
            }
        }
        if (y[id] == 1) return 1;
        else return 0;
    }

    private float loss(Matrix inputMat,float[] y)
    {
        Matrix y_hat = predict(inputMat);
        float sum = 0;
        for(int i = 0; i < y.Length; i++)
        {
            sum -= Maths.Log(y_hat.get(i,0)) * y[i];
        }
        return sum;
    }

    public void saveToFile()
    {
        string[] t1 = theta1.toString();
        File.WriteAllLines(Application.persistentDataPath + "/theta1.txt", t1);
        string[] t2 = theta2.toString();
        File.WriteAllLines(Application.persistentDataPath + "/theta2.txt", t2);
        string[] t3 = theta3.toString();
        File.WriteAllLines(Application.persistentDataPath + "/theta3.txt", t3);
    }

    public void readFromFile()
    {
        string[] t1 = File.ReadAllLines(Application.persistentDataPath + "/theta1.txt");
        foreach(string s in t1)
        {
            //Debug.Log("FILE - " + s);
        }
        theta1 = Matrix.fromStringArray(t1, theta1.rows(), theta1.cols());
        string[] t2 = File.ReadAllLines(Application.persistentDataPath + "/theta2.txt");
        //Debug.Log("Line 1 of old t2: " + theta2.toString()[0]);
        //Debug.Log("Line 1 of t2: " + t2[0]);
        theta2 = Matrix.fromStringArray(t2, theta2.rows(), theta2.cols());
        //Debug.Log("Line 1 of new t2: " + theta2.toString()[0]);
        string[] t3 = File.ReadAllLines(Application.persistentDataPath + "/theta3.txt");
        theta3 = Matrix.fromStringArray(t3, theta3.rows(), theta3.cols());
    }

    public void draw(Image neuron, Canvas parentCanvas)
    {
        int HEIGHT = 1080;
        int WIDTH = 1920;
        //layer 1(input)
        for(int i = 0; i < input.rows(); i++)
        {
            float xpos = 20;
            float ypos = (input.rows() - i) * HEIGHT /(float) input.rows();
            Image im = Object.Instantiate(neuron, new Vector3(xpos,ypos,0),Quaternion.identity,parentCanvas.transform);
            im.rectTransform.sizeDelta = new Vector2(HEIGHT / input.rows(),WIDTH / input.rows());
        }
        //layer 2(hidden)
        for (int i = 0; i < a1.cols(); i++)
        {
            float xpos = 0.4f * WIDTH;
            float ypos = (a1.cols() - i) * HEIGHT / (float)a1.cols();
            Image im = Object.Instantiate(neuron, new Vector3(xpos, ypos, 0), Quaternion.identity, parentCanvas.transform);
            im.rectTransform.sizeDelta = new Vector2(HEIGHT / a1.cols(), WIDTH / a1.cols());
        }
    }
}
