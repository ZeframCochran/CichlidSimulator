package cichlid_sim.engine.input;

import cichlid_sim.engine.action.AxesToggleActionHandler;
import cichlid_sim.engine.action.ClickActionHandler;
import cichlid_sim.engine.action.PauseActionHandler;
import cichlid_sim.engine.action.TimeManipulationActionHandler;
import cichlid_sim.engine.logger.Logger;
import cichlid_sim.engine.scene.CustomNode;
import cichlid_sim.game.NodeCollection;
import cichlid_sim.game.PipeFromGUI;

/**
 * This is the input listener class for the game. It maps the received inputs 
 * with the appropriate actions.
 *
 * @author Wesley Perry
 */
public class InputListener implements com.jme3.input.controls.ActionListener {
    /*
    private MainGame app;
    
    public InputListener(MainGame app) {
        this.app = app;
    }
    */
    
    /**
     * This method responds to known input events by initiating the appropriate action.
     * 
     * @param name The name of the mapping that was invoked.
     * @param keyPressed True if the action is "pressed", false otherwise.
     * @param tpf The time per frame value.
     */
    @Override
    public void onAction(String name, boolean keyPressed, float tpf) {
        if(!keyPressed) //This ensures the action will fire on button DOWN and not on button UP, or is it up and not down?;
        {
            switch(name)
            {
                case "Click" : ClickActionHandler.mouseClicked(); return;
                case "Pause" : PauseActionHandler.setPaused(!PauseActionHandler.getPaused()); return;
                case "Divide" : AxesToggleActionHandler.toggleAxes(); return;
                case "Plus" : TimeManipulationActionHandler.increaseSpeed(); return;
                case "Minus" : TimeManipulationActionHandler.decreaseSpeed(); return;
                case "Multiply" : TimeManipulationActionHandler.restoreSpeedToDefault(); return;
                default: Logger.outputToGUI(Logger.Type.ERROR, "Key defined in input listener not mapped to an action: " + name); break;
            }
        }
    }
}
