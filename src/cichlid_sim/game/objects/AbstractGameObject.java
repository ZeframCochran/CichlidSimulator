package cichlid_sim.game.objects;

import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * This class defines the basic structure of a GameObject.
 *
 * @author Wesley Perry
 */
public abstract class AbstractGameObject extends Node implements IGameObject{
    private int uniqueID;
    protected float size;
    
    /**
     * @param name The name of this object.
     * @param uniqueID The unique ID assigned to this object.
     */
    public AbstractGameObject(String name, int uniqueID) {
        super(name);
        this.uniqueID = uniqueID;
    }

    /**
     * @return The unique ID assigned to this object.
     */
    @Override
    public int getUniqueID() {
        return uniqueID;
    }
    
    /**
     * Return the bounds of the object;
     * 
     * @return a Vector3f representing the objects bounds.
     */
    public Vector3f getWorldBounds() {
        BoundingVolume bounds = this.getWorldBound();
        if(bounds.getType().equals(BoundingVolume.Type.AABB)) {
            return ((BoundingBox)bounds).getExtent(null);
        }
        else {
            return new Vector3f();
        }
    }
    
    public float getSize() {
        return size;
    }
}
