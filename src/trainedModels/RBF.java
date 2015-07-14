package trainedModels;

import interfaces.Adapter;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.RBFNetwork;
import org.neuroph.nnet.learning.RBFLearning;
import xml.XESSPlus;

/**
 * Created by Rathinakumar on 7/13/2015.
 */
public class RBF implements Adapter {
    @Override
    public String initiateAlgoUsing(XESSPlus xsPlus, String outputLoc) {


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
}
