package cichlid_sim.game.ai;

import cichlid_sim.engine.util.GameRandomManager;
import cichlid_sim.game.objects.Fish;
import cichlid_sim.game.objects.Pot;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import org.drools.FactHandle;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.DroolsError;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderErrors;

/**
 *
 * @author Jerod
 */
public class RealAI {
    
    private Random rand;
    private WorkingMemory workingMemory;
    
    public RealAI() {
        this.rand = cichlid_sim.engine.util.GameRandomManager.getRand();
        //Load Rules
        try{
            RuleBase ruleBase = initialiseDrools();
            workingMemory = ruleBase.newStatefulSession();
        }
        catch(Exception e){
            System.out.println("Error loading rules");
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    /**
     * This mimics the real life action of the fish:
     * Act, delay while considering the next action, repeat.
     * @param tpf 
     */
    public void update(float tpf) {
                //Check if pause has been requested;
        boolean pauseRequested = cichlid_sim.engine.action.PauseActionHandler.getPaused();
        //Update all fish. If pauseRequested then each Fish is expected to perform its idle animation in-place;
        Node fishNode = cichlid_sim.game.NodeCollection.getNode("FishNode");
        for (Spatial spatialObj : fishNode.getChildren()) {         //Iterate over all fish;
            ((Fish) spatialObj).update(tpf, pauseRequested);        //Update the fish;
            if(!pauseRequested) {
                workingMemory.insert(((Fish) spatialObj));
            }
        }
        Node potNode  = cichlid_sim.game.NodeCollection.getNode("PotNode");
        
        for (Spatial spatialObj : potNode.getChildren()) {         //Iterate over all fish;
            workingMemory.insert(((Pot) spatialObj));
        }
        
        //Fire rules, react to the objects you are aware of.
        workingMemory.fireAllRules();

        //If there is no reaction in progress, move about at random.
        for (Spatial spatialObj : fishNode.getChildren()) {         //Itterate over all fish;
            if(!pauseRequested && !((Fish) spatialObj).isMoving()) {                  //If fish is done moving;
                ((Fish) spatialObj).setMoveSpeed(0.5f + rand.nextFloat() * 0.5f);   //Set the fish's new movement speed;
                ((Fish) spatialObj).setDestinationLocation(GameRandomManager.getRandom3DPointWithinArenaTank(((Fish) spatialObj).getModelBounds()));   //Set the fish's new destination;
            }
        }

        //Forget all fish, react to the new situation on the next update.
        retractCollection(workingMemory);
    }
    
    
    /**
     * Empty the memory
     */
    public static void retractCollection(WorkingMemory workingMemory) {
        Collection collection = workingMemory.getFactHandles();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
                workingMemory.retract((FactHandle)iterator.next());
        }
    } 
    /**
     * Generates a new random location within the tank bounds (TANK BOUNDS HARD CODED).
     * 
     * @return the new location.
     */
    public Vector3f generateNewDestinationLocation() {
        return new Vector3f((rand.nextBoolean() ? 1 : -1)*(rand.nextInt(4) + rand.nextFloat()),(rand.nextBoolean() ? 1 : -1)*(rand.nextInt(2) + (rand.nextFloat()*0.5f)),(rand.nextBoolean() ? 1 : -1)*(rand.nextInt(4) + rand.nextFloat()));
    }

    private RuleBase initialiseDrools() throws IOException, DroolsParserException {
        try{
            System.out.println("Loading rules..");
            PackageBuilder packageBuilder = readRuleFiles();
            return addRulesToWorkingMemory(packageBuilder);    
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private PackageBuilder readRuleFiles() throws DroolsParserException, IOException {
        PackageBuilder packageBuilder = new PackageBuilder();

        String[] ruleFiles = {
            "/cichlid_sim/../../assets/Scripts/Drools/sizeReaction.drl",
            "/cichlid_sim/../../assets/Scripts/Drools/sizeReaction2.drl",
            //"/cichlid_sim/../../assets/Scripts/Drools/centerOnPots.drl"
        };

        for (String ruleFile : ruleFiles) {
            Reader reader = getRuleFileAsReader(ruleFile);
            packageBuilder.addPackageFromDrl(reader);
        }
        assertNoRuleErrors(packageBuilder);

        return packageBuilder;
    }

    private Reader getRuleFileAsReader(String ruleFile) {
        InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);

        return new InputStreamReader(resourceAsStream);
    }

    private RuleBase addRulesToWorkingMemory(PackageBuilder packageBuilder) {
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        org.drools.rule.Package rulesPackage = packageBuilder.getPackage();
        ruleBase.addPackage(rulesPackage);

        return ruleBase;
    }

    private void assertNoRuleErrors(PackageBuilder packageBuilder) {
        PackageBuilderErrors errors = packageBuilder.getErrors();

        if (errors.getErrors().length > 0) {
            StringBuilder errorMessages = new StringBuilder();
            errorMessages.append("Found errors in package builder\n");
            for (int i = 0; i < errors.getErrors().length; i++) {
                DroolsError errorMessage = errors.getErrors()[i];
                errorMessages.append(errorMessage);
                errorMessages.append("\n");
            }
            errorMessages.append("Could not parse knowledge");

            throw new IllegalArgumentException(errorMessages.toString());
        }
    }

    private WorkingMemory initializeMessageObjects(RuleBase ruleBase) {
        WorkingMemory workingMemory = ruleBase.newStatefulSession();

  //      createHelloWorld(workingMemory);
 //       createHighValue(workingMemory);

        return workingMemory;
    }

}