package bardevkit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Author: Simon Brereton Ferns
 * 
 * Runs as a jenkins pipeline to add *any* element into Endevor A JSON control
 * file should be provided in the current working directory to provide Endevor
 * details. Details can be overidden using parameters described below
 * 
 * X740087.asm < Assembler source X740087.json < endevor control file
 * 
 * parm 1 = Full element name parm 2 = RACF user ID parm 3 = RACF password parm
 * 4 = Endevor system parm 5 = Endevor subsystem parm 6 = Endevor environment
 * parm 7 = "" type parm 8 = "" CCID parm 9 = "" comment parm 10 = "" override
 * signout flag <Optional> parm 10 = "" Processor group <Optional>
 * 
 * 
 * 
 *
 */
public class App {
    public static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOG.info("Dev kit starting - any help contact SBF");
        int nrArgs = args.length;
        String FullEleName = "", RACFUser = "", RACFPass = "";
        String ndElement = "", ndType = "", ndEnv = "DEVLB", ndSys = "CPY", ndSubSys = "", ndComment = "", ndCC = "",
                ndProcessor = "", ndOveSig = "Y";

        if (nrArgs == 0) {
            LOG.info("No parameters provided, can't continue!");
            return;
        }
        FullEleName = args[0];
        RACFUser = args[1];
        RACFPass = args[2];

        if (nrArgs > 3) {
            int y = 0;
            LOG.info("Endevor overrides detected");
            LOG.info("TODO - endevor overrides");
        }

        ndElement = FullEleName.substring(0, FullEleName.indexOf("."));
        File jsControl = new File("./" + ndElement + ".json");

        JSONObject js = new JSONObject();

        // Get the mandatory values from control file
        try {
            js = GetParms.ReadParams(jsControl);
            ndSys = js.get("ndSys").toString();
            ndSubSys = js.get("ndSubSys").toString();
            ndType = js.get("ndType").toString();
            ndEnv = js.get("ndEnv").toString();
            ndCC = js.get("ndCC").toString();
            ndComment = js.get("ndComment").toString();
        }
        catch (NullPointerException e) {
            LOG.info("A necessary JSON parameter was not found, values currently:" + "\n"
            + "System " + ndSys + "\n" + "Subsystem " + ndSubSys + "\n" + "Environment " + 
            ndEnv + "\n" + "Type " + ndType + "\n" + "CCID " + ndCC + "\n" + "Comment " + 
            ndComment);
            return;
        } 
        catch (IOException e) {
            LOG.info("IO Error on JSON control file");
            e.printStackTrace();
            return;
        }

        // Get the non-mandatory values from control file
        try {
            ndProcessor = js.get("ndProcessor").toString();         
        } catch (Exception e){
            ndProcessor = "DEFAULT";
        }
        try {         
            ndOveSig = js.get("ndOveSig").toString();
        } catch (Exception e){
            ndOveSig = "Y"; 
        }

        try{
        URL endevor = new URL("http://endevor.fill.in");
        } catch (MalformedURLException e) {
            LOG.info("Bad URL, somehow");
            return;
        }



        LOG.info("down here");

    }
}


/*
Use json file to get endevor details, or use parameters if provided
use file extension to determine type, unless provided
json endevor file will be derived from element name, ie

X740087.asm < Assembler source
X740087nd   < endevor control file


*/