package de.tobias_hettler.neuralnetwork.datasets;

import java.io.*;
import java.util.ArrayList;

/**
 * The Manager for the DataSets with Methods to load the DataSets
 */
public class DataSetManager {


    /**
     * Creates a TextToTextDataSet with the content of the given file
     * @param file The File of the TextToTextDataSet
     * @throws FileNotFoundException if the given File is not existing the Exception will be thrown
     * @return A TextToTextDataSet object
     */
    public static TextToTextDataSet loadTextToText(File file) throws FileNotFoundException{
        if(!file.exists()) {
            throw new FileNotFoundException();
        }
        String line;
        ArrayList<String> fileContent = new ArrayList<>();
        ArrayList<String> inputStrings = new ArrayList<>();
        ArrayList<String> outputStrings = new ArrayList<>();
        ArrayList<ArrayList<Float>> input = new ArrayList<>();
        ArrayList<ArrayList<Float>> output = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while((line = br.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContentString;
        for(int i = 0; i < fileContent.size(); i++) {
            fileContentString = fileContent.get(i).replace("ö", "oe");
            fileContentString = fileContentString.replaceAll("ü", "ue");
            fileContentString = fileContentString.replaceAll("A", "Ae");
            fileContentString = fileContentString.replaceAll("Ö", "Oe");
            fileContentString = fileContentString.replaceAll("Ü", "Ue");

            inputStrings.add(fileContentString.split(":")[0]);
            outputStrings.add(fileContentString.split(":")[1]);
        }
        fileContentString = null;
        fileContent = null;

        int ascii ;
        float asciiFloat;
        for(int i = 0; i < inputStrings.size(); i++) {
            ArrayList<Float> tmp = new ArrayList<>();
            for(int j = 0; j < inputStrings.get(i).length(); j++) {
                char current = inputStrings.get(i).charAt(j);
                ascii = current;
                asciiFloat = (float) ascii / 127;
                //asciiFloat = (float) (((int) (asciiFloat*100)) / 100.0);
                System.out.println("AsciiFloat: " + asciiFloat);
                tmp.add(asciiFloat);
            }
            input.add(tmp);
            tmp = null;
        }

        for(int i = 0; i < outputStrings.size(); i++) {
            ArrayList<Float> tmp = new ArrayList<>();
            for(int j = 0; j < outputStrings.get(i).length(); j++) {
                char current = outputStrings.get(i).charAt(j);
                ascii = current;
                asciiFloat = (float) ascii / 127;
                //asciiFloat = (float) (((int) (asciiFloat*100)) / 100.0);
                tmp.add(asciiFloat);
            }
            output.add(tmp);
            tmp = null;
        }

        return new TextToTextDataSet(input, output);
    }

    /**
     * Creates a TextClassificationDataSet with the content of the given file
     * @param file The File of the DataSet
     * @param amountclasses The amount of classes which are existing
     * @return A TextClassificationDataSet
     * @throws FileNotFoundException If the given File was not found it will throw this exception
     */
    public static TextClassificationDataSet loadTextClassification(File file, int amountclasses) throws FileNotFoundException {
        if(!file.exists()) {
            throw new FileNotFoundException();
        }
        String line;
        ArrayList<String> fileContent = new ArrayList<>();
        ArrayList<String> inputStrings = new ArrayList<>();
        ArrayList<Integer> OutputClassification = new ArrayList<>();
        ArrayList<ArrayList<Float>> input = new ArrayList<>();
        ArrayList<ArrayList<Float>> output = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while((line = br.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < fileContent.size(); i++) {
            String fileContentString = fileContent.get(i).replace("ö", "oe");
            fileContentString = fileContentString.replaceAll("ü", "ue");
            fileContentString = fileContentString.replaceAll("A", "Ae");
            fileContentString = fileContentString.replaceAll("Ö", "Oe");
            fileContentString = fileContentString.replaceAll("Ü", "Ue");

            inputStrings.add(fileContentString.split(":")[0]);
            OutputClassification.add(Integer.valueOf(fileContentString.replaceAll(" ", "").split(":")[1]));
        }

        int ascii;
        float asciiFloat;
        for(int i = 0; i < inputStrings.size(); i++) {
            ArrayList<Float> tmp = new ArrayList<>();
            for(int j = 0; j < inputStrings.get(i).length(); j++) {
                char current = inputStrings.get(i).charAt(j);
                ascii = current;
                asciiFloat = (float) ascii / 127;
                //asciiFloat = (float) (((int) (asciiFloat*100)) / 100.0);
                System.out.println("AsciiFloat: " + asciiFloat);
                tmp.add(asciiFloat);
            }
            input.add(tmp);
            tmp = null;
        }




        return new TextClassificationDataSet(input, OutputClassification);
    }

    /**
     * Returns the corresponding String of an ArrayList of Ascii coded Floats
     * @param floats the Ascii coded Floats
     * @return An String
     */
    public static String returnToString(ArrayList<Float> floats) {

        String returnValue = "";

        for(int i = 0; i < floats.size(); i++) {
            float current = floats.get(i);
            int currentInt = (int) (current * 127);
            System.out.println("currentInt: " + currentInt);
            returnValue = returnValue + String.valueOf(Character.toChars(currentInt));
            System.out.println("ReturnValue: " + returnValue);
        }

        return returnValue;
    }

}
