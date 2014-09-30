/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.BModel;

import java.io.Serializable;

/**
 * This is the class Worker, which includes Journalist and Photographer.
 * This is the main class of people of the system. That's why all the workers are
 * hereded of this class.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Worker implements Serializable{
   
    private String id, name, birthDate;
    
    /**
     * Class constructor of a worker with ID, name and birth date.
     * @param id This is an unique ID of the worker.
     * @param name The name of the worker.
     * @param birthDate The birth dathe of the worker.
     */
    public Worker(String id, String name, String birthDate){
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }
        
    /**
     * Sets an ID for a Worker.
     * @param id desired worker id
     */ 
    public void setId(String id){
        this.id = id;
    }
    
    /**
     * Gets an ID of an associated Worker
     * @return id of the associated worker
     */
    public String getId(){
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
