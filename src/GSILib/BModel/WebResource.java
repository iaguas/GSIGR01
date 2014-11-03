/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel;

import GSILib.BModel.workers.Photographer;

/**
 * This is the class WebResource.
 * It's an interface in order to use all the web resources, this is, pictures 
 * and webnews.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */

public interface WebResource{
    
    /**
     * Adds a key word to the list of the associated WebNews
     * @param keyword keyword to input into the web notice.
     * @return true if key word is smaller than 6 characters, and is added to 
     * the associated WebNews correctly, false otherwise.
     */ 
    public boolean addKeyWord(String keyword);
    
    /**
     * Retrieves the information on the URL of a Picture. 
     * If the Picture is not stored in the system, the result is null.
     * @return The URL associated with the picture
     */
    public String getUrl();
    
    /**
     * Retrieves the information on the author of a Picture. 
     * If the Picture is not stored in the system, the result is null.
     * @return The photographer associated with the picture
     */ 
    public Photographer getAutor();    
}
