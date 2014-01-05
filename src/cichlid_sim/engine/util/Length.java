package cichlid_sim.engine.util;

import java.math.BigDecimal;

/**
 * This class handles conversions between the in-game world units and real world
 * scientific units (centimeters).
 *
 * @author Wesley Perry
 */
public class Length {
    //The difference between centimeters and game-world units;
    public static final BigDecimal scaleFactor = new BigDecimal("0.1");   //0.1f = Each game unit is 10 cm;
    
    /**
     * Converts centimeters to game world units;
     * 
     * @param cm The number of centimeters to convert to game world units.
     * @return The input number of centimeters converted into game world units.
     */
    public static float cmToWorldUnits(float cm) {
        return scaleFactor.multiply(new BigDecimal(cm)).floatValue();
    }
    
    /**
     * Converts game world units into centimeters.
     * 
     * @param worldUnits The number of world units to convert into centimeters.
     * @return The input number of world units converted into centimeters.
     */
    public static float worldUnitsToCM(float worldUnits) {
        return (new BigDecimal(worldUnits)).divide(scaleFactor).floatValue();
    }
}