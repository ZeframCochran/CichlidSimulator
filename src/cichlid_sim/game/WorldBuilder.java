package cichlid_sim.game;

import cichlid_sim.engine.json.JSONArray;
import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.engine.logger.Logger;
import cichlid_sim.game.objects.IGameObject;

/**
 * This class handles construction of the game world from a JSONObject.
 *
 * @author Wesley Perry
 */
public class WorldBuilder {
    /**
     * Constructs the game world from the provided JSONObject file.
     * 
     * @param world The JSONObject containing the world data.
     */
    public static void buildWorldFromJSON(JSONObject world) {
        //Tank must be added first;
        if(world.has(IGameObject.Type.TANK.toString()))
        {
            //Add the tank;
            AddObject.addObject((JSONObject)((JSONArray)world.get(IGameObject.Type.TANK.toString())).get(0));
            
            //Iterate through the rest of the objects;
            for(IGameObject.Type type : IGameObject.Type.values()) {
                //We already added the tank, skip it;
                if(type != IGameObject.Type.TANK) {
                    if(world.has(type.toString())) {
                        //Grab the JSONArray containing all objects of this type;
                        JSONArray objects = ((JSONArray)world.get(type.toString()));
                        //Extract each item from the array;
                        for(int i=0;i<objects.length();i++) {
                            //Insert it into the game world;
                            AddObject.addObject((JSONObject)objects.get(i));
                        }
                    }
                }
            }
        }
        else {
            Logger.outputToGUI(Logger.Type.ERROR, "Cannot build world from JSON. No tank found! " + world);
        }
    }
}