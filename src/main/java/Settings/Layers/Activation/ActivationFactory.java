package Settings.Layers.Activation;

public class ActivationFactory {
    public static ActivationFunction make(Activation a){
        return switch(a){
            case RELU: yield new ReLu();
            case SOFTMAX: yield new Softmax();
        };
    }
}
