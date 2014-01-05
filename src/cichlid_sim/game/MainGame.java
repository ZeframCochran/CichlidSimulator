package cichlid_sim.game;

import cichlid_sim.engine.action.AxesToggleActionHandler;
import cichlid_sim.engine.action.PauseActionHandler;
import cichlid_sim.engine.action.TimeManipulationActionHandler;
import cichlid_sim.engine.app.GameAppManager;
import cichlid_sim.engine.scene.CustomNode;
import cichlid_sim.engine.scene.models.Models;
import cichlid_sim.engine.util.GameRandomManager;
import cichlid_sim.game.ai.RealAI;
import cichlid_sim.game.objects.Axes;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/*
 * This class is the main 'game' class of this program. It extends the default 
 * jMonkeyEngine game class and contains the game initialization methods, game 
 * update loop, and game render loop.
 *
 * @author Wesley Perry
 */
public class MainGame extends com.jme3.app.SimpleApplication {

    private RealAI realAI;
    private boolean done = false;
    private boolean isLoaded = false;
    
    public static void main(String[] args) {
        new MainGame().start();
    }

    /**
     * This method is called during JME startup before the game loop is started.
     */
    @Override
    public void simpleInitApp() {
        java.util.Random rand = new java.util.Random();
        //Store frequently used game objects in static locations in the game engine;
        GameAppManager.setMainGame(this);
        GameRandomManager.setRand(rand);
        //Load the models used during the game;
        loadObjectModels();
        setupAppSettings();
        setupInputBinds();
        setupWorld();
        
        //Load a previous world save just so we can continue testing;
        loadWorldFromFile();
    }

    /**
     * This method initializes the default settings for this program.
     */
    private void setupAppSettings() {
        //Set the camera settings;
        cam.setFrustumPerspective(60f, (float) cam.getWidth() / cam.getHeight(), 0.01f, 1000f);
        flyCam.setMoveSpeed(3);
        //Turns logging off except for severe errors;
        //java.util.logging.Logger.getLogger("").setLevel(java.util.logging.Level.SEVERE);
        //Turns off on-screen debug output;
        setDisplayStatView(false);
        setDisplayFps(false);
        //Enables click and drag to rotate camera instead of using arrow keys;
        flyCam.setDragToRotate(true);
        //Allows the game canvas to continue unpaused while it does not contain focus (ie: when the outer GUI has focus).
        this.setPauseOnLostFocus(false);
    }
    
    /**
     * This method initializes the key bindings for this program.
     */
    private void setupInputBinds() {
        cichlid_sim.engine.input.InputListener iL = new cichlid_sim.engine.input.InputListener();
        
        inputManager.addMapping("Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_PAUSE));
        inputManager.addMapping("Plus", new KeyTrigger(KeyInput.KEY_ADD));
        inputManager.addMapping("Minus", new KeyTrigger(KeyInput.KEY_SUBTRACT));
        inputManager.addMapping("Multiply", new KeyTrigger(KeyInput.KEY_MULTIPLY));
        inputManager.addMapping("Divide", new KeyTrigger(KeyInput.KEY_DIVIDE));
        
        inputManager.addListener(iL, "Click");
        inputManager.addListener(iL, "Pause");
        inputManager.addListener(iL, "Plus");
        inputManager.addListener(iL, "Minus");
        inputManager.addListener(iL, "Multiply");
        inputManager.addListener(iL, "Divide");
    }

    /**
     * This function uses the static cichlid_sim.engine.scene.models.Models class
     * to load and store all 3d models used in this program;
     */
    private void loadObjectModels() {
        //Load fish;
        Models.addModelFromFile("Fish.Male", Models.Type.FISH, "Models/Fish/Male.j3o", "Textures/Fish/Male.png");
        Models.addModelFromFile("Fish.Female", Models.Type.FISH, "Models/Fish/Female.j3o", "Textures/Fish/Female.png");

        //Load Pots;
        Models.addModelFromFile("Pot", Models.Type.POT, "Models/Pots/JustPot.j3o", "Textures/Pot/brown.png");

        //Load Plants;
        Models.addModelFromFile("Plant", Models.Type.PLANT, "Models/Plants/Plant.j3o", "Textures/Plant/Plant.png");
    }
    
    private void setupWorld() {
        //Set up the Node structure for game objects;
        CustomNode clickableObjectNode = new CustomNode("ClickableObjectNode");
        CustomNode nonClickableObjectNode = new CustomNode("NonClickableObjectNode");
        CustomNode gameObjectNode = new CustomNode("GameObjectNode");
        
        gameObjectNode.attachChild(clickableObjectNode);
        gameObjectNode.attachChild(nonClickableObjectNode);
        rootNode.attachChild(gameObjectNode);
        
        //Set up the different tank nodes for storing fish;
        //CustomNode arenaTank = new CustomNode("ArenaTank");         //Fish in the arena tank are stored in the FishNode;
        CustomNode stockTank = new CustomNode("StockTank");         //Fish in the stock and isolation tanks shouldn't be displayed in the arena tank.
        CustomNode isolationTank = new CustomNode("IsolationTank");
        
        stockTank.setCullHint(Spatial.CullHint.Always);             //So, cull the nodes;
        isolationTank.setCullHint(Spatial.CullHint.Always);
        
        nonClickableObjectNode.attachChild(stockTank);
        nonClickableObjectNode.attachChild(isolationTank);
                
        //Register those Nodes with the NodeCollection;
        cichlid_sim.game.NodeCollection.addNode(rootNode, "RootNode");
        //CustomNode objects are automatically added to the NodeCollection;
        //cichlid_sim.game.NodeCollection.addNode(clickableObjectNode, "ClickableObjects");
        //cichlid_sim.game.NodeCollection.addNode(nonClickableObjectNode, "NonClickableObjects");
        //cichlid_sim.game.NodeCollection.addNode(gameObjectNode, "GameObjectNode");
        
        //Setup GameObject Nodes;
        setupTank(nonClickableObjectNode);
        setupFish(clickableObjectNode);
        setupPots(clickableObjectNode);
        setupPlants(clickableObjectNode);
        setupAxes(nonClickableObjectNode);
        
        //Setup Lights;
        setupLights();
        
                
        //Start the AI
        realAI = new RealAI();
        
        this.isLoaded = true;
    }

    /**
     * This method builds a tank and attaches it to the scene graph.
     * 
     * @param parent The parent node of the TankNode.
     */
    private void setupTank(Node parent) {
        CustomNode tankNode = new CustomNode("TankNode");
        parent.attachChild(tankNode);
    }
    
    /**
     * This method sets up the Node structure for Fish objects.
     * 
     * @param parent The parent node of the FishNode.
     */
    private void setupFish(Node parent) {
        CustomNode fishNode = new CustomNode("FishNode");
        parent.attachChild(fishNode);

    }
    
    /**
     * This method sets up the Node structure for Pot objects.
     * 
     * @param parent The parent node of the PotNode.
     */
    private void setupPots(Node parent) {
        CustomNode potNode = new CustomNode("PotNode");
        //Pots are too light by default. A reverse ambient light fixes that;
        AmbientLight al;
        al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(-1.5f));        
        potNode.addLight(al);
        parent.attachChild(potNode);
    }
    
    /**
     * This method sets up the Node structure for Plant objects.
     * 
     * @param parent The parent node of the PlantNode.
     */
    private void setupPlants(Node parent) {
        CustomNode plantNode = new CustomNode("PlantNode");
        parent.attachChild(plantNode);
    }
    
    /**
     * This method sets up the axes node and creates a representation of the
     * default (right handed) world coordinate system.
     * 
     * @param parent The parent node of the AxesNode.
     */
    private void setupAxes(Node parent) {
        CustomNode axesNode = new CustomNode("AxesNode");
        axesNode.attachChild(new Axes("Axes", 5f));
        AxesToggleActionHandler.addAxes(axesNode);
        parent.attachChild(axesNode);
    }
    
    /**
     * This method adds lighting to the game world.
     */
    private void setupLights() {
        //Add simple light so we can see the world;
        com.jme3.light.DirectionalLight sun = new com.jme3.light.DirectionalLight();
        sun.setColor(com.jme3.math.ColorRGBA.White.mult(0.5f));
        sun.setDirection(new com.jme3.math.Vector3f(-0.2f, -1.0f, -0.2f));
        rootNode.addLight(sun);
        
        //Add ambient light for a more realistic look;
        com.jme3.light.AmbientLight al = new com.jme3.light.AmbientLight();
        al.setColor(com.jme3.math.ColorRGBA.White.mult(2.0f));
        rootNode.addLight(al);
    }

    /**
     * This method is called on each iteration of the game update loop. It handles
     * the update logic of the game world.
     * 
     * @param tpf Time per frame. The time (in seconds) since the last update.
     */
    @Override
    public void simpleUpdate(float tpf) {
        //Do the things that need to be done once... this really shouldn't be here but it needs to happen after the app starts ... -_-
        if(!done) {
            //Disable zoom on mouse wheel scroll;
            inputManager.deleteMapping("FLYCAM_ZoomIn");
            inputManager.deleteMapping("FLYCAM_ZoomOut");
            done = true;
        }
        //Check if 'skip forward' has been requested;
        if(TimeManipulationActionHandler.getSkipRequested()) {
            this.batchUpdate(TimeManipulationActionHandler.getSkipRequestedSeconds());
            TimeManipulationActionHandler.resetSkipRequested();
        }
        
        //Multiply the time since last frame by the current game speed to determine how much game time has passed;
        float gameSpeed = TimeManipulationActionHandler.getCurrentSpeed();
        tpf *= gameSpeed;
        //If the current speed is less than the default speed run update once;
        if(gameSpeed < 1) {
            customUpdate(tpf);
        }
        else {
            /* For all other values update 'game speed' times. 
             * For example: if the time since the last frame is 1 second and the 
             * current speed is 5.25 then we will run update 5 times with a tpf 
             * value of 1.05 on each of those 5 updates. That will result in a
             * total game time jump of 5.25 seconds.
             * (1 second real time passed * 5.25 speed modifier) = 5.25 seconds.
             * 
             * Using this method allows us 'limitless' speed possibilities while
             * ensuring we keep the same level of granularity in our time jumps.
             * 
             * ie: using the above example again. If 5 seconds passes between each
             * update the fish have 5 seconds to swim around without being checked
             * by the AI and without being rendered to the display. 
             */
            for(int i=0;i<(int)gameSpeed;i++) {
                customUpdate(tpf/gameSpeed);    //Update @ the normal tpf (int)gameSpeed # times;
            }
            //Update @ normal tpf * (int)gameSpeed 1 more time;
            customUpdate(tpf - ((int)gameSpeed * tpf / gameSpeed));
        }
        //Update the GUI's clock display with the current game time;
        PipeToGUI.setGameClock(TimeManipulationActionHandler.getGameTime());
    }
    
    /**
     * This method provides a way to force a large number of game world updates 
     * in-between rendering to the display.
     * 
     * @param tpf How much time to update (in seconds).
     */
    public void batchUpdate(float tpf) {
        //Aim for .01666666667 game seconds per update (60 fps).
        float targetFPS = 60;
        float desiredTPF = 1/targetFPS;                 //If this is too high we wont get acurate updates (because on each update objects move proportaional to this amount).
        if(tpf > desiredTPF) {
            float numUpdatesRequired = tpf*targetFPS;   //How many times will we need to update with a tpf value of desiredTPF?
            for(int i=0;i<numUpdatesRequired;i++) {     //Update that many times with tpf value = desiredTPF;
                customUpdate(desiredTPF);
            }
        }
        else {      //actual tpf is lower than the desired, so just update.
                    //In this case we don't need to worry about the tpf because a lower than desired value will only produce a MORE accurate game update.
            customUpdate(tpf);
        }
    }
    
    /**
     * This method contains the update logic for the game.
     * 
     * @param tpf Time in between each update.
     */
    private void customUpdate(float tpf) {
        //Check if pause has been requested;
        if(PauseActionHandler.getPaused()) {//Game is paused, so only update things that should be updated while game is paused. ... 'pause menu animation' ... duno;
        }
        else {                              //Game is not paused, so update things that should only be updated while the game is not paused, like the game clock;
            //Increment the game time;
            TimeManipulationActionHandler.incrementGameTime(tpf);
        }
        //Fish update regardless of pause (when game = paused fish don't move but are free to idle animate);
        realAI.update(tpf);
    }
    
    /**
     * @return true if this app is finished with its init and is running its main loop. False otherwise;
     */
    public boolean isRunning() {
        return done;
    }
    
    /**
     * @return True if the game world has finished initial setup. False otherwise. 
     */
    public boolean isLoaded() {
        return this.isLoaded;
    }
    
    /**
     * A temporary test method to populate the game world with the game objects stored in the test file.
     */
    private void loadWorldFromFile() {
        PipeFromGUI.loadWorldFromFile("assets/Scripts/testLoad.txt");
    }
 }
