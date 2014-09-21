/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel;

import java.io.Serializable;

/**
 *
 * @author Alvaro
 */
public class Worker implements Serializable{
   
    private String name, birthDate;
    private Integer id;
    
    // Constructor
    
    public Worker(String name, String birthDate){
        this.name = name;
        this.birthDate = birthDate;
    }
    
    // Set privates
    
    public void setId(Integer id){
        this.id = id;
    }
    
    // Get privates
    
    public Integer getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getBirthDate(){
        return this.birthDate;
    }
}
