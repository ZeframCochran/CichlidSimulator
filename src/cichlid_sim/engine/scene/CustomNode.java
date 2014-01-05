package cichlid_sim.engine.scene;

import cichlid_sim.engine.logger.Logger;
import cichlid_sim.game.NodeCollection;
import cichlid_sim.game.objects.IGameObject;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class provides additional methods to the default Node class to ensure 
 * scene graph stability. This class should be used instead of Node in situations
 * where the node could possibly have children added or removed after the game 
 * calls updateGeometricState on the node.
 *
 * @author Wesley Perry
 */
public class CustomNode extends Node{
    private ArrayList<Spatial> childrenToAdd;
    private ArrayList<Spatial> childrenToRemove;
    
    public CustomNode(String name) {
        super(name);
        //Register this node with the NodeCollection;
        NodeCollection.addNode(this, name);
        childrenToAdd = new ArrayList();
        childrenToRemove = new ArrayList();
    }
    
    /**
     * Overrides the default attachChild method to ensure the scene graph will
     * not be updated after a call to updateGeometricState().
     * 
     * @param child The child to add to this node.
     * @return The number of children being managed by this node.
     */
    @Override
    public int attachChild(Spatial child) {
        childrenToAdd.add(child);
        Logger.outputToGUI(Logger.Type.DEBUG, "Child " + child.getName() + " added to node " + this.getName());
        return children.size() + childrenToAdd.size();
    }
    
    /**
     * Overrides the default detachChild method to ensure the scene graph will
     * not be updated after a call to updateGeometricState().
     * @param child
     * @return 
     */
    @Override
    public int detachChild(Spatial child) {
        childrenToRemove.add(child);
        Logger.outputToGUI(Logger.Type.DEBUG, "Child " + child.getName() + " removed from node " + this.getName());
        return children.size() - childrenToRemove.size();
    }
    
    /**
     * This overridden method returns the child whos name matches the provided 
     * childName parameter.
     * 
     * @param childName The name of the child to return.
     * @return The child whos name matches the childName parameter.
     */
    @Override
    public synchronized Spatial getChild(String childName) {
        if(!childrenToAdd.isEmpty()) {
            Iterator<Spatial> iter = childrenToAdd.iterator();
            while(iter.hasNext()) {
                Spatial child = iter.next();
                if(child.getName() == null ? childName == null : child.getName().equals(childName)) {
                    return child;
                }
            }
        }
        return super.getChild(childName);
    }
    
    /**
     * This overridden method attaches any children added since the last call to
     * updateGeometricState then calls the parents updateGeometricState method.
     */
    @Override
    public synchronized void updateGeometricState() {
        //Remove all children in the childrenToRemove list;
        while(!childrenToRemove.isEmpty()) {
            Spatial child = childrenToRemove.get(0);
            super.detachChild(child);
            Logger.outputToGUI(Logger.Type.DEBUG, "Child " + child.getName() + " DETACHED from node " + this.getName());
            childrenToRemove.remove(child);
        }
        //Add all children in the childrenToAdd list;
        while(!childrenToAdd.isEmpty()) {
            Spatial child = childrenToAdd.get(0);
            super.attachChild(child);
            Logger.outputToGUI(Logger.Type.DEBUG, "Child " + child.getName() + " ATTACHED to node " + this.getName());
            childrenToAdd.remove(child);
        }
        
        //java.util.ConcurrentModificationException happens with the below code. Which is why we do the goofy iterate above;
        /*
        if(!childrenToRemove.isEmpty()) {
            Iterator<Spatial> iter = childrenToRemove.iterator();
            while(iter.hasNext()) {
                Spatial child = iter.next();
                super.detachChild(child);
                Logger.outputToGUI(Logger.Type.DEBUG, "Child " + child.getName() + " DETACHED from node " + this.getName());
            }
            childrenToRemove.clear();
        }
        //Add all children in the childrenToAdd list;
        if(!childrenToAdd.isEmpty()) {
            for(Iterator<Spatial> iter = childrenToAdd.iterator(); iter.hasNext();) {
                Spatial child = iter.next();
                super.attachChild(child);
                Logger.outputToGUI(Logger.Type.DEBUG, "Child " + child.getName() + " ATTACHED to node " + this.getName());
            }
            childrenToAdd.clear();
        }
        */
        //Update the scene graph;
        super.updateGeometricState();
    }
    
    /**
     * Prints the node tree for this and all child nodes for debug purposes.
     */
    public void print() {
        System.out.println("### Begin Printout of Node: " + this.getName() + ". ###");
        this.print(this, "");
        System.out.println("### End Printout of Node: " + this.getName() + ". ###");
    }
    private void print(CustomNode node, String prefix) {
        for(Spatial child : node.getChildren()) {
            if(child instanceof CustomNode) {
                System.out.println(prefix + child.toString());
                print((CustomNode)child, prefix + " ");
            }
            else {
                System.out.println(prefix + child.toString());
            }
        }
    }
}