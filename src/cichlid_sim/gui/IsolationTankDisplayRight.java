/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cichlid_sim.gui;

import cichlid_sim.engine.json.JSONArray;
import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.engine.logger.Logger;
import cichlid_sim.game.PipeFromGUI;
import cichlid_sim.game.objects.Tank;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author pros
 */
public class IsolationTankDisplayRight extends JPanel implements ActionListener, MouseListener {

    private static boolean isSelected=false;
    private static JSONObject isolationObject = new JSONObject();
    private JSONObject importFish = new JSONObject();
    private Timer timer = new Timer(20,this);
    
    public IsolationTankDisplayRight()
    {
        this.setBounds(2, 2, 200, 100);
        addMouseListener(this);
        timer.start();
    }
    
    public static void displayFishInISO()
    {        
        {
         try{
             if(PipeFromGUI.getIsolationTankObjects().has("FISH"))
                {
                    JSONArray array =(JSONArray)PipeFromGUI.getIsolationTankObjects().get("FISH");                    
                    if(array.length()==2)
                    {
                        isolationObject=array.getJSONObject(1);                  
                    }
                }
            }
            catch(java.lang.NullPointerException nfe)
            {Logger.outputToGUI(Logger.Type.ERROR, "No Current Fish inside the Isolation tank " +nfe);}
        }
    }
    
      /*Remove fish object from the System*/
    public void RemoveFishObject(JSONObject fish)
    {
        try{
        if(fish.getInt("ID")==(int)isolationObject.getInt("ID"))
        { 
            int result=JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?","Confirm Deletion",JOptionPane.YES_NO_OPTION);
            if(result==JOptionPane.YES_OPTION) 
                {    
                     PipeFromGUI.removeObjectFromGameWorld(isolationObject.getInt("ID"));
                     isolationObject.clear();
                     setSelected(false); 
                }                                  
        }                  
        }
          catch(cichlid_sim.engine.json.JSONException nde){}
   
          
    }
    
    /*Remove fish object from the stock tank*/
    public void importTOGameWorld(JSONObject fish,String name)
    {   
            
        try{
        if(fish.getInt("ID")==isolationObject.getInt("ID"))
        { 
            if(name=="STOCK"){isolationObject.put("Tank", Tank.Type.STOCK);}           
            else{isolationObject.put("Tank", Tank.Type.ARENA);}          
            isolationObject.remove("posY");
            isolationObject.remove("posX");
            PipeFromGUI.updateGameWorldObject(isolationObject);                  
            isolationObject.clear();                
            setSelected(false);                                
        }                  
        }
          catch(cichlid_sim.engine.json.JSONException nde){
          Logger.outputToGUI(Logger.Type.ERROR," "+nde);}                   
    }
    
    @Override
   public void paintComponent(Graphics g)
        {       
            /*set the background color*/
            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D)g; 
            g2d.setColor(Color.black);
            g2d.fillRect(0, 0, getWidth(), getHeight());
          
            /*Set the border color and thickness*/
            Stroke oldStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(5));
            g2d.setColor(Color.gray);
            g2d.drawRect(0, 0, getWidth(), getHeight());
            g2d.setStroke(oldStroke);
            
            /*Calls to draw base on gender*/
          
            try{
            if(isolationObject.getBoolean("Gender"))
            {
                  isolationObject.put("SizeX", new Integer(164));
                  isolationObject.put("SizeY", new Integer(100));
                  Image male = new ImageIcon(this.getClass().getResource("img/Midas.png")).getImage();                  
                  g2d.drawImage(male, 20, 0, 164,100,null);            
                  
            }
            else
            {
                isolationObject.put("SizeX", new Integer(194));
                isolationObject.put("SizeY", new Integer(100));   
                Image female = new ImageIcon(this.getClass().getResource("img/BandedCichlid.png")).getImage();   
                g2d.drawImage(female, 20, 0, 194,100,null);   
            }
            }catch(cichlid_sim.engine.json.JSONException ndf)
            {
                //Do nothing
            }
                       
            
            if(isSelected)
            {   
                try{
                if(importFish.getBoolean("Gender"))
                {
                    Image male = new ImageIcon(this.getClass().getResource("img/Midas_select.png")).getImage();
                    g2d.drawImage(male,20,0, 164,100,null);                   
                }
                else
                {
                    Image female = new ImageIcon(this.getClass().getResource("img/BandedCichlid_select.png")).getImage();
                    g2d.drawImage(female,20,0,194,100,null);                    
                }}
                catch(cichlid_sim.engine.json.JSONException nfe){}
            }
        }
   
    @Override
    public void actionPerformed(ActionEvent e) {
       repaint();   
      
    }
    public static void setJSONObject(JSONObject obj){isolationObject=obj;}
    public JSONObject getJSONObject(){return isolationObject;}
    public JSONObject getImportFish(){return importFish;}
    public void setImportFish(JSONObject i){importFish=i;}
    public boolean isSelect(){return isSelected;}
    public void setSelected(boolean f){isSelected=f;}

    @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        EditMenu.init(isolationObject,2); 
        importFish=isolationObject;
        setSelected(true);
        setImportFish(importFish);             

    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }
}
