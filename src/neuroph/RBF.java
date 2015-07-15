package neuroph;

import interfaces.Adapter;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.RBFNetwork;
import org.neuroph.nnet.learning.RBFLearning;
import xml.XESSPlus;

import java.util.Arrays;

/**
 * Created by Rathinakumar on 7/13/2015.
 */
public class RBF implements Adapter {
    @Override
    public String initiateAlgoUsing(XESSPlus xsPlus) {


        // get the path to file with data
        String inputFileName = "data/Sine.txt";

        // create MultiLayerPerceptron neural network
        RBFNetwork neuralNet = new RBFNetwork(1, 15, 1);

        // create training set from file
        DataSet dataSet = DataSet.createFromFile(inputFileName, 1, 1, ",", false);

        RBFLearning learningRule = ((RBFLearning)neuralNet.getLearningRule());
        learningRule.setLearningRate(0.02);
        learningRule.setMaxError(0.01);
        //learningRule.addListener(this);

        // train the network with training set
        neuralNet.learn(dataSet);

        System.out.println("Done training.");
        System.out.println("Testing network...");

        //testNeuralNetwork(neuralNet, dataSet);
        return null;
    }

    /**
     * Prints network output for the each element from the specified training set.
     * @param neuralNet neural network
     * @param testSet test data set
     */
    public void testNeuralNetwork(NeuralNetwork neuralNet, DataSet testSet) {
        for(DataSetRow testSetRow : testSet.getRows()) {
            neuralNet.setInput(testSetRow.getInput());
            neuralNet.calculate();
            double[] networkOutput = neuralNet.getOutput();

            System.out.print("Input: " + Arrays.toString(testSetRow.getInput()) );
            System.out.println(" Output: " + Arrays.toString( networkOutput) );
        }
    }
}
