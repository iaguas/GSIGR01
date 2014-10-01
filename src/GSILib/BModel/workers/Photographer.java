/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.BModel.workers;

import GSILib.BModel.Worker;

/**
 * This is the class Photographer.
 * He represent a worker who takes pictures into de system. He has ID, name,
 * birthDate and, especialy, its residence and holyday residence.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Photographer extends Worker{
    private String regularResidence, holidayResidence;
    
    /**
     * Class constructor
     * @param id This is an unique ID of the worker.
     * @param name The name of the worker.
     * @param birthDate The birth dathe of the worker.
     * @param regularResidence The regular residence of a photographer.
     * @param holidayResidence The residence during the holydays of a photographer.
     */
    public Photographer(String id, String name, String birthDate, String regularResidence, String holidayResidence){
        
        super(id, name, birthDate);
        
        this.regularResidence = regularResidence;
        this.holidayResidence = holidayResidence;
    }
    
    /**
     * Gets the regular residence of the associated photographer
     * @return the regular residence of the associated photographer
     */
    public String getRegularResidence(){
        // Devolvemos la residencia habitual.
        return this.regularResidence;
    }
    
    /**
     * Gets the holiday residence of the associated photographer
     * @return the holiday residence of the associated photographer
     */
    public String getHolidayResidence(){
        // Devolvemos la residencia vacacional.
        return this.holidayResidence;
    }
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos del fotografo.
        return "Photographer ID: " + this.getId() + "\n  Name: " + this.getName()
                + "\n  BirthDate: " + this.getBirthDate() + "\n  Regular residence: " 
                + this.regularResidence + "\n  Holiday residence: " + this.holidayResidence;
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param p a photographer
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Photographer p){
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(p.getId());
    }
}