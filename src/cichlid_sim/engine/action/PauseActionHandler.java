package cichlid_sim.engine.action;

/**
 * This class handles the user-action of pausing the game.
 * Currently it just sets a flag that is checked during MainGame's update loop.
 *
 * @author Wesley Perry
 */
public class PauseActionHandler {
    private static boolean pauseRequested;
    
    public static void setPaused(boolean pause) {
        pauseRequested = pause;
    }
    
    public static boolean getPaused() {
        return pauseRequested;
    }
}
