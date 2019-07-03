package de.tobias_hettler.neuralnetwork.net.Layer;

import de.tobias_hettler.neuralnetwork.neurons.Neuron;

import java.util.ArrayList;

/**
 * An abstract parent class for the other Layers
 */
public abstract class Layer {

    public ArrayList<Neuron> neurons = new ArrayList<>();

}
