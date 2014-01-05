package cichlid_sim.engine.util;

import cichlid_sim.engine.logger.Logger;
import cichlid_sim.game.objects.Tank;
import com.jme3.math.Vector3f;
import java.util.Random;

/**
 *
 * @author Wesley Perry
 */
public class GameRandomManager {
    private static Random rand;
    
    public static void setRand(Random rnd) {
        rand = rnd;
    }
    
    public static Random getRand() {
        return rand;
    }
    
    /**
     * Returns a random floating point number from 0 to the input number [0,num].
     * @param num The number to use as upper bound.
     * @return a random floating point number from 0 to the input number.
     */
    public static float getRandomFloat(float num) {
        return rand.nextFloat() * num;
    }
    
    /**
     * Returns a random floating point number from [0,num) or from (-num,num).
     * @param num The number to use as upper bound.
     * @param randomNegative If true, this function will return a random float from [-num,num].
     */
    public static float getRandomFloat(float num, boolean randomNegative) {
        return (randomNegative ? (rand.nextBoolean() ? 1 : -1) : 1) * getRandomFloat(num);
    }
    
    /**
     * This logic is used so often it has been moved into a static method to reduce code clutter.
     * 
     * @param bounds Optional. The buffer to allow in the x,y,z direction from the tank edges.
     * @return A random point (cast into a Vector3f) within the bounds of the ArenaTank.
     */
    public static Vector3f getRandom3DPointWithinArenaTank() {
        return getRandom3DPointWithinArenaTank(new Vector3f(0,0,0));
    }
    public static Vector3f getRandom3DPointWithinArenaTank(Vector3f bounds) {
        //Grab the tank object to retrieve tank bounds;
        Tank tank = (Tank) cichlid_sim.game.NodeCollection.getNode("TankNode").getChild("ArenaTank");
        if(bounds.x > tank.getX() || bounds.y > tank.getY() || bounds.z > tank.getZ()) {
            Logger.outputToGUI(Logger.Type.ERROR, "Tried to generate a random point within the tank but the input bounds are larger than the tank size! Bounds: " + bounds.toString() + ". Tank: " + new Vector3f(tank.getX(), tank.getY(), tank.getZ()).toString() + ".");
            return new Vector3f(0,0,0);
        }
        else {
            //Create a random point within that tank;
            return new Vector3f(getRandomFloat(tank.getX()-bounds.x, true),getRandomFloat(tank.getY()-bounds.y, true),getRandomFloat(tank.getZ()-bounds.z, true));
        }
    }
}
