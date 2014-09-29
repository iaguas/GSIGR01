/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel;

import java.io.Serializable;

/**
 *
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia
 */
public class Worker implements Serializable{
   
    private String name, birthDate;
    private Integer id;
    
    // Constructor
    
    public Worker(String name, String birthDate){
        this.name = name;
        this.birthDate = birthDate;
    }
        
    /**
     * Sets an ID for a Worker
     * @param id desired worker id
     */ 
    public void setId(Integer id){
        this.id = id;
    }
    
    // Get privates
    /**
     * Gets an ID of an associated Worker
     * @return id of the associated worker
     */
    public Integer getId(){
        return this.id;
    }
    
    /**
     * Gets the name of an associated Worker
     * @return name of the associated worker
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Gets birth date of an associated Worker
     * @return birth date of the associated worker
     */
    public String getBirthDate(){
        return this.birthDate;
    }
}
