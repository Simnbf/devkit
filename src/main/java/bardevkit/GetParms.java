package bardevkit;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;
// Used to read a control file with JSON data in it, returns JSON object.
// We know nothing of the JSON data in this class, caller should parse.

public class GetParms {
	private static final Logger LOG = LoggerFactory.getLogger(GetParms.class);
	public static JSONObject ReadParams(File parmFile) throws IOException{
		FileReader reader = new FileReader(parmFile);		
		JSONParser parse = new JSONParser();
		JSONObject jsbr = null;
		try {
			jsbr = (JSONObject) parse.parse(reader);
		} catch (ParseException e) {
            LOG.info("JSON Parse error on file" + parmFile.toString());
		}				
		return jsbr;
	}
}