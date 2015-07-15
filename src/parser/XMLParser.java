package parser;

import neuroph.MLP;
import neuroph.RBF;
import xml.Classification;
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
        File file = new File("src\\examples\\MLP_Iris.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(XESSPlus.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        XESSPlus xessPlus = (XESSPlus) jaxbUnmarshaller.unmarshal(file);
        System.out.println(xessPlus);
        if(xessPlus.getClassification() != null)
        {
            Classification.Algorithm algo = xessPlus.getClassification().getAlgorithm();
            if(algo.getMultiLayerPerceptron()!=null)
            {
                MLP mlp = new MLP();
                mlp.initiateAlgoUsing(xessPlus);
                return;
            }
            if(algo.getRadialBasisFunctionNetwork()!=null)
            {
                RBF rbf = new RBF();
                rbf.initiateAlgoUsing(xessPlus);
                return;
            }
        }
    }
}
