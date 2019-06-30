package de.tobias_hettler.neuralnetwork.neurons;


public class InputNeuron extends Neuron{

    private float Value = 0;


    @Override
    public float getValue() {
        return Value;
    }

    public void setValue(float value) {
        this.Value = value;
    }


}
