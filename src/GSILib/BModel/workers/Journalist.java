/**
 * This is the class Journalist.
 */
package GSILib.BModel.workers;

import GSILib.BModel.Worker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia
 */
public class Journalist extends Worker {
    
    private List<String> interests = new ArrayList<String>();
    
    /**
     * Class constructor
     * @param id
     * @param name
     * @param birthDate
     * @param interests
     */
    public Journalist(String id, String name, String birthDate, ArrayList interests){
        
        super(id, name, birthDate);
 
        this.interests = interests;
    }
    
    // Overdrive methods equals and toString.
    @Override
    public String toString(){
        return "Journalist ID: " + this.getId() + "\n  Name: " + this.getName()
                + "\n  BirthDate: " + this.getBirthDate() + "\n  Interests: " +
                this.interests;
    }
    
     /** Equals
      * @param jr a journalist
      * @return true if they are the same object.
      */
    public boolean equals(Journalist jr){
        return this.getId().equals(jr.getId());
    }
}
