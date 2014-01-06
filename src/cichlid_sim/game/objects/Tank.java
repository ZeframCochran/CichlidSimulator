package cichlid_sim.game.objects;

import cichlid_sim.engine.action.TimeManipulationActionHandler;
import cichlid_sim.engine.app.GameAppManager;
import cichlid_sim.engine.json.JSONObject;
import cichlid_sim.engine.scene.CustomNode;
import cichlid_sim.engine.util.Length;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.WireBox;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import java.text.DecimalFormat;

/**
 * This class describes a rectangular fish tank.
 *
 * @author Wesley Perry
 */
public class Tank extends AbstractGameObject
{
    private static boolean created = false;
    
    public enum Type {
        ARENA, STOCK, ISOLATION
    }
    
    private float xSize;
    private float ySize;
    private float zSize;
    private Vector3f savedSize;
    private float temperature;
    
    /**
     * This method constructs the various rectangles used to represent the fish tank.
     * @param tankX 1/2 the length of the X side of the tank.
     * @param tankY 1/2 the length of the Y side of the tank.
     * @param tankZ 1/2 the length of the Z side of the tank.
     */
    public Tank(String name, int uniqueID, Vector3f size)
    {
        super(name, uniqueID);
        //Save the input size so we can spit it back out in the toJSON() ... damn floating point inacuracies...
        this.savedSize = size;
        //Internally, length is defined from the origin outward in the positive and negative directions
        //To keep the tank sides the correct size we divide by 2
        //(Side length = 5 = (-2.5 <-- 0 --> 2.5)
        size = size.divide(2);
        xSize = (float)Length.cmToWorldUnits(size.x);
        ySize = (float)Length.cmToWorldUnits(size.y);
        zSize = (float)Length.cmToWorldUnits(size.z);
        this.temperature = 25;
        
        AssetManager assetManager = GameAppManager.getMainGame().getAssetManager();
        
        setupTankOutline(assetManager, xSize, ySize, zSize);
        setupGlassEdge(assetManager, xSize, ySize, zSize);
        setupWallpaper(assetManager);
        created = true;
    }
    
    /**
     * Builds a white outline around the tank to increase ease-of-view.
     * 
     * @param assetmanager The AssetManager to use for loading materials.
     * @param tankX 1/2 of the X bound of the tank.
     * @param tankY 1/2 of the Y bound of the tank.
     * @param tankZ 1/2 of the Z bound of the tank. 
     */
    private void setupTankOutline(AssetManager assetManager, float tankX, float tankY, float tankZ) {
        //Create the outline for the tank
        WireBox tankOutline = new WireBox(tankX, tankY, tankZ);
        Geometry tankBox = new Geometry("Box",tankOutline);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.White);
        tankBox.setMaterial(material);
        this.attachChild(tankBox);
    }
      
    /**
     * Adds 'glass' side panels to the tank.
     * 
     * @param assetmanager The AssetManager to use for loading materials.
     * @param tankX 1/2 of the X bound of the tank.
     * @param tankY 1/2 of the Y bound of the tank.
     * @param tankZ 1/2 of the Z bound of the tank. 
     */
    private void setupGlassEdge(AssetManager assetManager, float tankX, float tankY, float tankZ) {
        //Create the tanks semitransparent 'glass' edges
        Box b = new Box(tankX, tankY, tankZ);
        Geometry tank = new Geometry("Box", b);
        Material material2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material2.setColor("Color", new ColorRGBA(.5f, .5f, 1, .25f));
        material2.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        tank.setMaterial(material2);
        tank.setQueueBucket(RenderQueue.Bucket.Transparent);
        this.attachChild(tank);
    }
    
    /**
     * Adds the provided aquarium themed wallpaper to the tank edges, top and bottom.
     * @param assetManager 
     */
    private void setupWallpaper(AssetManager assetManager) {
        CustomNode wallpaperNode = new CustomNode("TankWallpaper");
        
        //Create the panels that will become the tanks wallpaper
        
        //Front and back
        Quad front = new Quad(xSize*2, ySize*2);
        Geometry frontGeom = new Geometry("frontPanel", front);
        Geometry backGeom = new Geometry("backPanel", front);
        Material materialFront = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture textureFront =  GameAppManager.getMainGame().getAssetManager().loadTexture("Textures/Tank/Front.jpg");
        materialFront.setTexture("DiffuseMap",textureFront);
        backGeom.setMaterial(materialFront);
        //backGeom.rotate(0, (float)java.lang.Math.toRadians(180), 0)
        backGeom.move(-xSize, -ySize, -zSize);
        frontGeom.setMaterial(materialFront);
        frontGeom.rotate(0, (float)java.lang.Math.toRadians(180), 0);
        frontGeom.move(xSize, -ySize, zSize);
        wallpaperNode.attachChild(frontGeom);
        wallpaperNode.attachChild(backGeom);
        
        //Left and right
        Quad left = new Quad(zSize*2, ySize*2);
        Geometry leftGeom = new Geometry("leftPanel", left);
        Geometry rightGeom = new Geometry("rightPanel", left);
        Material materialLeft = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture textureLeft =  GameAppManager.getMainGame().getAssetManager().loadTexture("Textures/Tank/Front.jpg");
        materialLeft.setTexture("DiffuseMap",textureLeft);
        rightGeom.setMaterial(materialLeft);
        rightGeom.rotate(0, (float)java.lang.Math.toRadians(-90), 0);
        rightGeom.move(xSize, -ySize, -zSize);
        leftGeom.setMaterial(materialLeft);
        leftGeom.rotate(0, (float)java.lang.Math.toRadians(90), 0);
        leftGeom.move(-xSize, -ySize, zSize);
        wallpaperNode.attachChild(leftGeom);
        wallpaperNode.attachChild(rightGeom);
        
        //Top
        Quad top = new Quad(xSize*2, zSize*2);
        Geometry topGeom = new Geometry("topPanel", top);
        Material materialTop = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture textureTop =  GameAppManager.getMainGame().getAssetManager().loadTexture("Textures/Tank/Front.jpg");
        materialTop.setTexture("DiffuseMap",textureTop);
        topGeom.setMaterial(materialTop);
        topGeom.rotate((float)java.lang.Math.toRadians(90), 0, 0);
        topGeom.move(-xSize, ySize, -zSize);
        //wallpaperNode.attachChild(topGeom)
        
        //Bottom
        Geometry bottomGeom = new Geometry("bottomPanel", top);
        Material materialBottom = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture textureBottom =  GameAppManager.getMainGame().getAssetManager().loadTexture("Textures/Tank/Bottom.jpg");
        materialBottom.setTexture("DiffuseMap",textureBottom);
        bottomGeom.setMaterial(materialBottom);
        bottomGeom.rotate((float)java.lang.Math.toRadians(-90), 0, 0);
        bottomGeom.move(-xSize, -ySize, zSize);
        wallpaperNode.attachChild(bottomGeom);
        
        this.attachChild(wallpaperNode);
    }
    
    /**
     * Resets the world variables stored in this tank object.
     */
    public static void reset() {
        created = false;
    }
    
    /**
     * Getters for tank size. Returned values are 1/2 the size of the specified side.
     */
    public float getX() {
        return xSize;
    }
    
    public float getY() {
        return ySize;
    }
    
    public float getZ() {
        return zSize;
    }
    
    public void setTemperature(float newTemp) {
        this.temperature = newTemp;
    }
    
    public float getTemperator() {
        return this.temperature;
    }
    
    public static boolean isCreated() {
        return created;
    }

    /**
     * Converts this game object into a JSONObject.
     * 
     * @return This object represented as a JSONObject.
     */
    @Override
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("Type", IGameObject.Type.TANK);
        object.put("ID", this.getUniqueID());
        object.put("Name", this.name);
        object.put("SizeX", Float.toString(savedSize.x));
        object.put("SizeY", Float.toString(savedSize.y));
        object.put("SizeZ", Float.toString(savedSize.z));
        object.put("Temperature", this.temperature);
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        df.setMaximumFractionDigits(4);
        object.put("GameTime", df.format(TimeManipulationActionHandler.getGameTime()));
        return object;
    }
}