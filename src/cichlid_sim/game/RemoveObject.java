package cichlid_sim.game;

import cichlid_sim.engine.action.TimeManipulationActionHandler;
import cichlid_sim.engine.logger.Logger;
import cichlid_sim.engine.scene.CustomNode;
import cichlid_sim.game.objects.IGameObject;
import cichlid_sim.game.objects.Tank;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * This class handles the removal of objects from the simulation.
 *
 * @author Wesley Perry
 */
public class RemoveObject {
    /**
     * Removes the object defined by the provided objectID from the game world.
     * 
     * @param objectID The unique ID of the object to remove from the game world.
     * @return The removed object.
     */
    public static Object removeObjectFromGameWorld(int objectID) {
        Node gameObjectNode = NodeCollection.getNode("GameObjectNode");
        Object removedObject = removeGameObjectFromNode(gameObjectNode, objectID);
        if(removedObject == null) {
            Logger.outputToGUI(Logger.Type.ERROR, "Could not remove object with ID " + objectID + " from node " + gameObjectNode.getName() + ".");
        }
        else {
            Logger.outputToGUI(Logger.Type.INFO, "Removed from the game world: " + removedObject);
        }
        return null;
    }
    
    /**
     * Removes the specified object from the specified node.
     * 
     * @param node The node from which to remove the object.
     * @param objectID The object to remove.
     * @return The removed object. Null if the object was not found in the specified node.
     */
    private static Object removeGameObjectFromNode(Node node, int objectID) {
        Object retObj;
        for(Spatial spatial : node.getChildren()) {
            if(spatial instanceof IGameObject) {
                if(objectID == ((IGameObject)spatial).getUniqueID()) {
                    node.detachChild(spatial);
                    return spatial;
                }
            }
            else {
                if(spatial instanceof Node) {
                     retObj = removeGameObjectFromNode((Node)spatial, objectID);
                     if(retObj != null) {
                         return retObj;
                     }
                }
            }
        }
        return null;
    }
    
    /**
     * This method removes all game objects and resets world variables to their defaults.
     */
    public static void resetWorld() {
        CustomNode gameObjectNode = (CustomNode)NodeCollection.getNode("GameObjectNode");
        removeGameObjectsFromNode(gameObjectNode);
        Tank.reset();
        TimeManipulationActionHandler.setGameTime(0);
    }
    
    /**
     * This method recursivly removes any IGameObject from the specified node.
     * 
     * @param node The node from which to remove all IGameObjects.
     */
    private static void removeGameObjectsFromNode(Node node) {
        for(Spatial child : node.getChildren()) {
            if(child instanceof IGameObject) {
                removeObjectFromGameWorld(((IGameObject)child).getUniqueID());
            }
            else if(child instanceof Node) {
                removeGameObjectsFromNode((Node)child);
            }
        }
    }
    
    /*
     * The below methods are remants of an attempt to switch from Node to Array
     * storage of game world objects.
     */
    
    /**
     * Removes the specified object from the specified tank.
     * 
     * @param objectID The unique ID of the object to remove.
     * @param tankType The tank from which to remove the object.
     * @return The removed object if the removal was successful. null otherwise.
     */
    /*
    public static Object removeObjectFromTank(int objectID, Tank.Type tankType) {
        Object object;
        switch(tankType) {
            case ARENA : return removeObjectFromGameWorld(objectID);
            case STOCK : object = removeObjectFromArray(objectID, (ArrayList<IGameObject>) GameObjectCollection.getArray("StockTank")); if(object == null) {break;} else {return object;}
            case ISOLATION : object = removeObjectFromArray(objectID, (ArrayList<IGameObject>) GameObjectCollection.getArray("IsolationTank")); if(object == null) {break;} else {return object;}
            default : Logger.outputToGUI(Logger.Type.ERROR, "Unknown tank of type: " + tankType); return null;
        }
        Logger.outputToGUI(Logger.Type.ERROR, "Could not find or remove object with ID: " + objectID + " from tank of type: " + tankType);
        return null;
    }
    */
    
    /**
     * Removes the specified object from the specified array.
     * 
     * @param objectID The unique ID of the object to remove.
     * @param tank The array from which to remove the object.
     * @return The removed object if the removal was successful. null otherwise.
     */
    /*
    private static Object removeObjectFromArray(int objectID, ArrayList<IGameObject> tank) {
        for(IGameObject gameObject : tank) {
            if(gameObject.getUniqueID() == objectID) {
                if(tank.remove(gameObject)) {
                    return gameObject;
                }
                else {
                    break;
                }
            }
        }
        return null;
    }
    */
}
