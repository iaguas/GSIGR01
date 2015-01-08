/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel;

import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;

/**
 * This is the class Worker, which includes Journalist and Photographer.
 * This is the main class of people of the system. That's why all the workers are
 * hereded of this class.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public abstract class Worker implements Serializable {
   
    private String id, name, birthDate;
    
    /**
     * Class constructor of a worker with ID, name and birth date.
     * @param id This is an unique ID of the worker.
     * @param name The name of the worker.
     * @param birthDate The birth dathe of the worker.
     */
    protected Worker(String id, String name, String birthDate){
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }
    
    /**
     * Empty class constructor.
     */
    protected Worker(){
        // Constructor nulo
    }
        
    /**
     * Sets an ID for a Worker.
     * @param id desired worker id
     */ 
    public void setId(String id){
        this.id = id;
    }
    
    /**
     * Sets an Name for a Worker.
     * @param name desired worker name
     */ 
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Sets an BirthDate for a Worker.
     * @param birthDate desired worker birthDate
     */ 
    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }
    
    /**
     * Lowest rank method which obtains the atribute values for Worker from an
     * XML Element. 
     * @param xmlWorker Element type which contains useful data (id, name and birthdate)
     */ 
    protected void loadFromElement(Element xmlWorker){
        
        this.id = xmlWorker.getAttribute("id");
        this.name = xmlWorker.getAttribute("name");
        this.birthDate = xmlWorker.getAttribute("birthDate");
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
    
    /**
     * Copies name and birthdate from an associated Worker to a new one
     * @param newWorker original Worker the fields are read from
     */
    protected void copyValuesFrom(Worker newWorker){
        this.name = newWorker.getName();
        this.birthDate = newWorker.getBirthDate();
    }
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos del trabajador.
        return "Worker ID: " + this.getId() + "\n  Name: " + this.getName()
                + "\n  BirthDate: " + this.getBirthDate();
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param w a worker
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Worker w){
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(w.getId());
    }

    /**
     * Worker to JSON object parser
     * @return json JSON object containing a Worker
     * @throws JSONException exception derived from JSON inputing
     */
    public JSONObject getJSONObject() throws JSONException{
        
        JSONObject json = new JSONObject();
        
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("birthDate", this.birthDate);
        
        return json;
    }
}
