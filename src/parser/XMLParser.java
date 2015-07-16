package parser;

import interfaces.Adapter;
import xml.XESSPlus;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rathinakumar on 7/12/2015.
 */
public class XMLParser {

    public static void main(String[] args) throws JAXBException, IOException {

        String xmlFile= "src//examples//MLP_Iris.xml";//args[0];
        String outputLocation = "src//output";//args[1];
        XESSPlus xessPlus = XMLParser.parse(xmlFile);
        System.out.println(xessPlus);
        Adapter adapter = AdapterFactory.getAdapter(xessPlus);
        adapter.tranNeuralNetwork(xessPlus, outputLocation);

        //testing
        adapter.testNeuralNetwork("C:\\Users\\Rathinakumar\\IdeaProjects\\JavaPlugin\\src\\output\\MLP_1437005328918\\MLP",
                "C:\\Users\\Rathinakumar\\IdeaProjects\\JavaPlugin\\src\\data\\Iris.csv",
                "C:\\Users\\Rathinakumar\\IdeaProjects\\JavaPlugin\\src\\output\\MLP_1437005328918\\testResult.txt");


    }

    public static XESSPlus parse(String xmlFile) throws JAXBException {
        File file = new File(xmlFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(XESSPlus.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (XESSPlus) jaxbUnmarshaller.unmarshal(file);
    }
}
