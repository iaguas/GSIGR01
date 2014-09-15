/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel;

/**
 *
 * @author Alvaro
 */
public class Worker {
   
    private String id, name, birthDate;
    
    // Constructor
    
    public Worker(String name, String birthDate){
        this.name = name;
        this.birthDate = birthDate;
    }
    
    // Get privates
    
    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getBirthDate(){
        return this.birthDate;
    }
}
