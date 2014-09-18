/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel.documents;

import GSILib.BModel.Document;
import GSILib.BModel.workers.Journalist;

/**
 *
 * @author Alvaro
 */
public class Teletype extends Document{
    
    // Constructor
    
    public Teletype(String headline, String body, Journalist journalist){
        super(headline, body, journalist);
    }
}
