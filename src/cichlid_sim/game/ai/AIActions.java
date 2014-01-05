/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cichlid_sim.game.ai;

import cichlid_sim.engine.logger.Logger;
import cichlid_sim.engine.util.GameRandomManager;
import cichlid_sim.engine.util.Length;
import cichlid_sim.game.objects.AbstractGameObject;
import cichlid_sim.game.objects.Fish;

/**
 * It is possible to write the resulting actions of the fish as rules, but it would be very difficult.
 * Better to test with this first.
 * @author anonymous
 */
public class AIActions {
    private static int instanceCount = 0;
    public void testAction(Fish one){
        instanceCount++;
        Logger.outputToGUI(Logger.Type.INFO, "Test action fired "+instanceCount+" times.");
    }
    //Flee target fish
    public void fleeAction(Fish myFish, Fish otherFish){
        double distance = otherFish.getLocation().distance(myFish.getLocation());
        if(!myFish.isBusy() && distance <  Length.cmToWorldUnits(10))
        {
            Logger.outputToGUI(Logger.Type.INFO, myFish+" fleeing " + otherFish + " at distance: "+distance);
            //Get the location of the bad fish
            myFish.setMoveSpeed(3);
            myFish.setDestinationLocation(GameRandomManager.getRandom3DPointWithinArenaTank(myFish.getModelBounds()));
            myFish.setBusy((long)GameRandomManager.getRandomFloat(3)+2);
            //Set a destination away from that fish
            //Go to our new destination with increased speed
        }
    }
    
    public void chaseAction(Fish myFish, Fish otherFish){
        double distance = otherFish.getLocation().distance(myFish.getLocation());
        if(!myFish.isBusy() && distance <  Length.cmToWorldUnits(10))
        {
            Logger.outputToGUI(Logger.Type.INFO, myFish+" chasing " + otherFish + " at distance: "+distance);
            //Get the location of the chasing fish
            myFish.setMoveSpeed(2);
            myFish.setDestinationLocation(otherFish.getLocation().clone());
            myFish.setBusy((long)GameRandomManager.getRandomFloat(3)+2);
            //Set a destination away from that fish
            //Go to our new destination with increased speed
        }
    }
    
        public void interestingObject(Fish myFish, AbstractGameObject interestingObject){
        double distance = interestingObject.getWorldTranslation().distance(myFish.getLocation());
        if(!myFish.isBusy() && distance < Length.cmToWorldUnits(9))
        {
            Logger.outputToGUI(Logger.Type.INFO, myFish+" Saw an interesting object at distance: "+distance);
            //Get the location of the chasing fish
            myFish.setDestinationLocation(interestingObject.getWorldTranslation());
            if(interestingObject.getWorldTranslation().distance(myFish.getLocation()) < 0.5)
            {//Slow down the fish
                myFish.setMoveSpeed((float)0.10);    
                myFish.setBusy((long)GameRandomManager.getRandomFloat(3));
            }
            
            
            //Set a destination away from that fish
            //Go to our new destination with increased speed
        }
    }
    
    public void delayAction(Fish myFish, int time){
        Logger.outputToGUI(Logger.Type.INFO, myFish + " pausing for the " + time + " seconds.");
    }
}
