package NeuralNet;

import Settings.Layers.Activation.Activation;
import Settings.Layers.Activation.ActivationFactory;

import java.util.ArrayList;

public class NeuralNetworkBuilder {
    private ArrayList<Layer> layers = new ArrayList<>();

    public void addFullyConnectedLayer(int size, Activation a){
        if(layers.isEmpty())
            layers.add(new InputLayer(size, a));
    }

    public NeuralNetwork build(){
        Layer last = layers.get(layers.size() - 1);
        layers.set(layers.size() - 1,new OutputLayer(last.getNodeCount(),layers.get(layers.size() - 2).getNodeCount(),last.getActivation()));
        NeuralNetwork n = new NeuralNetwork(layers);
        return n;
    }
}
