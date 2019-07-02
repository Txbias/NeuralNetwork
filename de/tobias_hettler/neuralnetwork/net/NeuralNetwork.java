package de.tobias_hettler.neuralnetwork.net;

import de.tobias_hettler.neuralnetwork.activationfunctions.ActivationFunction;
import de.tobias_hettler.neuralnetwork.net.Layer.HiddenLayer;
import de.tobias_hettler.neuralnetwork.net.Layer.OutputLayer;
import de.tobias_hettler.neuralnetwork.neurons.InputNeuron;
import de.tobias_hettler.neuralnetwork.neurons.WorkingNeuron;
import de.tobias_hettler.neuralnetwork.net.Layer.InputLayer;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {

    private ArrayList<HiddenLayer> hiddenLayers = new ArrayList<>();
    private InputLayer inputLayer;
    private OutputLayer outputLayer;
    private boolean hasInputLayer = false;
    private boolean hasOutputLayer = false;
    private int trainingSample = 0;
    private int batchSize = 1;

    /**
     * Creates a new InputLayer with given amount of neurons
     */
    public InputLayer createInputLayer(int amountNeurons) {
        if (amountNeurons <= 0) {
            throw new IllegalArgumentException();
        }

        InputLayer il = new InputLayer(amountNeurons);
        hasInputLayer = true;
        inputLayer = il;

        return il;
    }

    /**
     * Create a new HiddenLayer with given amount of neurons
     */
    public void createHiddenLayer(int amountNeurons) {
        if (amountNeurons <= 0) {
            throw new IllegalArgumentException();
        }

        HiddenLayer hl = new HiddenLayer(amountNeurons);
        hiddenLayers.add(hl);
    }

    /**
     * Creates multiple HiddenLayer with given amount of neurons
     */
    public void createHiddenLayer(int amountNeurons, int amountLayer) {
        for (int i = 0; i < amountLayer; i++) {
            createHiddenLayer(amountNeurons);
        }
    }

    /**
     * Creates a new OutputLayer with the given amount of neurons
     */
    public OutputLayer createOutputLayer(int amountNeurons) {
        if (amountNeurons <= 0) {
            throw new IllegalArgumentException();
        }

        OutputLayer ol = new OutputLayer(amountNeurons);
        hasOutputLayer = true;
        outputLayer = ol;

        return ol;
    }

    /**
     * Resets all neurons
     */
    public void reset() {
        for (int i = 0; i < hiddenLayers.size(); i++) {
            for (int j = 0; j < hiddenLayers.get(i).neurons.size(); j++) {
                hiddenLayers.get(i).neurons.get(j).reset();
            }
        }

        for (int i = 0; i < outputLayer.neurons.size(); i++) {
            outputLayer.neurons.get(i).reset();
        }
    }

    /**
     * Connects the given HiddenLayer with the given InputLayer
     */
    private void connectHiddenInput(HiddenLayer hiddenLayer, InputLayer inputLayer) {
        ArrayList<InputNeuron> inputNeurons = new ArrayList<>();
        ArrayList<WorkingNeuron> workingNeurons = new ArrayList<>();
        inputNeurons.addAll(inputLayer.neurons);
        workingNeurons.addAll(hiddenLayer.neurons);

        Random r = new Random();
        for (WorkingNeuron wn : workingNeurons) {
            for (InputNeuron in : inputNeurons) {
                float weight = r.nextFloat();
                wn.addConnection(new Connection(in, weight));
            }
        }
    }

    /**
     * Connects the given HiddenLayer with another HiddenLayer
     */
    private void connectHiddenHidden(HiddenLayer hiddenLayer, HiddenLayer hiddenLayer2) {
        ArrayList<WorkingNeuron> hiddenNeurons = new ArrayList<>();
        ArrayList<WorkingNeuron> hiddenNeurons2 = new ArrayList<>();
        hiddenNeurons.addAll(hiddenLayer.neurons);
        hiddenNeurons2.addAll(hiddenLayer2.neurons);

        Random r = new Random();
        for (WorkingNeuron wn2 : hiddenNeurons2) {
            for (WorkingNeuron wn : hiddenNeurons) {
                float weight = r.nextFloat();
                wn2.addConnection(new Connection(wn, weight));
            }
        }
    }

    /**
     * Connects the given OutputLayer with the given HiddenLayer
     */
    private void connectOutputHidden(OutputLayer outputLayer, HiddenLayer hiddenLayer) {
        ArrayList<WorkingNeuron> hiddenNeurons = new ArrayList<>();
        ArrayList<WorkingNeuron> outputNeurons = new ArrayList<>();
        hiddenNeurons.addAll(hiddenLayer.neurons);
        outputNeurons.addAll(outputLayer.neurons);

        Random r = new Random();
        for (WorkingNeuron on : outputNeurons) {
            for (WorkingNeuron wn : hiddenNeurons) {
                float weight = r.nextFloat();
                on.addConnection(new Connection(wn, weight));
            }
        }
    }

    /**
     * Connects a given OutputLayer with a given InputLayer
     */
    private void connectOutputInput(OutputLayer outputLayer, InputLayer inputLayer) {
        ArrayList<WorkingNeuron> outputNeurons = new ArrayList<>();
        ArrayList<InputNeuron> inputNeurons = new ArrayList<>();
        outputNeurons.addAll(outputLayer.neurons);
        inputNeurons.addAll(inputLayer.neurons);

        Random r = new Random();
        for (WorkingNeuron on : outputNeurons) {
            for (InputNeuron in : inputNeurons) {
                float weight = r.nextFloat();
                on.addConnection(new Connection(in, weight));
            }
        }
    }


    /**
     * Connects all Layer in the NeuralNetwork with random weights
     */
    public void createFullMesh() {
        if (hasInputLayer && hasOutputLayer) {
            if (inputLayer == null | outputLayer == null) {
                System.out.println("Please create an Inputlayer and an Outputlayer.");
                throw new NullPointerException();
            }

            if (hiddenLayers.size() == 0) {
                connectOutputInput(outputLayer, inputLayer);
            } else if (hiddenLayers.size() == 1) {
                connectOutputHidden(outputLayer, hiddenLayers.get(0));
                connectHiddenInput(hiddenLayers.get(0), inputLayer);
            } else {
                connectOutputHidden(outputLayer, hiddenLayers.get(hiddenLayers.size() - 1));
                for (int i = hiddenLayers.size() - 1; i > 0; i--) {
                    connectHiddenHidden(hiddenLayers.get(i), hiddenLayers.get(i - 1));
                }

                connectHiddenInput(hiddenLayers.get(0), inputLayer);
            }

        }
    }

    /**
     * Connects all Layer in the NeuralNetwork with the given weights
     * @param weights Weights for the Connections
     */
    public void createFullMesh(float... weights) {
        if(hiddenLayers.size() == 0) {
            if(weights.length != inputLayer.neurons.size() * outputLayer.neurons.size()) {
                throw new RuntimeException();
            }

            int index = 0;

            for(WorkingNeuron wn : outputLayer.neurons) {
                for(InputNeuron in : inputLayer.neurons) {
                    wn.addConnection(new Connection(in, weights[index]));
                    index++;
                }
            }
        } else {

            int index = 0;

            for(WorkingNeuron wn : outputLayer.neurons) {
                for(WorkingNeuron hidden : hiddenLayers.get(hiddenLayers.size() - 1).neurons) {
                    wn.addConnection(new Connection(hidden, weights[index]));
                    index++;
                }
            }

            for (int i = hiddenLayers.size(); i > 1; i--) {
                for (WorkingNeuron wn : hiddenLayers.get(i - 1).neurons) {
                    for(WorkingNeuron hidden : hiddenLayers.get(i - 2).neurons) {
                        wn.addConnection(new Connection(hidden, weights[index]));
                        index++;
                    }
                }
            }

            for(WorkingNeuron wn: hiddenLayers.get(0).neurons) {
                for(InputNeuron in : inputLayer.neurons) {
                    wn.addConnection(new Connection(in, weights[index]));
                    index++;
                }
            }

        }
    }

    /**
     * Sets the ActivationFunction for all OutputNeurons
     */
    public void setActivationFunction(ActivationFunction activationFunction) {
        if (!hasOutputLayer) {
            System.out.println("Please create an Outputlayer than set the Activationfunction.");
            throw new NullPointerException();
        }
        for (WorkingNeuron wn : outputLayer.neurons) {
            wn.setActivationFunction(activationFunction);
        }
    }

    /**
     * Trains the NeuralNetwork with the Backpropagation method
     */
    public void backpropagation(float[] shoulds, float epsilon) {
        if (!hasOutputLayer) {
            System.out.println("Please create first an Outputlayer than do backpropagation.");
            throw new NullPointerException();
        }
        if (shoulds.length != outputLayer.neurons.size()) {
            throw new IllegalArgumentException();
        }

        reset();
        for (int i = 0; i < shoulds.length; i++) {
            outputLayer.neurons.get(i).calculateOutputDelta(shoulds[i]);
        }

        if (hiddenLayers.size() == 0) {
            for (int i = 0; i < shoulds.length; i++) {
                outputLayer.neurons.get(i).backpropagateSmallDelta();
            }
        }


        for (int i = 0; i < shoulds.length; i++) {
            outputLayer.neurons.get(i).deltaLearning(epsilon);
        }
        for (int i = hiddenLayers.size(); i > 0; i--) {
            for (int j = 0; j < hiddenLayers.get(i - 1).neurons.size(); j++) {
                hiddenLayers.get((i - 1)).neurons.get(j).deltaLearning(epsilon);
            }
        }

        if (trainingSample % batchSize == 0) {
            for (int i = 0; i < shoulds.length; i++) {
                outputLayer.neurons.get(i).applyBatch();
            }

            for (int i = hiddenLayers.size(); i > 0; i--) {
                for (int j = 0; j < hiddenLayers.get(i - 1).neurons.size(); j++) {
                    hiddenLayers.get((i - 1)).neurons.get(j).applyBatch();
                }

            }

            trainingSample++;

        }

    }

    /**
     * Saves the NeuralNetwork to a file
     */
    public void save(String path) {
        boolean hasHiddenLayers = false;
        ArrayList<Float> weights = new ArrayList<>();
        File file = new File(path);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        for(int i = 0; i < outputLayer.neurons.size(); i++) {
            for(Connection c : outputLayer.neurons.get(i).getConnections()) {
                weights.add(c.getWeight());
            }
        }
        if(!(hiddenLayers.size() == 0)) { // has HiddenLayers
            hasHiddenLayers = true;
            for (int i = hiddenLayers.size(); i > 0; i--) {
                for (int j = 0; j < hiddenLayers.get(i - 1).neurons.size(); j++) {
                    for(Connection c : hiddenLayers.get(i - 1).neurons.get(j).getConnections()) {
                        weights.add(c.getWeight());
                    }
                }

            }
        }

        try {
            FileWriter fw = new FileWriter(path);
            fw.write("InputNeurons: " + inputLayer.neurons.size() + "~");
            fw.write("HiddenLayer: " + hiddenLayers.size() + "~");
            if(hasHiddenLayers) {
                fw.write("HiddenNeurons: " + hiddenLayers.get(0).neurons.size() + "~");
            } else {
                fw.write("HiddenNeurons: 0~");
            }
            fw.write("OutputNeurons: " + outputLayer.neurons.size() + "~");
            fw.write("#");
            for(Float f : weights) {
                fw.write(f.toString() + "#");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * deletes all Connections of the neuralNetwork
     */
    private void deleteConnections() {
        for(WorkingNeuron wn : outputLayer.neurons) {
            wn.deleteConnections();
        }

        if(hiddenLayers.size() != 0) {
            for(int i = 0; i < hiddenLayers.size(); i++) {
                for(WorkingNeuron wn : hiddenLayers.get(i).neurons) {
                    wn.deleteConnections();
                }
            }
        }
    }

    /**
     * The NeuralNetwork predicts outputs for given inputs
     */
    public float[] predict(float... inputs) {
        if(inputs.length != inputLayer.neurons.size()) {
            throw new IllegalArgumentException();
        }
        if(!hasInputLayer | !hasOutputLayer) {
            System.out.println("Please create first the required Layers");
            throw new NullPointerException();
        }

        float[] output = new float[outputLayer.neurons.size()];
        for(int i = 0; i < inputLayer.neurons.size(); i++) {
            inputLayer.neurons.get(i).setValue(inputs[i]);
        }
        for(int j = 0; j < outputLayer.neurons.size(); j++) {
            output[j] = outputLayer.neurons.get(j).getValue();
        }

        return output;
    }

    /**
     * Loads a NeuralNetwork from a file
     */
    public static NeuralNetwork load(String path) {
        File file = new File(path);
        if(!file.exists()) {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String weightsAsString = "";
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while((line = br.readLine()) != null) {
                weightsAsString = weightsAsString + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Float> weights = new ArrayList<>();

        /*for(String s : weightsAsString.split("#")) {
            float weightAsFloat = Float.valueOf(s);
            weights.add(weightAsFloat);
        } */

        String[] tmp = weightsAsString.split("#");
        String meta = "";

        for(int i = 0; i < tmp.length; i++) {
            if(i == 0) {
                meta = tmp[0];
            } else {
                float weightAsFloat = Float.valueOf(tmp[i]);
                weights.add(weightAsFloat);
            }
        }

        String[] metavalues = meta.split("~");
        int inputneurons = 0;
        int hiddenlayer = 0;
        int hiddenneurons = 0;
        int outputneurons = 0;
        for(int i = 0; i < metavalues.length; i++) {
            int index = metavalues[i].indexOf(": ");
            metavalues[i] = metavalues[i].substring(index);
            if(i == 0) {
                inputneurons = Integer.parseInt(metavalues[i].substring(2));
            } else if(i == 1) {
                hiddenlayer = Integer.parseInt(metavalues[i].substring(2));
            } else if(i == 2) {
                hiddenneurons = Integer.parseInt(metavalues[i].substring(2));
            } else if(i == 3) {
                outputneurons = Integer.parseInt(metavalues[i].substring(2));
            }
        }


        float[] weightsArray = new float[weights.size()];
        int index = 0;

        for(Float f : weights) {
            weightsArray[index++] = (f != null ? f : 0.5f);
        }
        for(int i = 0; i < weightsArray.length; i++) {
            System.out.println(weightsArray[i]);
        }



        NeuralNetwork nn = new NeuralNetwork();

        InputLayer inputLayer = nn.createInputLayer(inputneurons);
        if(hiddenlayer > 0)  {
            nn.createHiddenLayer(hiddenneurons, hiddenlayer);
        }
        OutputLayer outputLayer = nn.createOutputLayer(outputneurons);

        nn.deleteConnections();
        nn.reset();
        nn.setActivationFunction(ActivationFunction.ActivationIdentity);

        nn.createFullMesh(weightsArray);

        return nn;
    }


    /**
     * Sets the batchsize of the NeuralNetwork
     */
    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    /**
     * @return current InputLayer
     */
    public InputLayer getInputLayer() {
        return this.inputLayer;
    }

    /**
     * @return current HiddenLayers
     */
    public ArrayList<HiddenLayer> getHiddenLayers() {
        return this.hiddenLayers;
    }

    /**
     * @return current OutputLayer
     */
    public OutputLayer getOutputLayer() {
        return this.outputLayer;
    }

}
