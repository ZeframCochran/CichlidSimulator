package cichlid_sim.game;

import cichlid_sim.engine.logger.Logger;
import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 * A 'node manager' for the game. Maintains a collection of the games Nodes for
 * easy access.
 *
 * @author Wesley Perry
 */
public class NodeCollection {
    private static ArrayList<NodeCollectionObject> nodeCollection = new ArrayList();
    
    /**
     * Adds a Node to this NodeCollection.
     * 
     * @param node The Node to add.
     * @param name The name to assign to the Node.
     */
    public static void addNode(Node node, String name) {
        nodeCollection.add(new NodeCollectionObject(node, name));
    }
    
    /**
     * Returns the Node associated with the provided name.
     * 
     * @param name The name of the Node to return.
     * @return The Node associated with the provided name.
     */
    public static Node getNode(String name) {
        for(NodeCollectionObject nCO : nodeCollection)
        {
            if(name.equals(nCO.getName())) {
                return nCO.getNode();
            }
        }
        Logger.outputToGUI(Logger.Type.ERROR, "Node '" + name + "' is not stored in the NodeCollection.");
        return null;
    }
    
    /**
     * Private enum to associate Nodes with their assigned names.
     */
    private static class NodeCollectionObject {
        private Node node;
        private String name;
        
        public NodeCollectionObject(Node nod, String nam) {
            node = nod;
            name = nam;
        }
        
        public String getName() {
            return name;
        }
        
        public Node getNode() {
            return node;
        }
    }
}
