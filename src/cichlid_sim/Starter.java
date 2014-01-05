package cichlid_sim;

import cichlid_sim.game.MainGame;
import cichlid_sim.gui.MainGUI;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JPanel;

/**
 * The starter class for the cichlid simulator project.
 *
 * @author Wesley Perry
 */
public class Starter 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        /* This function creates an anyonmous class which implements the Runnable
         * interface. Through its run() method, this class will create the main
         * GUI, jme canvas and place the jme canvas into the created GUI.
         */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Create the main Graphical User Interface;
                MainGUI gui = new MainGUI();                
                
                //Get the center panel from the GUI;
                JPanel tankPanel = gui.getTankPanel();
                Dimension dim = tankPanel.getPreferredSize();

                //Initialize a jme AppSettings object with the desired jme canvas width and height;
                AppSettings settings = new AppSettings(true);
                settings.setWidth(dim.width);
                settings.setHeight(dim.height);
                settings.setFrameRate(60);  //Set soft fps limit;

                //Create and initialize the main jme game;
                MainGame mainGame = new MainGame();
                mainGame.setSettings(settings);
                mainGame.createCanvas();    //Creates a jme canvas;
                JmeCanvasContext ctx = (JmeCanvasContext) mainGame.getContext();
                ctx.setSystemListener(mainGame);
                ctx.getCanvas().setPreferredSize(dim);  //Match the jme cavas size with the GUI canvas size;

                //Add the jme canvas to the GUI;
                tankPanel.add(ctx.getCanvas(), java.awt.BorderLayout.CENTER);
                gui.pack();

                //gui.setVisible(true);
                mainGame.startCanvas(); //Start the game loop;
            }
        });
    }
}