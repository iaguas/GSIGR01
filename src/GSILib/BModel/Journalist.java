/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Journalist extends Worker{
    
    private List<String> interests = new ArrayList<String>();
    
    // Constructor

    public Journalist(String name, String birthDate, ArrayList interests){
        
        super(name, birthDate);
        
        this.interests = interests;
    }
}
