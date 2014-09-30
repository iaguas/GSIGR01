/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
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
    
    // Constructor

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
    
    public boolean equals(Journalist jr){
        return this.getId().equals(jr.getId());
    }
}
