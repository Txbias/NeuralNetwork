package de.tobias_hettler.neuralnetwork.datasets;

import java.util.ArrayList;
/**
 * A DataSet with a text input and a Integer as output
 */
public class TextClassificationDataSet {

    private final ArrayList<ArrayList<Float>> inputs;
    private final ArrayList<Integer> outputs;

    public TextClassificationDataSet(ArrayList<ArrayList<Float>> inputs, ArrayList<Integer> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public TextClassificationDataSet() {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
    }

    public ArrayList<ArrayList<Float>> getInputs() {
        return inputs;
    }

    public ArrayList<Integer> getOutputs() {
        return outputs;
    }


}
