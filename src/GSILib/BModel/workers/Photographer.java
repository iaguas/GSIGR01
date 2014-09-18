/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel.workers;

import GSILib.BModel.Worker;

/**
 *
 * @author Alvaro
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
    
    public String getRegularResidence(){
        return this.regularResidence;
    }
    public String getHolidayResidence(){
        return this.holidayResidence;
    }
}
