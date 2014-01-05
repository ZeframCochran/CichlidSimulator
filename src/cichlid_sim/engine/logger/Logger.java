package cichlid_sim.engine.logger;

import cichlid_sim.game.PipeToGUI;

/**
 * This class contains methods to allow logging of simulation actions.
 *
 * @author Wesley Perry
 */
public class Logger {
    public enum Type {
        DEBUG,      //Child attached to parent, etc.
        INFO,       //Loading files, adding objects, etc.
        ERROR,      //File not found, etc.
        EXCEPTION   //try{}catch(){print exception}
    }
    
    /**
     * This method sends a string to the GUI to be output.
     * 
     * @param string The string to be output.
     */
    public static void outputToGUI(Type type, String string) {
        PipeToGUI.logOutput(type, string);
    }
}
