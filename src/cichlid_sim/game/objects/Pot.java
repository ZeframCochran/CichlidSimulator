package cichlid_sim.game.objects;

import cichlid_sim.engine.json.JSONObject;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * This class defines a Pot object.
 *
 * @author Wesley Perry
 */
public class Pot extends Abstract3DModelGameObject implements cichlid_sim.engine.input.IClickableObject
{
    private static final float POTMODELSCALINGFACTOR = 0.4f;
    
    /**
     * Constructs a new Pot object.
     * 
     * @param name The name of this plant.
     * @param uniqueID This plants unique ID.
     * @param model The 3D model used to display this plant in the 3D world.
     */
    public Pot(String name, int uniqueID, Spatial model, float size)
    {
        super(name, uniqueID, model, POTMODELSCALINGFACTOR);
        //'Tip' the pot over;
        model.rotate(0,0,(float)java.lang.Math.toRadians(-90));
        this.scale(size);
        this.size = size;
        this.setModelBounds(new Vector3f(1.25f,.75f,.75f).mult(size));
    }

    /**
     * Converts this game object into a JSONObject.
     * 
     * @return This object represented as a JSONObject.
     */
    @Override
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("Type", IGameObject.Type.POT);
        object.put("ID", this.getUniqueID());
        object.put("Name", this.name);
        object.put("Size", this.size);
        return object;
    }
}
