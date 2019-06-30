package de.tobias_hettler.neuralnetwork.activationfunctions;

public interface ActivationFunction {
    public static Boolean ActivationBoolean = new Boolean();
    public static Identity ActivationIdentity = new Identity();
    public static HyperbolicTangent ActivationHyperbolicTangent = new HyperbolicTangent();
    public static Sigmoid ActivationSigmoid = new Sigmoid();
    public static ReLU ActivationReLU = new ReLU();
    public float activation(float input);

    public float derivative(float input);
}
