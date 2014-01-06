/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cichlid_sim.gui;

import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.game.PipeFromGUI;

/**
 *
 * @author pros
 */
public class PipeInit {

    /*Sends the fish object to the correct pipe. */
     public static void AttribsToPipe(JSONObject obj)
    {  
       PipeFromGUI.addObjectToGameWorld(obj); 
    }
     
    public static void FromPipe(JSONObject obj)
    {
       
       //StockTankDisplay.setJSONArray(PipeFromGUI.getStockTankObjects())
       /*Testing the pipe with the stock panel*/
       // StockTankDisplay.stockTankObject(obj)
     
    }
}
