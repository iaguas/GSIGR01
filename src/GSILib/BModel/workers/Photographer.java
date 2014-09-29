/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel.workers;

import GSILib.BModel.Worker;

/**
 *
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia
 */
public class Photographer extends Worker{
    private String regularResidence, holidayResidence;
    
    // Constructor
    
    public Photographer(String name, String birthDate, String regularResidence, String holidayResidence){
        
        super(name, birthDate);
        
        this.regularResidence = regularResidence;
        this.holidayResidence = holidayResidence;
    }
    
    // Get privates
    
    /**
     * Gets the regular residence of the associated photographer
     * @return the regular residence of the associated photographer
     */
    public String getRegularResidence(){
        return this.regularResidence;
    }
    
    /**
     * Gets the holiday residence of the associated photographer
     * @return the holiday residence of the associated photographer
     */
    public String getHolidayResidence(){
        return this.holidayResidence;
    }
}