package cichlid_sim.game;

import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.engine.logger.Logger;
import cichlid_sim.gui.EditMenu;
import cichlid_sim.gui.LogReport;
import cichlid_sim.gui.MainGUI;

/**
 * This class is responsible for GUI / game world communication. It acts as a proxy
 * for methods in both the game world and the GUI. It farms requests sent from 
 * the game world to the appropriate GUI classes.
 * 
 * It is the programmers responsibility to confirm the JSONObjects passed to these
 * methods are in the correct format. Different parameters may be required for 
 * different objects. See the specific classes (ie: AddObject, RemoveObject) for 
 * additional details.
 *
 * @author Wesley Perry
 */
public class PipeToGUI {    
    /**
     * Notifies the GUI that a game world object was selected by the user for
     * attribute viewing and/or editing.
     * 
     * @param object The JSONObject containing all attributes of the selected object.
     */
    public static void gameWorldObjectSelected(JSONObject object) {
        EditMenu.init(object,1);
    }
    
    /**
     * This method provides a way for the game world to pass the game time to the
     * GUI for display to the user.
     * 
     * @param gameTimeInSeconds The current game time in Seconds.
     */
    public static void setGameClock(double gameTimeInSeconds) {
        MainGUI.setClock(gameTimeInSeconds);
        
    }
    
    /**
     * Forwards the game speed to the GUI to display to the user.
     * 
     * @param gameSpeed The current speed of the game.
     */
    public static void setGameSpeedDisplay(float gameSpeed) {
        //Static method in GUI to set the display of gameSpeed;
        MainGUI.speedDisplay(gameSpeed);
    }
    
    /**
     * This method forwards an output string to the GUI.
     * 
     * @param type The type of output (info, error, bite, chase, etc).
     * @param output The string to be output.
     */
    public static void logOutput(Logger.Type type, String output) {
        //Static method in GUI package for outputting logging info.
        //The below line should be removed once GUI output is working;
        //LogReport.logReports(type,output);
        System.err.println(""+type+" "+output);
      
    }
}
