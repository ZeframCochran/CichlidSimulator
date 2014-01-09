package cichlid_sim.game.objects;

import cichlid_sim.engine.action.TimeManipulationActionHandler;
import cichlid_sim.engine.json.JSONObject;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * This class defines a Fish object.
*
* @author Wesley Perry
*/
public class Fish extends Abstract3DModelGameObject implements cichlid_sim.engine.input.IClickableObject
{
    private static final float FISHMODELSCALINGFACTOR = 1.0f;
    private Vector3f destinationLocation;
    private float distanceToDestination;
    private Vector3f direction;
    private float moveSpeed;
    //private float size;
    private Vector3f worldUpVector = new Vector3f(0,1,0);
    private boolean moving;
    private float health;
    private float aggression;
    private boolean gender;
    private float breedingStatus;
    private float weight;
    private Tank.Type homeTank;
    private int actionPriority = 0;
    private double endTime=0;
    private double initialTime;
    
    /**
    * Constructors
    *
    * @param name This object's name.
    * @param fishModel The model used to draw this object.
    * @param direction The fish's view direction vector.
    * @param moveSpeed The fish's movement speed.
    * @param size The fish's size (used to scale the model).
    */
    public Fish(String name, int uniqueID, Spatial fishModel, float size, boolean gender, float weight) {
        super(name, uniqueID, fishModel, FISHMODELSCALINGFACTOR);
        this.size = size;
        this.gender = gender;
        //Scale the whole fish by fish size to set the fish to the appropriate size
        this.scale(size);
        fishModel.rotate(0,(float)java.lang.Math.toRadians(90),0);  //Ensure that the model is facing the correct direction
        this.updateModelBound();
        health = 1;
        aggression = 0;
        breedingStatus = 0;
        this.weight = weight;
        this.setModelBounds(new Vector3f(1,1.25f,1.5f).mult(size));
    }
            
    /**
    * Instructs this object to perform its update routines.
    */
    public void update(float time, boolean pauseRequested) {
        if(!pauseRequested) {
            moving = move(time);        //Sets the flag if this fish has a destination (isMoving)
        }
        else
        {
            //perform idle animation
        }
    }
    
    public boolean isBusy() {
        double currentTime = TimeManipulationActionHandler.getGameTime();
        if( (endTime - currentTime) < 0){
            actionPriority = 0;
            return false;
        }
        return true;
    }

    /**
     * The fish should ignore move requests when it is busy.
     * @param time 
     */
    public void setBusy(long time, int priority) {
        this.actionPriority = priority;
        initialTime = TimeManipulationActionHandler.getGameTime();
        endTime = time+initialTime;
    }
    
    /**
     * Moves the fish a small amount in the direction of the desired location.
     * @param time The time elapsed since the last update.
     * @return True if a move was performed. False otherwise (ie, no destinationLocation set).
     */
    private boolean move(float time) {
        if(this.destinationLocation != null) {
            float curSpeed = moveSpeed * time;
            Vector3f viewDir = direction.normalize();

            Vector3f tempMove = this.getLocalTranslation();     //Make the move on a dummy transform before moving the fish
            tempMove = tempMove.add(viewDir.mult(curSpeed));

            float distance = this.getLocation().distance(tempMove); //Get distance that the fish is about to move
            if(distance > this.distanceToDestination) { //Fish is about to jump over its destination point
                this.setLocalTranslation(this.destinationLocation); //So just snap it to the destination point
                this.destinationLocation = null;
                this.rotateUpTo(worldUpVector);         //Rotate the fish back to standard horizontal 'floating'
            }
            else {
                this.setLocalTranslation(tempMove);     //Move fish a small amount in the current direction
                this.distanceToDestination -= distance; //Remove the travled distance from the distance to travel
            }
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
* Sets the location that the fish will swim to.
* @param desiredLocation The location (as a 3dPoint cast to Vector3f) that the fish will swim to.
*/
    public void setDestinationLocation(Vector3f destinationLocation) {
        this.destinationLocation = destinationLocation;
        this.direction = this.destinationLocation.subtract(this.getLocation());
        this.distanceToDestination = this.destinationLocation.distance(this.getLocation());
        this.lookAt(this.destinationLocation, worldUpVector);
    }
    
    /**
     * Getters and setters.
     */
    public void setMoveSpeed(float speed) {
        this.moveSpeed = speed;
    }
    
    public Vector3f getLocation() {
        return this.getLocalTranslation();
    }
    
    public boolean isMoving() {
        return this.moving;
    }
    
    public void setHealth(float health) {
        this.health = health;
    }
    
    public void setAggression(float aggression) {
        this.aggression = aggression;
    }
    
    public void setBreedingStatus(float breedingStatus) {
        this.breedingStatus = breedingStatus;
    }
    
    public float getHealth() {
        return this.health;
    }
    
    public float getAggression() {
        return this.aggression;
    }
    
    @Override
    public float getSize() {
        return this.size;
    }
    
    public boolean getGender() {
        return this.gender;
    }
    
    public float getBreedingStatus() {
        return this.breedingStatus;
    }
    
    public Tank.Type getHomeTank() {
        return this.homeTank;
    }
    
    public void setHomeTank(Tank.Type tank) {
        this.homeTank = tank;
    }

    /**
     * Converts this game object into a JSONObject.
     * 
     * @return This object represented as a JSONObject.
     */
    @Override
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("Type", IGameObject.Type.FISH);
        object.put("ID", this.getUniqueID());
        object.put("Gender", this.gender);
        object.put("Aggression", this.aggression);
        object.put("Health", this.health);
        object.put("Name", this.name);
        object.put("Size", this.size);
        object.put("Weight", this.weight);
        object.put("Breeding Status", this.breedingStatus);
        object.put("Tank", this.homeTank);
        return object;
    }
}
