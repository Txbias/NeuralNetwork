package de.tobias_hettler.neuralnetwork.net.Layer;

import de.tobias_hettler.neuralnetwork.neurons.WorkingNeuron;

import java.util.ArrayList;

/**
 * The last Layer in the NeuralNetwork
 */
public class OutputLayer extends Layer {


    public ArrayList<WorkingNeuron> neurons = new ArrayList<>();

    public OutputLayer(int amountNeurons) {
        if(amountNeurons <= 0) {
            throw  new IllegalArgumentException();
        }

        for(int i = 0; i < amountNeurons; i++) {
            WorkingNeuron wn = new WorkingNeuron();
            neurons.add(wn);
        }
    }

}
