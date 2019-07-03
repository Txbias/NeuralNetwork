package de.tobias_hettler.neuralnetwork.datasets;

import java.util.ArrayList;

/**
 * A DataSet with a text input and a text output
 */
public class TextToTextDataSet {

    private final ArrayList<ArrayList<Float>> inputs;
    private final ArrayList<ArrayList<Float>> outputs;

    public TextToTextDataSet(ArrayList<ArrayList<Float>> inputs, ArrayList<ArrayList<Float>> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public ArrayList<ArrayList<Float>> getInputs() {
        return inputs;
    }

    public ArrayList<ArrayList<Float>> getOutputs() {
        return outputs;
    }


}
