package interfaces;

import xml.XESSPlus;

import java.io.IOException;

/**
 * Created by Rathinakumar on 7/11/2015.
 */
public interface Adapter {
    public String initiateAlgoUsing(XESSPlus xsPlus, String outputLoc) throws IOException;
}
