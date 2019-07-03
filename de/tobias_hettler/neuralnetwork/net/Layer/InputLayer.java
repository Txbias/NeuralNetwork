package de.tobias_hettler.neuralnetwork.net.Layer;

import de.tobias_hettler.neuralnetwork.neurons.InputNeuron;

import java.util.ArrayList;

/**
 * The first Layer in the NeuralNetwork
 * Takes the inputs for the NN
 */
public class InputLayer extends Layer {


    public ArrayList<InputNeuron> neurons = new ArrayList<>();


    public InputLayer(int amountNeurons) {
        if(amountNeurons <= 0) {
            throw  new IllegalArgumentException();
        }

        for(int i = 0; i < amountNeurons; i++) {
           InputNeuron in = new InputNeuron();
           neurons.add(in);
        }
    }


}
