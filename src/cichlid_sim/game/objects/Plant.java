package cichlid_sim.game.objects;

import cichlid_sim.engine.json.JSONObject;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * This class defines a Plant object.
 *
 * @author Wesley Perry
 */
public class Plant extends Abstract3DModelGameObject implements cichlid_sim.engine.input.IClickableObject, IGameObject
{
    private static final float PLANTMODELSCALINGFACTOR = 0.5f;    
    
    /**
     * Constructs a new Plant object.
     * 
     * @param name The name of this plant.
     * @param uniqueID This plants unique ID.
     * @param model The 3D model used to display this plant in the 3D world.
     */
    public Plant(String name, int uniqueID, Spatial model, float size)
    {
        super(name, uniqueID, model, PLANTMODELSCALINGFACTOR);
        this.scale(size);
        this.size = size;
        this.setModelBounds(new Vector3f(1,1.25f,1).mult(size));
    }

    /**
     * Converts this game object into a JSONObject.
     * 
     * @return This object represented as a JSONObject.
     */
    @Override
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("Type", IGameObject.Type.PLANT);
        object.put("ID", this.getUniqueID());
        object.put("Name", this.name);
        object.put("Size", this.size);
        return object;
    }
}