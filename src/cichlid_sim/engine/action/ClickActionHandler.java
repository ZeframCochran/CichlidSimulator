package cichlid_sim.engine.action;

import cichlid_sim.engine.app.GameAppManager;
import cichlid_sim.engine.input.IClickableObject;
import cichlid_sim.game.NodeCollection;
import cichlid_sim.game.PipeToGUI;
import cichlid_sim.game.objects.IGameObject;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

/**
 * This class handles the user-action of 'clicking' within the JME canvas.
 *
 * @author Wesley Perry
 */
public class ClickActionHandler {
    
    private static Node previouslySelectedObject = null;
    private static AmbientLight highlighter = null;
    
    /**
     * The action to take place when the mouse is clicked within the JME canvas.
     * In this case we check if the user has clicked on any clickable object.
     * To check this we cast a ray from the camera through the mouse pointer.
     * The first clickable object the ray hits is said to have been clicked and
     * is highlighted.
     */
    public static void mouseClicked() {
        if(highlighter == null || previouslySelectedObject == null)
        {
            highlighter = new AmbientLight();
            highlighter.setColor(com.jme3.math.ColorRGBA.White.mult(5.0f));
        }
        else{
            previouslySelectedObject.removeLight(highlighter);
        }

        //Build the ray;
        Camera cam = GameAppManager.getMainGame().getCamera();
        Vector2f cursorScreenPos = GameAppManager.getMainGame().getInputManager().getCursorPosition();
        Vector3f cursorWorldPos = cam.getWorldCoordinates(new Vector2f(cursorScreenPos.x,cursorScreenPos.y), 0);
        Vector3f direction = new Vector3f(cursorWorldPos.subtract(cam.getLocation()));  //I'm not sure what the above does, this works fine;
        Ray ray = new Ray(cam.getLocation(), direction);
        //Collide the ray with the clickable objects node;
        Node clickableObjects = NodeCollection.getNode("ClickableObjectNode");
        CollisionResults results = new CollisionResults();
        clickableObjects.collideWith(ray, results);
        //Grab the closest object;
        CollisionResult closest = results.getClosestCollision();
        
        //Apply highlighting so we can see that it worked. Eventually we'll want a tool tip or similar;
        //Highlight via applying a local ambient light;
        try{    
            Node selected = closest.getGeometry().getParent();
            while(true) {
                //The part of the object that is clicked will not be the actual object itself. Instead it will be a sub-part of the object. So we do .getParent() until we either get a IClickableObject or null.
                if(selected instanceof IClickableObject) {
                    //Add highliting on the selected object;
                    selected.addLight(highlighter);
                    previouslySelectedObject = selected;
                    
                    if(selected instanceof IGameObject) {
                        /*  //Adds a 'bounding box' around the clicked object. Very helpful when debugging model size;
                        if(selected instanceof cichlid_sim.game.objects.Abstract3DModelGameObject) {
                            ((cichlid_sim.game.objects.Abstract3DModelGameObject)selected).attachBoundingBox();
                        }
                        */
                        //Send the selected game object to the GUI;
                        PipeToGUI.gameWorldObjectSelected(((IGameObject)selected).toJSON());
                    }
                    break;
                }
                else {
                    //Looking for the clickable object. Do getParent() and loop if parent isn't null;
                    selected = selected.getParent();
                    if(selected == null) {  //This will happen if a non-ClickableObject is added to the ClickableObject node;
                        break;              //Our program will happily catch this crash and plug right along... Wesley is working on a fix for this;
                    }
                }
            }
        }
        catch(NullPointerException e) {
            //No object collided with the ray;
            previouslySelectedObject = null;
        }
    }
}
