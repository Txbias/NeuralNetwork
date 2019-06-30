package de.tobias_hettler.neuralnetwork.neurons;

import de.tobias_hettler.neuralnetwork.net.Connection;
import de.tobias_hettler.neuralnetwork.activationfunctions.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class WorkingNeuron extends Neuron {

    private List<Connection> connections = new ArrayList<>();
    private ActivationFunction activationFunction = ActivationFunction.ActivationSigmoid;
    private float smallDelta = 0;
    private float value = 0;
    private boolean valueClean = false;


    public List<Connection> getConnections() {
        return connections;
    }

    @Override
    public float getValue() {
        if(!valueClean) {
            float sum = 0;
            for(Connection c : connections) {
                sum += c.getValue();
            }
            value = activationFunction.activation(sum);
            valueClean = true;
        }
        return value;
    }

    public void addConnection(Connection c) {
        connections.add(c);
    }

    public void setActivationFunction(ActivationFunction newActivationFunction) {
        activationFunction = newActivationFunction;
    }

    public void deleteConnections() {
        for(Connection c : connections) {
            connections.remove(c);
            c = null;
        }
    }

    public void deltaLearning(float epsilon) {
        float bigDeltaFactor = activationFunction.derivative(getValue()) * epsilon * smallDelta;
        for(int i = 0; i < connections.size(); i++) {
            float bigDelta = bigDeltaFactor * connections.get(i).getNeuron().getValue();
            //System.out.println("Bigdelta: " + bigDelta);
            //System.out.println("Weight before: " + connections.get(i).getWeight());
            connections.get(i).addWeight(bigDelta);
            //System.out.println("Weight after: " + connections.get(i).getWeight());
        }
}

    public void reset() {
        smallDelta = 0;
        valueClean = false;
    }

    public void calculateOutputDelta(float should) {
        smallDelta = should - getValue();
    }

    public void backpropagateSmallDelta() {
        for(Connection c : connections) {
            Neuron n = c.getNeuron();
            if( n instanceof WorkingNeuron) {
                WorkingNeuron wn = (WorkingNeuron) n;
                wn.smallDelta += this.smallDelta * c.getWeight();
            }

        }
    }

    public void applyBatch() {
        for(Connection c : connections) {
            c.applyBatch();
        }
    }


}
