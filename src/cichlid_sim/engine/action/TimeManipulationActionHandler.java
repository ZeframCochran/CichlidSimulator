package cichlid_sim.engine.action;

/**
 * This class handles the manipulation of game world time. 
 *
 * @author Wesley Perry
 */
public class TimeManipulationActionHandler {
    private static final float STEP = 1.0f;//0.25f;
    private static float currentSpeed = 1;
    private static double gameTime = 0;
    private static boolean skipRequested = false;
    private static int secondsToSkip = 0;
    
    /**
     * This method increases the speed multiplier of the game by STEP amount.
     */
    public static void increaseSpeed() {
        currentSpeed += STEP;
    }
    
    /**
     * This method decreases the speed multiplier of the game by STEP amount.
     */
    public static void decreaseSpeed() {
        if(currentSpeed > STEP) {
            currentSpeed -= STEP;
        }
    }
    
    /**
     * This method restores the game speed multiplier to its default state 
     * (default state is 1, neither increasing or decreasing the speed of the game).
     */
    public static void restoreSpeedToDefault() {
        currentSpeed = 1;
    }
    
    /**
     * Sets the game speed multiplier to the provided variable.
     */
    public static void setSpeed(float newSpeed) {
        if(newSpeed > 0) {
            currentSpeed = newSpeed;
        }
    }
    
    /**
     * @return The current game speed multiplier.
     */
    public static float getCurrentSpeed() {
        return currentSpeed;
    }
    
    /**
     * @return The current gameTime variable.
     */
    public static double getGameTime() {
        return gameTime;
    }
    
    /**
     * Sets the running time of the simulation (for resuming from save file).
     * 
     * @param time The game time in seconds.
     */
    public static void setGameTime(double time) {
        gameTime = time;
    }
    
    /**
     * Increments the gameTime variable by the provided amount.
     * 
     * @param howMuch How much to increment the gameTime by.
     * @return The new value of the gameTime.
     */
    public static double incrementGameTime(float howMuch) {
        return gameTime += howMuch;
    }
    
    /**
     * Dramatically increases the game speed until the game time has progressed 
     * by the specified amount.
     * 
     * @param seconds How much time (in seconds) to skip forward from the current game time.
     */
    public static void skipForward(int seconds) {
        //The following could modify the game after updateGeometricState(), so we just set flags to let MainGame know that a skip has been requested;
        //GameAppManager.getMainGame().batchUpdate(seconds);  //does the whole skip in one frame, can take a while for large jumps;
        secondsToSkip = seconds;
        skipRequested = true;
    }
    
    public static void resetSkipRequested() {
        skipRequested = false;
        secondsToSkip = 0;
    }
    
    public static boolean getSkipRequested() {
        return skipRequested;
    }
    
    public static int getSkipRequestedSeconds() {
        return secondsToSkip;
    }
    
    
    
    
    
    /**
     * These methods are a secondary approach to a 'fast forward' feature. 
     * Basically, increase the game speed and allow the update loop to continue
     * as normal. This approach has a tendency to 'overshoot' the desired end time.
     * 
     * This approach allows the user to 'watch' the skip. The other approach 
     * performs the whole skip in a single update loop (so it is more exact).
     * But the user will only see the game pause for a moment and a sudden increase
     * in time and difference in fish location in the game world.
     * 
     * An optimal approach would be to split the 'skip in a single frame' approach
     * into multiple frames so the user could watch the game world change. A 
     * progress bar would also be very nice (using any approach).
     * 
     * @param seconds Seconds to 'skip forward.'
     */
    private static boolean skipForwardActive = false;
    private static float backupSpeed;
    private static double skipForwardEndTime;
    public static void skipForward2(int seconds) {          //skips over several frames. has some issues with 'over shooting' though;
        double currentGameTime = gameTime;
        skipForwardEndTime = currentGameTime + seconds;
        backupSpeed = currentSpeed;
        skipForwardActive = true;
        currentSpeed = 1000000;    //Turbo mode;
    }
    
    private static void resetSkipSettings() {
        currentSpeed = backupSpeed;
        skipForwardActive = false;
    }
    
    /**
     * Increments the gameTime variable by the provided amount.
     * 
     * @param howMuch How much to increment the gameTime by.
     * @return The new value of the gameTime.
     */
    public static double incrementGameTime2(float howMuch) {
        if(skipForwardActive) {
            if((gameTime += howMuch) >= skipForwardEndTime) {
                resetSkipSettings();
            }
            return gameTime;
        }
        else {
            return gameTime += howMuch;
        }
    }
}
