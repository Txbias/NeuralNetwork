package de.tobias_hettler.neuralnetwork.net.Layer;

import de.tobias_hettler.neuralnetwork.neurons.WorkingNeuron;
import de.tobias_hettler.neuralnetwork.activationfunctions.ActivationFunction;

import java.util.ArrayList;

/**
 * Optional Layers between the InputLayer and the OutputLayer
 */
public class HiddenLayer extends Layer{

    public ArrayList<WorkingNeuron> neurons = new ArrayList<>();

    public HiddenLayer(int amountNeurons) {
        if(amountNeurons <= 0) {
            throw  new IllegalArgumentException();
        }

        for(int i = 0; i < amountNeurons; i++) {
            WorkingNeuron wn = new WorkingNeuron();
            wn.setActivationFunction(ActivationFunction.ActivationReLU);
            neurons.add(wn);
        }
    }


}
