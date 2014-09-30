/**
 * This is the class Picture.
 */
package GSILib.BModel;

import GSILib.BModel.workers.Photographer;

/**
 *
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia
 */
public class Picture {
    
    private String url;
    private Photographer author; // Deberíamos considerar el hacerlo por el ID del Fotografo.
    
    /**
     * Class constructor
     * @param url
     * @param photographer
     */
    public Picture(String url, Photographer photographer){
        this.url = url;
        this.author = photographer;
    }
    
    // Get privates
    /**
     * Retrieves the information on the URL of a Picture. If the Picture is not stored in the system,
     *		the result is null.
     *
     * @return The URL associated with the picture
     */
    public String getUrl(){
        return this.url;
    }
    
    /**
     * Retrieves the information on the author of a Picture. If the Picture is not stored in the system,
     *		the result is null.
     *
     * @return The photographer associated with the picture
     */    
    public Photographer getAutor(){
        return this.author;
    }
}
