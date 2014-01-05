package cichlid_sim.game.objects;

import cichlid_sim.engine.action.AxesToggleActionHandler;
import cichlid_sim.engine.app.GameAppManager;
import cichlid_sim.engine.util.Length;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.WireBox;

/**
 * This class handles the functions common to all GameObjects which contain 3D Models.
 *
 * @author Wesley Perry
 */
public abstract class Abstract3DModelGameObject extends AbstractGameObject {
    private Vector3f modelBounds;
    
    /**
     * Constructor.
     * 
     * @param name The name of this object.
     * @param uniqueID The unique ID assigned to this object.
     * @param model The model used to represent this object in the 3D world.
     * @param modelScaleFactor The factor used to 'shrink/expand' this object to a size of 1cm.
     */
    public Abstract3DModelGameObject(String name, int uniqueID, Spatial model, float modelScaleFactor) {
        super(name, uniqueID);
        this.attachChild(model);
        //Scale the model by modelScaleFactor to 'shrink/expand' the model to an approximate size of 1cm;
        model.scale(modelScaleFactor);
        model.updateModelBound();
        
        //add a 1cm axis to the model (useful for testing);
        Axes modelAxes = new Axes("ModelAxes:" + model.getName(), (float)Length.cmToWorldUnits(1.0f));
        AxesToggleActionHandler.addAxes(modelAxes);
        this.attachChild(modelAxes); 
    }
    
    /**
     * Sets the size of the bounding box of the un-scaled model. When attempting
     * to figure out what the model bounds are it is very helpful to use the local
     * attachBoundingBox() method to attach a box around the modelBounds that
     * have been set for that object.
     * 
     * @param modelBounds the x, y, and z extents of the model.
     */
    protected void setModelBounds(Vector3f modelBounds) {
        this.modelBounds = modelBounds.mult(Length.scaleFactor.floatValue());
    }
    
    public Vector3f getModelBounds() {
        return modelBounds;
    }
    
    /**
     * Attaches a viewable box representing the bounding box for this model.
     * Useful when debugging bounding volumes and model sizes.
     * 
     * @param selected The object to attach the visible bounding volume.
     */
    public void attachBoundingBox() {
        /* //With official bounding volumes;
        BoundingBox bBox = (BoundingBox) selected.getWorldBound();
        WireBox wireBox = new WireBox(bBox.getXExtent(), bBox.getYExtent(), bBox.getZExtent());
        //wireBox.fromBoundingBox((BoundingBox)selected.getWorldBound());
        Geometry boundsBox = new Geometry("Box",wireBox);
        Material material = new Material(GameAppManager.getMainGame().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.White);
        boundsBox.setMaterial(material);
        boundsBox.updateModelBound();
        selected.attachChild(boundsBox);
        */
        //Official jme bounding box is way too big. Unsure why but think it may have to do with multi-part 3d models;
        //So I created a custom parameter in each 3dModel game object to store the approximate bounds of the object;
        //This method displays those bounds as a 'bounding box';
        Vector3f bounds = this.getModelBounds();
        bounds = bounds.divide(this.getSize());

        WireBox wireBox = new WireBox(bounds.x, bounds.y, bounds.z);
        Geometry boundsBox = new Geometry("Box",wireBox);
        Material material = new Material(GameAppManager.getMainGame().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.White);
        boundsBox.setMaterial(material);
        this.attachChild(boundsBox);
        Axes a = new Axes(this.getName() + ": BoundingBoxAxes", bounds.x, bounds.y, bounds.z, true);
        this.attachChild(a);
    }
}