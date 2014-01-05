package cichlid_sim.engine.json;

import cichlid_sim.engine.logger.Logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class handles file-level IO for our implementation of the JSON library.
 *
 * @author Wesley Perry
 */
public class JSONFileIO {    
    /**
     * Writes a single JSONObject to file.
     * 
     * @param object The JSONObject to write to file.
     * @param saveFilePath The location of the file to open (overwrite) or create. 
     */
    public static void saveJSONObjectToFile(JSONObject object, String saveFilePath) {
        PrintWriter pWriter;
        try{
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter(saveFilePath)));
        }
        catch(IOException ioe) {
            Logger.outputToGUI(Logger.Type.ERROR, "ERROR: Save file " + saveFilePath + " cannot be opened or created.");
            return;
        }
        
        pWriter.print(object.toString());
        pWriter.close();
    }
    
    /**
     * Loads all JSONObjects from the specified file. Requires objects to be stored
     * in a single JSONObject on a single line.
     * 
     * @param loadFilepath The location of the file which stores the JSONObject.
     */
    public static JSONObject retrieveJSONObjectFromFile(String loadFilePath) {
        BufferedReader bReader;
        try {
            bReader = new BufferedReader(new FileReader(loadFilePath));
        }
        catch(IOException ioe) {
            Logger.outputToGUI(Logger.Type.ERROR, "Error: Cannot open " + loadFilePath + " for reading.");
            return null;
        }
        try {
            String strFromFile = bReader.readLine();
            return new JSONObject(strFromFile);
        }
        catch(IOException ioe) {
            Logger.outputToGUI(Logger.Type.EXCEPTION, ioe.getMessage());
            return null;
        }
    }
}
