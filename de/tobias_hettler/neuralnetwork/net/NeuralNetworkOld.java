package de.tobias_hettler.neuralnetwork.net;

import de.tobias_hettler.neuralnetwork.activationfunctions.ActivationFunction;
import de.tobias_hettler.neuralnetwork.neurons.InputNeuron;
import de.tobias_hettler.neuralnetwork.neurons.WorkingNeuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetworkOld {

    private List<InputNeuron> inputNeurons = new ArrayList<>();
    private List<WorkingNeuron> outputNeurons = new ArrayList<>();
    private List<WorkingNeuron> hiddenNeuronsLayerOne = new ArrayList<>();
    private List<WorkingNeuron> hiddenNeuronsLayerTwo = new ArrayList<>();


    //Create a outputneuron
    public WorkingNeuron createNewOutput() {
        WorkingNeuron wn = new WorkingNeuron();
        outputNeurons.add(wn);
        return wn;
    }

    //Create  a inputneuron
    public InputNeuron createNewInput() {
        InputNeuron in = new InputNeuron();
        inputNeurons.add(in);
        return in;
    }

    public void createHiddenNeuons(int amount, int layers) {
        if(amount <= 0) {
           throw new IllegalArgumentException();
        }
        if(layers <= 0) {
            throw new IllegalArgumentException();
        }

        if(layers == 1) {
            for (int i = 0; i < amount; i++) {
                hiddenNeuronsLayerOne.add(new WorkingNeuron());
            }
        } else if(layers == 2) {
            for(int i = 0; i < amount; i++) {
                hiddenNeuronsLayerOne.add(new WorkingNeuron());
                hiddenNeuronsLayerTwo.add(new WorkingNeuron());
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void reset() {
        for(WorkingNeuron wn : outputNeurons) {
            wn.reset();
        }
        for(WorkingNeuron wn : hiddenNeuronsLayerOne) {
            wn.reset();
        }
        for(WorkingNeuron wn : hiddenNeuronsLayerTwo) {
            wn.reset();
        }
    }

    //Create inputneurons
    public void createNewInputs(int amount) {
        for(int i = 0; i < amount; i++) {
            InputNeuron in = new InputNeuron();
            inputNeurons.add(in);
        }
    }

    //Create outputneurons
    public void createNewOutputs(int amount) {
        for(int i = 0; i < amount; i++) {
            WorkingNeuron wn = new WorkingNeuron();
            outputNeurons.add(wn);
        }
    }

    //Connect all neurons together with random weights
    public void createFullMesh() {
        if(hiddenNeuronsLayerOne.size() == 0) {
            for(WorkingNeuron wn : outputNeurons) {
                for(InputNeuron in : inputNeurons) {
                    Random r = new Random();
                    float weight = 0 + r.nextFloat() * (1 - 0);
                    wn.addConnection(new Connection(in, weight));
                }
            }
        } else if(!(hiddenNeuronsLayerOne.size() == 0) && hiddenNeuronsLayerTwo.size() == 0){
            for(WorkingNeuron wn : outputNeurons) {
                for(WorkingNeuron hidden : hiddenNeuronsLayerOne) {
                    Random r = new Random();
                    float weight = 0 + r.nextFloat() * (1 - 0);
                    wn.addConnection(new Connection(hidden, weight));
                }
            }
            for(WorkingNeuron hidden : hiddenNeuronsLayerOne) {
                for(InputNeuron in : inputNeurons) {
                    Random r = new Random();
                    float weight = 0 + r.nextFloat() * (1 - 0);
                    hidden.addConnection(new Connection(in, weight));
                }
            }
        } else if(!(hiddenNeuronsLayerTwo.size() == 0)) {
            //TODO
        }


    }

    //Connect all neurons with given weights
    public void createFullMesh(float... weights) {
        if(hiddenNeuronsLayerOne.size() == 0) {
            if(weights.length != inputNeurons.size() * outputNeurons.size()) {
                throw new RuntimeException();
            }

            int index = 0;

            for(WorkingNeuron wn : outputNeurons) {
                for(InputNeuron in : inputNeurons) {
                    wn.addConnection(new Connection(in, weights[index]));
                    index++;
                }
            }
        } else {
            if(weights.length != inputNeurons.size() * hiddenNeuronsLayerOne.size() + hiddenNeuronsLayerOne.size() * outputNeurons.size() ) {
                throw new RuntimeException();
            }

            int index = 0;

            for(WorkingNeuron hidden : hiddenNeuronsLayerOne) {
                for(InputNeuron in : inputNeurons) {
                    hidden.addConnection(new Connection(in, weights[index]));
                    index++;
                }
            }
            for(WorkingNeuron out : outputNeurons) {
                for(WorkingNeuron hidden : hiddenNeuronsLayerOne) {
                    out.addConnection(new Connection(hidden, weights[index]));
                    index++;
                }
            }

        }

    }

    //Changes the activationfunction th the given
    public void setActivationFunctions(ActivationFunction activationFunction) {
        for(WorkingNeuron wn : outputNeurons) {
            wn.setActivationFunction(activationFunction);
        }
    }

    public void backpropagation(float[] shoulds, float epsilon) {
        if(shoulds.length != outputNeurons.size()) {
            throw new IllegalArgumentException();
        }

        reset();

        for(int i = 0; i < shoulds.length; i++) {
            outputNeurons.get(i).calculateOutputDelta(shoulds[i]);
        }

        if(hiddenNeuronsLayerOne.size() > 0) {
            for(int i = 0; i< shoulds.length; i++) {
                outputNeurons.get(i).backpropagateSmallDelta();
            }
        }

        for(int i = 0; i < shoulds.length; i++) {
            outputNeurons.get(i).deltaLearning(epsilon);
        }
        for(int i = 0; i< hiddenNeuronsLayerOne.size(); i++) {
            hiddenNeuronsLayerOne.get(i).deltaLearning(epsilon);
        }


    }


}
