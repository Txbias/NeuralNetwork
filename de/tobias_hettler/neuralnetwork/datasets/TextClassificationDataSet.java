package de.tobias_hettler.neuralnetwork.datasets;

import java.util.ArrayList;

public class TextClassificationDataSet {

    private final ArrayList<ArrayList<Float>> inputs;
    private final ArrayList<Integer> outputs;

    public TextClassificationDataSet(ArrayList<ArrayList<Float>> inputs, ArrayList<Integer> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public ArrayList<ArrayList<Float>> getInputs() {
        return inputs;
    }

    public ArrayList<Integer> getOutputs() {
        return outputs;
    }


}
