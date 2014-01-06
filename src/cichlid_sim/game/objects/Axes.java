package cichlid_sim.game.objects;

/**
 * This class describes a set of X, Y, Z (Red, Green, Blue) coordinate axes for a
 * right handed coordinate system.
 * 
 * @author Wesley Perry
 */
public class Axes extends com.jme3.scene.Node {
    //The individual lines which make up the axes
    private com.jme3.scene.Geometry xGeom, yGeom, zGeom;
    
    /**
     * This method constructs a set of axes representing a right handed coordinate
     * system. It uses a red, a green, and a blue line to represent the X, Y, and
     * Z axes respectively. 
     * 
     * @param size The length of each axis measured from the origin in its positive direction.
     * @param positiveAndNegative If these axes will extend in both the negative and positive directions.
     */
    public Axes(String name, float size) {
        this(name, size, size, size, false);
    }
    
    public Axes(String name, float x, float y, float z, boolean positiveAndNegative) {
        super(name);
        com.jme3.asset.AssetManager assetManager = cichlid_sim.engine.app.GameAppManager.getMainGame().getAssetManager();
        
        //Create the lines
        com.jme3.scene.shape.Line xLine;
        com.jme3.scene.shape.Line yLine;
        com.jme3.scene.shape.Line zLine;
        
        if(positiveAndNegative) {
            xLine = new com.jme3.scene.shape.Line(new com.jme3.math.Vector3f(-x,0,0), new com.jme3.math.Vector3f(x,0,0));
            yLine = new com.jme3.scene.shape.Line(new com.jme3.math.Vector3f(0,-y,0), new com.jme3.math.Vector3f(0,y,0));
            zLine = new com.jme3.scene.shape.Line(new com.jme3.math.Vector3f(0,0,-z), new com.jme3.math.Vector3f(0,0,z));
        }
        else {
            com.jme3.math.Vector3f origin = new com.jme3.math.Vector3f(0,0,0);
            xLine = new com.jme3.scene.shape.Line(origin, new com.jme3.math.Vector3f(x,0,0));
            yLine = new com.jme3.scene.shape.Line(origin, new com.jme3.math.Vector3f(0,y,0));
            zLine = new com.jme3.scene.shape.Line(origin, new com.jme3.math.Vector3f(0,0,z));
        }
        
        //Create the Geometry objects from the above lines
        xGeom = new com.jme3.scene.Geometry("xAxis", xLine);
        yGeom = new com.jme3.scene.Geometry("yAxis", yLine);
        zGeom = new com.jme3.scene.Geometry("zAxis", zLine);
        
        //Create the Material objects to apply to the above Geometries (Materials allow color)
        com.jme3.material.Material mat = new com.jme3.material.Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", com.jme3.math.ColorRGBA.Red);
        xGeom.setMaterial(mat);
        
        mat = new com.jme3.material.Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", com.jme3.math.ColorRGBA.Green);
        yGeom.setMaterial(mat);
        
        mat = new com.jme3.material.Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", com.jme3.math.ColorRGBA.Blue);
        zGeom.setMaterial(mat);
        
        //Attach each Geometry to *this* Node
        this.attachChild(xGeom);
        this.attachChild(yGeom);
        this.attachChild(zGeom);
    }
}