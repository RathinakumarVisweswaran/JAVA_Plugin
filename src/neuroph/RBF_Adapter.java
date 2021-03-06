package neuroph;

import interfaces.Adapter;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.RBFNetwork;
import org.neuroph.nnet.learning.RBFLearning;
import org.neuroph.util.TransferFunctionType;
import xml.MachineLearning;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Rathinakumar on 7/13/2015.
 */
public class RBF_Adapter implements Adapter {

    @Override
    public String tranNeuralNetwork(MachineLearning machineLearning, String saveLocation) {


        xml.Classification xmlClassification = machineLearning.getClassification();
        xml.RadialBasisFunctionNetwork xmlRBF = xmlClassification.getAlgorithm().getRadialBasisFunctionNetwork();

        String inputFileName = xmlClassification.getDatafile();
        int inputNeurons = xmlClassification.getInput();
        int outputNeurons = xmlClassification.getOutput();
        int hiddenNeurons = xmlRBF.getHiddenNeurons();
        int epochs = xmlRBF.getEpochs();

        double learningRate = Double.parseDouble(xmlRBF.getLearningRate().toString());
        String outputLayerActivation = xmlRBF.getOutputLayerActivation().toUpperCase();

        // create MultiLayerPerceptron neural network
        RBFNetwork neuralNet = new RBFNetwork(inputNeurons, outputNeurons, hiddenNeurons);
        // create training set from file
        DataSet dataSet = DataSet.createFromFile(inputFileName, inputNeurons, outputNeurons, ",", false);

        DataSet.createFromFile(inputFileName, inputNeurons, outputNeurons, ",", false);
        RBFLearning learningRule = ((RBFLearning)neuralNet.getLearningRule());
        learningRule.setLearningRate(learningRate);
        learningRule.setMaxError(epochs);
        //learningRule.addListener(this);

        // train the network with training set
        neuralNet.learn(dataSet);
        saveModel(neuralNet, saveLocation);

        //testNeuralNetwork(neuralNet, dataSet);
        return null;
    }

    private String saveModel(RBFNetwork neuralNet, String saveLocation)
    {
        //saving the model
        File outputDir = new File(saveLocation+"\\"+System.currentTimeMillis());
        //File input = new File(inputFileName);
        if(outputDir.mkdir())
        {
            neuralNet.save(outputDir.getAbsolutePath().concat("\\RBF"));
            System.out.println("saving to " + outputDir.getAbsolutePath().concat("\\RBF"));
        }
        return outputDir.getAbsolutePath().concat("\\RBF");
    }

    public void testNeuralNetwork(String savedModel, String testDataFile, String output) throws IOException {
        File file = new File(savedModel);
        RBFNetwork model = (RBFNetwork) NeuralNetwork.load(new FileInputStream(file));
        DataSet testSet = DataSet.createFromFile(testDataFile, model.getInputsCount(), model.getOutputsCount(), ",", false);

        FileWriter outputFile = new FileWriter(new File(output));

        for(DataSetRow testSetRow : testSet.getRows()) {
            model.setInput(testSetRow.getInput());
            model.calculate();
            double[] networkOutput = model.getOutput();

            outputFile.write("Input: " + Arrays.toString(testSetRow.getInput()));
            outputFile.write(" Output: " + Arrays.toString(networkOutput));
        }
        outputFile.flush();
    }
}
