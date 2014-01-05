package cichlid_sim.engine.action;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 * This class enables toggling 3d-world display of axes (or any registered node).
 *
 * @author Wesley Perry
 */
public class AxesToggleActionHandler {
    private static boolean axesActive = false;
    private static ArrayList<Node> axesNodes = new ArrayList();
    
    /**
     * 'Registers' the provided Node with this class.
     * @param n The Node to register.
     */
    public static void addAxes(Node n) {
        axesNodes.add(n);
        if(axesActive){}
        else {
            disableAxes(n);
        }
    }
    
    /**
     * 'Unregisters' the provided Node with this class.
     * 
     * @param n The Node to unregister.
     * @return True if the unregistration was successful. False otherwise.
     */
    public static boolean removeAxes(Node n) {
        for(Node node : axesNodes) {
            if(node.equals(n)) {
                return axesNodes.remove(n);
            }
        }
        return false;
    }
    
    public static void toggleAxes() {
        if(axesActive) {
            disableAxes();
        }
        else {
            enableAxes();
        }
    }
    
    /**
     * Displays all axes which are registered with this class.
     */
    
    private static void enableAxes() {
        System.out.println("Enabling Axes display.");
        for(Node n : axesNodes) {
            n.setCullHint(Spatial.CullHint.Never);
        }
        axesActive = true;
    }
    
    /**
     * Hides all axes which are registered with this class.
     */
    private static void disableAxes() {
        System.out.println("Disabling Axes display.");
        for(Node n : axesNodes) {
            disableAxes(n);
        }
        axesActive = false;
    }
    
    private static void disableAxes(Node n) {
            n.setCullHint(Spatial.CullHint.Always);
    }
}