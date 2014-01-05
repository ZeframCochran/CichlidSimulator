package cichlid_sim.engine.app;

import cichlid_sim.game.MainGame;

/**
 * This class provides a static location to store the games SimpleApplication object.
 * Many objects and functions throughout the game reference the SimpleApplication object.
 * Using a static reference to the SimpleApplication object we are able to reduce the clutter
 * involved with constantly passing it from method to method class to class.
 *
 * @author Wesley Perry
 */
public class GameAppManager {
    private static MainGame mainGame;
    
    public static void setMainGame(MainGame mGame) {
        mainGame = mGame;
    }
    
    public static MainGame getMainGame() {
        return mainGame;
    }
}
