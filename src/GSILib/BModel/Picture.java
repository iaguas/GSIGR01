/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel;

import GSILib.BModel.workers.Photographer;

/**
 *
 * @author Alvaro
 */
public class Picture {
    
    private String url;
    private Photographer author;
    
    // Constructor
    
    public Picture(String url, Photographer photographer){
        this.url = url;
        this.author = photographer;
    }
    
    // Get privates
    
    public String getUrl(){
        return this.url;
    }
    public Photographer getAutor(){
        return this.author;
    }
}
