package neuroph;

import interfaces.Adapter;
import org.neuroph.core.Layer;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;
import xml.XESSPlus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Rathinakumar on 7/11/2015.
 */
public class MLP implements Adapter {


    @Override
    public String initiateAlgoUsing(XESSPlus xsPlus, String outputLoc) throws IOException {
        // TODO Auto-generated method stub
        // get the path to file with data


        /*
        	DONE <xs:element name="datafile" type="xs:string"/>
			<xs:element name="input" type="xs:int"/>
			<xs:element name="output" type="xs:int"/>
			<xs:element name="classes" type="xs:int"/>
			<xs:element name="split" type="xs:decimal"/>
			<xs:element name="delimiter" type="xs:string"/>
         */

        xml.Classification xmlClassification = xsPlus.getClassification();
        xml.MultiLayerPerceptron xmlMLP = xmlClassification.getAlgorithm().getMultiLayerPerceptron();



        String inputFileName = xmlClassification.getDatafile();
        int inputNeurons = xmlClassification.getInput();
        int outputNeurons = xmlClassification.getOutput();
        String hiddenLayers = xmlMLP.getHiddenLayers();

        String delimiter = xmlClassification.getDelimiter();

        double learningRate = Double.parseDouble(xmlMLP.getLearningRate().toString());
        String inputLayerActivation = xmlMLP.getInputLayerActivation();
        String outputLayerActivation = xmlMLP.getOutputLayerActivation();
        String hiddenLayerActivation = xmlMLP.getHiddenLayerActivation();
        int epochs = xmlMLP.getEpochs();

        int classes = xmlClassification.getClasses();
        //double momentum = Double.parseDouble(xmlMLP.getMomentum().toString());


        NeuronProperties inputProperties = new NeuronProperties(),
                outputProperties = new NeuronProperties(),
                hiddenProperties = new NeuronProperties();

        inputProperties.setProperty("transferFunction", inputLayerActivation);
        outputProperties.setProperty("transferFunction", outputLayerActivation);
        hiddenProperties.setProperty("transferFunction", hiddenLayerActivation);

        Layer inputLayer = new Layer(inputNeurons, inputProperties),
                outputLayer = new Layer(inputNeurons, inputProperties);

        // create MultiLayerPerceptron neural network
        MultiLayerPerceptron neuralNet = new MultiLayerPerceptron();
        neuralNet.addLayer(inputLayer);
        for(String n : hiddenLayers.split(","))
            neuralNet.addLayer(new Layer(Integer.parseInt(n), hiddenProperties));
        neuralNet.addLayer(outputLayer);

        // create training set from file
        DataSet trainDataSet = DataSet.createFromFile(inputFileName, inputNeurons, outputNeurons, delimiter, false);
        // train the network with training set

        neuralNet.getLearningRule().setLearningRate(learningRate);
        neuralNet.getLearningRule().setMaxIterations(epochs);


        neuralNet.learn(trainDataSet);

        //saving the model
        File output = new File("output\\MLP_"+System.currentTimeMillis());
        File input = new File(inputFileName);
        if(output.mkdir())
            Files.copy(input.toPath(), output.toPath());

        System.out.println("Done training.");
        System.out.println("Testing network...");
        return null;
    }
}
