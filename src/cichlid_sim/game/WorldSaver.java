package cichlid_sim.game;

import cichlid_sim.engine.json.JSONFileIO;
import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.game.objects.IGameObject;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * This class handles saving the game world in JSON format.
 *
 * @author Wesley Perry
 */
public class WorldSaver {
    
    /**
     * Saves the game world to the specified file.
     * 
     * @param path The location of the file in which to save the world object.
     */
    public static void saveWorldToFile(String path) {
        JSONFileIO.saveJSONObjectToFile(parseWorldToJSON(), path);
    }
    
    /**
     * Converts the game world objects into a JSONObject.
     * The format of the JSONObject is the following:
     * Each object of IGameObject.Type is stored in a JSONArray with other objects of the same Type.
     * Each of those JSONArrays is stored in a JSONObject with the IGameObject.Type of the objects used as the Key.
     * 
     * @return The game world stored in a JSONObject.
     */
    private static JSONObject parseWorldToJSON() {
        Node gameObjectNode = NodeCollection.getNode("GameObjectNode");
        JSONObject world = new JSONObject();
        
        recurseIntoWorld(world, gameObjectNode);        
        return world;
    }
    
    /**
     * Converts the provided node into a JSONObject.
     * 
     * @param node The node to parse into a JSONObject.
     * @return The node as a JSONObject.
     */
    public static JSONObject parseNodeToJSON(Node node) {
        JSONObject world = new JSONObject();
        
        recurseIntoWorld(world, node);
        return world;
    }
    
    /**
     * Recursive method to travel the game object nodes to collect and convert the
     * game objects into JSONObjects.
     * 
     * @param world The JSONObject containing the game world (so far).
     * @param node The current Node to travel.
     * @return The world parameter with additional game objects added (if any existed in the Node).
     */
    private static JSONObject recurseIntoWorld(JSONObject world, Node node) {
        for(Spatial spatial : node.getChildren()) {
            if(spatial instanceof IGameObject) {                                //Check if it's a game object first (some IGameObjects may be Nodes);
                JSONObject gameObjectJSON = ((IGameObject)spatial).toJSON();    //It's a game object. Convert it to JSON;
                world.append(gameObjectJSON.get("Type").toString(), gameObjectJSON); //And add the JSON to the world JSONObject;
            }
            else {                                                              //It's not a game object;
                if(spatial instanceof Node) {                                   //Maybe it's another node;
                    recurseIntoWorld(world, (Node)spatial);                     //It is. Check that node for game objects;
                }
            }
        }
        return world;
    }
}
