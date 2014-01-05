package cichlid_sim.game;

import cichlid_sim.engine.action.PauseActionHandler;
import cichlid_sim.engine.action.TimeManipulationActionHandler;
import cichlid_sim.engine.app.GameAppManager;
import cichlid_sim.engine.json.JSONFileIO;
import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.engine.logger.Logger;

/**
 * This class is responsible for GUI / game world communication. It acts as a proxy
 * for methods in both the game world and the GUI. It farms requests sent from 
 * the GUI to the appropriate game world classes.
 * 
 * It is the programmers responsibility to confirm the JSONObjects passed to these
 * methods are in the correct format. Different parameters may be required for 
 * different objects. See the specific classes (ie: AddObject, RemoveObject) for 
 * additional details.
 *
 * @author Wesley Perry
 */
public class PipeFromGUI {    
    /**
     * Adds the specified object to the game world. This method is intended to 
     * be called from outside the game world when an object should be added to 
     * the game world.
     * 
     * @param object The JSONObject containing all attributes of the object to add.
     */
    public static void addObjectToGameWorld(JSONObject object) {
        if(isGameRunning()) {
            AddObject.addObject(object);
        }
        else {
            Logger.outputToGUI(Logger.Type.ERROR, "Game still loading. Please be patient.");
        }
    }
    
    /**
     * Removes the specified object from the game world. This method is intended
     * to be called from outside the game world when an object should be removed
     * from the game world.
     * 
     * @param objectID The unique identifier of the obejct to remove from the game world.
     * @return The removed object.
     */
    public static Object removeObjectFromGameWorld(int objectID) {
        if(isGameRunning()) {
            return RemoveObject.removeObjectFromGameWorld(objectID);
        }
        else {
            Logger.outputToGUI(Logger.Type.ERROR, "Game still loading. Please be patient.");
            return null;
        }
    }
    /**
     * Update the size of the tank from GUI.
     * @param object  The object contains the key "TankX"
     * "TankY", "TankZ" and "Temperature". These values are in centimeter
     * TankX is the Lenght, TankY is the Height, TankZ is the width
     */
    public static void updateTankSize(JSONObject object){
        //GameWorld.updateTank(object);
        System.out.println(object);
    }
    /**
     * Removes the specified object from the specified tank.
     * 
     * @param objectID The unique ID of the object to remove.
     * @param tankType The tank from which to remove the object.
     * @return The removed object if the removal was successful. null otherwise.
     */
    /*
    public static Object removeObjectFromTank(int objectID, Tank.Type tankType) {
        return RemoveObject.removeObjectFromTank(objectID, tankType);
    }
    */
    
    /**
     * Updates the attributes of a game world object with the given values.
     * 
     * @param object The JSONObject containing an identifier to specify the game
     * world object to update and the new attributes for the specified game world 
     * object.
     */
    public static void updateGameWorldObject(JSONObject object) {
        if(isGameRunning()) {
            if(object.has("ID"))
            {
                removeObjectFromGameWorld(object.getInt("ID"));
                addObjectToGameWorld(object);
            }
        }
        else {
            Logger.outputToGUI(Logger.Type.ERROR, "Game still loading. Please be patient.");
        }
    }
    
    /**
     * Returns a JSONObject containing all the objects stored in the StockTank.
     */
    public static JSONObject getStockTankObjects() {
        return WorldSaver.parseNodeToJSON(NodeCollection.getNode("StockTank"));
    }
    
    /**
     * Returns a JSONObject containing all the objects stored in the IsolationTank.
     */
    public static JSONObject getIsolationTankObjects() {
        return WorldSaver.parseNodeToJSON(NodeCollection.getNode("IsolationTank"));
    }
    
    /**
     * Removes all objects from the world and resets all world variables.
     */
    public static void destroyWorld() {
        RemoveObject.resetWorld();
    }
    
    /**
     *  Loads the game world from the specified file.
     * 
     * @param path The path to the file containing the world data.
     */
    public static void loadWorldFromFile(String path) {
        if(isGameRunning()) {
            destroyWorld();
            WorldBuilder.buildWorldFromJSON(JSONFileIO.retrieveJSONObjectFromFile(path));
        }
        else {
            Logger.outputToGUI(Logger.Type.ERROR, "Game still loading. Please be patient.");
        }
    }
    
    /**
     * Saves the game world to the specified file.
     * 
     * @param path The path to the file in which to store the world data.
     */
    public static void saveWorldToFile(String path) {
        if(isGameRunning()) {
            WorldSaver.saveWorldToFile(path);
        }
        else {
            Logger.outputToGUI(Logger.Type.ERROR, "Game still loading. Please be patient.");
        }
    }
        
    /**
     * Notifies the game world of a pause request event.
     * 
     * @param paused The new value of the paused state. Expected to be true for
     * a pause request and false for an un-pause request.
     */
    public static void requestPauseAction(boolean paused) {
        PauseActionHandler.setPaused(paused);
    }
    
    /**
     * Requests the game world to 'skip' forward by the specified amount of time.
     * 
     * @param seconds How many seconds to skip forward.
     */
    public static void skipForwardInTime(int seconds) {
        TimeManipulationActionHandler.skipForward(seconds);
    }
    
    /**
     * @return True if the game is running. False if it is still loading.
     */
    private static boolean isGameRunning() {
        return GameAppManager.getMainGame().isLoaded();
    }
}
