package neuroph;

import interfaces.Adapter;
import org.neuroph.core.Layer;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.transfer.*;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;
import xml.XESSPlus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rathinakumar on 7/11/2015.
 */
public class MLP implements Adapter {

    public static Map<String, Class<? extends TransferFunction>> transferFunctionMap = new HashMap<>();

    public MLP()
    {
        transferFunctionMap.put("Linear", Linear.class);
        transferFunctionMap.put("Ramp", Ramp.class);
        transferFunctionMap.put("Step", Step.class);
        transferFunctionMap.put("Sigmoid", Sigmoid.class);
        transferFunctionMap.put("Tanh", Tanh.class);
        transferFunctionMap.put("Gaussian", Gaussian.class);
        transferFunctionMap.put("Trapezoid", Trapezoid.class);
        transferFunctionMap.put("Sgn", Sgn.class);
        transferFunctionMap.put("Sin", Sin.class);
        transferFunctionMap.put("Log", Log.class);
    }


    @Override
    public String initiateAlgoUsing(XESSPlus xsPlus) throws IOException {
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


        outputProperties.setProperty("transferFunction", transferFunctionMap.get(outputLayerActivation));
        hiddenProperties.setProperty("transferFunction", transferFunctionMap.get(hiddenLayerActivation));

        //inputProperties.setProperty("transferFunction", new TransferFunctionType(inputLayerActivation));
        //Layer inputLayer = new Layer(inputNeurons, inputProperties),
          Layer outputLayer = new Layer(outputNeurons, outputProperties);

        // create MultiLayerPerceptron neural network
        MultiLayerPerceptron neuralNet = new MultiLayerPerceptron(TransferFunctionType.valueOf(inputLayerActivation.toUpperCase()), inputNeurons, outputNeurons);
        //neuralNet.addLayer(inputLayer);
        for(String n : hiddenLayers.split(","))
            neuralNet.addLayer( neuralNet.getLayersCount()-1,new Layer(Integer.parseInt(n), hiddenProperties));
        //neuralNet.addLayer(outputLayer);

        // create training set from file
        DataSet trainDataSet = DataSet.createFromFile(inputFileName, inputNeurons, outputNeurons, delimiter, false);
        // train the network with training set

        neuralNet.getLearningRule().setLearningRate(learningRate);
        neuralNet.getLearningRule().setMaxIterations(epochs);


        neuralNet.learn(trainDataSet);



        System.out.println("Done training.");
        System.out.println("Testing network...");
        return null;
    }

    private boolean saveModel(MultiLayerPerceptron neuralNet)
    {
        //saving the model
        File outputDir = new File("output\\MLP_"+System.currentTimeMillis());
        //File input = new File(inputFileName);
        if(outputDir.mkdir())
        {
            System.out.println("saving to " + outputDir.getAbsolutePath().concat("\\MLP"));
            neuralNet.save(outputDir.getAbsolutePath().concat("\\MLP"));
            return true;
        }
        return false;
    }

}
