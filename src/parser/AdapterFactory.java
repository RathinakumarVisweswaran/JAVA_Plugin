package parser;

import interfaces.Adapter;
import neuroph.MLP;
import neuroph.RBF_Adapter;
import xml.Classification;
import xml.XESSPlus;

/**
 * Created by Rathinakumar on 7/15/2015.
 */
public class AdapterFactory {

    public static Adapter getAdapter(XESSPlus xessPlus)
    {
        if(xessPlus.getClassification() != null)
        {
            Classification.Algorithm algo = xessPlus.getClassification().getAlgorithm();
            if(algo.getMultiLayerPerceptron()!=null)
            {
                MLP mlp = new MLP();
                return mlp;
            }
            if(algo.getRadialBasisFunctionNetwork()!=null)
            {
                RBF_Adapter rbf = new RBF_Adapter();
                return rbf;
            }
        }
        return null;
    }
    
}