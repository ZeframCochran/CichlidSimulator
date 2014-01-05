package cichlid_sim.game;

import cichlid_sim.engine.action.TimeManipulationActionHandler;
import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.engine.logger.Logger;
import cichlid_sim.engine.scene.models.Models;
import cichlid_sim.engine.util.GameRandomManager;
import cichlid_sim.engine.util.Length;
import cichlid_sim.game.objects.Fish;
import cichlid_sim.game.objects.IGameObject;
import cichlid_sim.game.objects.Plant;
import cichlid_sim.game.objects.Pot;
import cichlid_sim.game.objects.Tank;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * This class handles the insertion of objects into the game world.
 *
 * @author Wesley perry
 */
public class AddObject {
    private static int uniqueID = 0;
    
    /**
     * This method sends the addObject request to the appropriate add<Object> method.
     * It seems like this logic would be better captured elsewhere.
     * Perhaps in the abstract parent?
     * @param object The JSONObject containing the attributes of the object to add.
     */
    public static void addObject(JSONObject object) {       
        IGameObject.Type type = IGameObject.Type.valueOf(object.get("Type").toString());       
        if(Tank.isCreated()){
            switch(type) {
                case TANK : 
                    Logger.outputToGUI(Logger.Type.ERROR, "Tank already added to world. Start a new simulation to add a different tank.");
                    break;
                case FISH : 
                    addFish(object);
                    break;
                case PLANT:
                    addPlant(object);
                    break;
                case POT  : if(Tank.isCreated()) 
                    addPot(object);
                    break;
                default : Logger.outputToGUI(Logger.Type.ERROR, "Object of type " + type + " cannot be added to the game world.");
            }
        }
        else{
            switch(type) {
                case TANK : addTank(object); break;
                case FISH : Logger.outputToGUI(Logger.Type.ERROR, "Tank must be created before adding other objects."); break;
                case PLANT: Logger.outputToGUI(Logger.Type.ERROR, "Tank must be created before adding other objects."); break;
                case POT  : Logger.outputToGUI(Logger.Type.ERROR, "Tank must be created before adding other objects."); break;
                default   : Logger.outputToGUI(Logger.Type.ERROR, "Object of type " + type + " cannot be added to the game world.");
            }
        }
        
        Logger.outputToGUI(Logger.Type.INFO, "Added to the game world: " + object);       
    }
    
    /**
     * Adds a Tank to the game world using the provided JSONObject.
     * 
     * @param object The JSONObject containing the attributes of the object to add.
     */
    private static boolean addTank(JSONObject object) {
        if(object.has("Name") && object.has("SizeX") && object.has("SizeY") && object.has("SizeZ") && object.has("Temperature")) {
            String name = object.getString("Name");
            float temperature = (float)object.getDouble("Temperature");
            Vector3f size = new Vector3f ((float)object.getDouble("SizeX"),(float)object.getDouble("SizeY"),(float)object.getDouble("SizeZ"));
            int newUniqueID;
            if(object.has("ID")) {
                newUniqueID = object.getInt("ID");
            }
            else {
                newUniqueID = getNewUniqueID();
            }
            if(object.has("GameTime")) {
                TimeManipulationActionHandler.setGameTime(object.getDouble("GameTime"));
            }
            Tank tank = new Tank(name, newUniqueID, size);
            tank.setTemperature(temperature);
            NodeCollection.getNode("TankNode").attachChild(tank);
            return true;
        }
        else {
            Logger.outputToGUI(Logger.Type.ERROR, "Add Tank request corrupted. Unable to add object: " + object);
            return false;
        }
    }
    
    /**
     * Adds a Fish to the game world using the provided JSONObject.
     * 
     * @param object The JSONObject containing the attributes of the object to add.
     */
    private static boolean addFish(JSONObject object) {
        if(object.has("Name") && object.has("Size") && object.has("Weight") && object.has("Tank") && object.has("Gender") && object.has("Aggression") && object.has("Health") && object.has("Breeding Status")) {
            Tank.Type tankType = Tank.Type.valueOf(object.get("Tank").toString());
            boolean gender = object.getBoolean("Gender");
            String genderModelName = gender ? "Fish.Male" : "Fish.Female";
            float aggression = (float)object.getDouble("Aggression");
            float health = (float)object.getDouble("Health");
            float breedingStatus = (float) object.getDouble("Breeding Status");
            int newUniqueID;
            if(object.has("ID")) {
                newUniqueID = object.getInt("ID");
            }
            else {
                newUniqueID = getNewUniqueID();
            }

            String name = object.getString("Name");
            Spatial model = Models.getModel(genderModelName);
            float size = (float)object.getDouble("Size");
            float weight = (float)object.getDouble("Weight");

            Fish fish = new Fish(name,newUniqueID,model,size,gender,weight);
            fish.setAggression(aggression);
            fish.setHealth(health);
            fish.setBreedingStatus(breedingStatus);
            fish.setHomeTank(tankType);

            switch(tankType) {
                case ARENA : NodeCollection.getNode("FishNode").attachChild(fish); break;
                case STOCK : NodeCollection.getNode("StockTank").attachChild(fish); break; //GameObjectCollection.getArray("StockTank").add(fish); break;
                case ISOLATION : NodeCollection.getNode("IsolationTank").attachChild(fish); break; //GameObjectCollection.getArray("IsolationTank").add(fish); break;
                default : Logger.outputToGUI(Logger.Type.ERROR, "Unknown tank type: " + tankType);
            }
            return true;
        }
        else {
            Logger.outputToGUI(Logger.Type.ERROR, "Add Fish request corrupted. Unable to add object: " + object);
            return false;
        }
    }
    
    /**
     * Adds a Plant to the game world using the provided JSONObject.
     * 
     * @param object The JSONObject containing the attributes of the object to add.
     */
    private static boolean addPlant(JSONObject object) {
        if(object.has("Name") && object.has("Size")) {
            String name = object.getString("Name");
            float size = (float)object.getDouble("Size");
            int newUniqueID;
            if(object.has("ID")) {
                newUniqueID = object.getInt("ID");
            }
            else {
                newUniqueID = getNewUniqueID();
            }
            Plant plant = new Plant(name,newUniqueID,Models.getModel("Plant"),size);
            plant.setLocalTranslation(GameRandomManager.getRandom3DPointWithinArenaTank(plant.getModelBounds()).setY((-((Tank)NodeCollection.getNode("TankNode").getChild("ArenaTank")).getY())));
            NodeCollection.getNode("PlantNode").attachChild(plant);
            return true;
        }
        else {
            Logger.outputToGUI(Logger.Type.ERROR, "Add Plant request corrupted. Unable to add object: " + object);
            return false;
        }
    }
    
    /**
     * Adds a Pot to the game world using the provided JSONObject.
     * 
     * @param object The JSONObject containing the attributes of the object to add.
     */
    private static boolean addPot(JSONObject object) {
        if(object.has("Name") && object.has("Size")) {
            String name = object.getString("Name");
            float size = (float)object.getDouble("Size");
            int newUniqueID;
            if(object.has("ID")) {
                newUniqueID = object.getInt("ID");
            }
            else {
                newUniqueID = getNewUniqueID();
            }
            Pot pot = new Pot(name,newUniqueID,Models.getModel("Pot"),size);
            pot.setLocalTranslation(GameRandomManager.getRandom3DPointWithinArenaTank(pot.getModelBounds()).setY((-((Tank)NodeCollection.getNode("TankNode").getChild("ArenaTank")).getY()) + Length.cmToWorldUnits(pot.getSize()*.5f)));
            NodeCollection.getNode("PotNode").attachChild(pot);
            return true;
        }
        else {
            Logger.outputToGUI(Logger.Type.ERROR, "Add Pot request corrupted. Unable to add object: " + object);
            return false;
        }
    }
    
    /**
     * Returns a new unique ID with which to identify the created object.
     * 
     * NOTE: This method is currently public in case a programmer wants to (illegally)
     * add an item to the game world without using this class. 
     * 
     * @return A new unique integer ID.
     */
    public static int getNewUniqueID() {
        return ++uniqueID;
    }
}
