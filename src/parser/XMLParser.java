package parser;

import xml.XESSPlus;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Rathinakumar on 7/12/2015.
 */
public class XMLParser {
    public static void main(String[] args) throws JAXBException {
        File file = new File("src\\examples\\RBF.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(XESSPlus.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        XESSPlus customer = (XESSPlus) jaxbUnmarshaller.unmarshal(file);
        System.out.println(customer);

    }
}
