package cichlid_sim.game.objects;

import cichlid_sim.engine.input.IClickableObject;
import cichlid_sim.engine.json.JSONObject;

/**
 * Each class which implements this interface is defined as a game object.
 *
 * @author Wesley Perry
 */
public interface IGameObject extends IClickableObject{
    
    /**
     * Defines the types of game objects.
     */
    public enum Type {
        TANK, FISH, PLANT, POT
    }
    
    /**
     * This method should return this IGameObjects unique identifier.
     */
    public int getUniqueID();
    
    /**
     * This method should convert this IGameObject into a JSONObject.
     */
    public JSONObject toJSON();
}
