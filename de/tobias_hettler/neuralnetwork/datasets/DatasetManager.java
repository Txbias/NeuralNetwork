package de.tobias_hettler.neuralnetwork.datasets;

import java.io.*;
import java.util.ArrayList;

public class DatasetManager {


    /**
     * Creates two 2-dimensional ArrayLists with the Inputs and the Outputs in floats between 0 and 1
     * @param file The File of the DataSet
     * @throws FileNotFoundException if the given File is not existing the Exception will be thrown
     * @return A DataSet object
     */
    public static DataSet loadText(File file) throws FileNotFoundException{
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
        for(int i = 0; i < fileContent.size(); i++) {
            String fileContentString = fileContent.get(i).replace("ö", "oe");
            fileContentString = fileContentString.replaceAll("ü", "ue");
            fileContentString = fileContentString.replaceAll("A", "Ae");
            fileContentString = fileContentString.replaceAll("Ö", "Oe");
            fileContentString = fileContentString.replaceAll("Ü", "Ue");

            inputStrings.add(fileContentString.split(":")[0]);
            outputStrings.add(fileContentString.split(":")[1]);
        }

        for(String s : inputStrings) {
            for(int i = 0; i < s.length(); i++) {
                int ascii = s.charAt(i);
                float asciiFloat = ascii / 127;
                s = s.replaceAll(String.valueOf(s.charAt(i)), String.valueOf(asciiFloat)) + "/";
            }
        }

        for(String s : outputStrings) {
            for(int i = 0; i < s.length(); i++) {
                int ascii = s.charAt(i);
                float asciiFloat = ascii / 127;
                s = s.replaceAll(String.valueOf(s.charAt(i)), String.valueOf(asciiFloat)) + "/";
            }
        }

        for(int i = 0; i < inputStrings.size(); i++) {
            String[] floatAsString = inputStrings.get(i).split("/");
            ArrayList<Float> floats = new ArrayList<>();
            for(int j = 0; j < floatAsString.length; j++) {
                floats.add(Float.parseFloat(floatAsString[j]));
            }
            input.add(floats);
        }

        for(int i = 0; i < outputStrings.size(); i++) {
            String[] floatAsString = outputStrings.get(i).split("/");
            ArrayList<Float> floats = new ArrayList<>();
            for(int j = 0; j < floatAsString.length; j++) {
                floats.add(Float.parseFloat(floatAsString[j]));
            }
            output.add(floats);
        }
        return new DataSet(input, output);
    }

}
