/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cichlid_sim.gui;
import cichlid_sim.engine.json.JSONArray;
import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.engine.logger.Logger;
import cichlid_sim.game.PipeFromGUI;
import cichlid_sim.game.PipeToGUI;
import cichlid_sim.game.objects.Tank;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author pros
 */
public class StockTankDisplay extends JPanel implements ActionListener {
  
   private static JSONArray stockObject = new JSONArray();
   private JSONObject importFish = new JSONObject();
   private Boolean isSelected=false;
   private int posX=0;
   private int posY=0; 
   private Timer timer = new Timer(20,this);
   private static int max = 16; /*The maximun number of fish a stock tank can hold*/
   
   public StockTankDisplay()
        {   
         setBounds(10, 11, 570, 500);
         stockTankObject();          
         timer.start();
        }   
   /*Remove fish object from the System*/
    public void RemoveFishObject(JSONObject importFish)
    {
         for(int x=0;x<stockObject.length();x++)
        {
                JSONObject obj = (JSONObject)stockObject.get(x);  
                try{
                if(importFish.getInt("ID")==(int)obj.getInt("ID"))
                { 
                    int result=JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?","Confirm Deletion",JOptionPane.YES_NO_OPTION);
                    if(result==JOptionPane.YES_OPTION) 
                        {    
                             PipeFromGUI.removeObjectFromGameWorld(obj.getInt("ID"));
                             stockObject.remove(x);
                             setSelected(false);  
                             int max = getMax();
                             max++;
                             setMax(max);
                        }                                  
                }                  
                }
                  catch(cichlid_sim.engine.json.JSONException nde){}
    
           }   
          
    }
    /*Remove fish object from the stock tank*/
    public void importArena(JSONObject importFish)
    {   
        for(int x=0;x<stockObject.length();x++)
        {
                JSONObject obj = (JSONObject)stockObject.get(x);    
                try{
                if(importFish.getInt("ID")==obj.getInt("ID"))
                {                    
                  obj.put("Tank", Tank.Type.ARENA); 
                  obj.remove("posY");
                  obj.remove("posX");
                  PipeFromGUI.updateGameWorldObject(obj);
                  stockObject.remove(x);
                  setSelected(false); 
                  int max = getMax();
                  max++;
                  setMax(max);                  
                }                  
                }
                  catch(cichlid_sim.engine.json.JSONException nde){
                  Logger.outputToGUI(Logger.Type.ERROR," "+nde);}                     
           } 
    }
   
    /*Loop through the JSONArray to get all fish objects that are marked as Stock Tank*/
    public void stockTankObject()
    {       
        /*loop and add the position of the fish
         *the posX and the posY are the coordinate in which the object is drawn*/
        for(int x = 0;x<stockObject.length();x++)
        {
            JSONObject obj = (JSONObject)stockObject.get(x);
            try{          //Make sure to check object type "Stock Tank"
                obj.put("posX", new Integer(getPosX()));
                obj.put("posY", new Integer(getPosY()));  
                setPosX(getPosX()+190); 
            }catch(cichlid_sim.engine.json.JSONException nfd){}                    
            if(getPosX()==570)
                {                    
                    setPosY(getPosY()+100);
                    setPosX(0);
                }
        }
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
            for(int x = 0;x<stockObject.length();x++)
            {               
            JSONObject obj =(JSONObject)stockObject.get(x);
            try{
            if(obj.getBoolean("Gender"))
            {
                  obj.put("SizeX", new Integer(164));
                  obj.put("SizeY", new Integer(100));
                  Image male = new ImageIcon(this.getClass().getResource("img/Midas.png")).getImage();                  
                  g2d.drawImage(male, (int)obj.get("posX"), (int)obj.get("posY"), 164,100,null);            
                  
            }
            else
            {
                obj.put("SizeX", new Integer(194));
                obj.put("SizeY", new Integer(100));   
                Image female = new ImageIcon(this.getClass().getResource("img/BandedCichlid.png")).getImage();   
                g2d.drawImage(female, (int)obj.get("posX"), (int)obj.get("posY"), 194,100,null);   
            }
            }catch(cichlid_sim.engine.json.JSONException ndf)
            {
                //Do nothing
            }
            }               
          
            if(isSelected)
            {   
                try{
                if(importFish.getBoolean("Gender"))
                {
                    Image male = new ImageIcon(this.getClass().getResource("img/Midas_select.png")).getImage();
                    g2d.drawImage(male,(int)importFish.get("posX"),(int)importFish.get("posY"), 164,100,null);
                }
                else
                {
                    Image female = new ImageIcon(this.getClass().getResource("img/BandedCichlid_select.png")).getImage();
                    g2d.drawImage(female,(int)importFish.get("posX"),(int)importFish.get("posY"),194,100,null);
                }}
                catch(cichlid_sim.engine.json.JSONException nfe){}
            }
            int y=100;
	    for(int j=0;j<4;j++){
                g2d.setColor(Color.gray);
		g2d.drawLine(0, y, 570, y);
		y+=100;	
		}           
          
        }
    /*Setters and Getters*/
    public static void setJSONArray(JSONArray obj){stockObject=obj;}
    public JSONArray getJSONArray(){return stockObject;}
    public void setPosX(int x){posX=x;}
    public void setPosY(int y){posY=y;}  
    public int getPosX(){return posX;}
    public int getPosY(){return posY;}    
    public boolean isSelect(){return isSelected;}
    public void setSelected(boolean f){isSelected=f;}
    public void setImportFish(JSONObject i){importFish=i;}
    public int getMax(){return max;}
    public void setMax(int i){max=i;}

    @Override
    public void actionPerformed(ActionEvent e) {
       repaint();  
    }
    
}
