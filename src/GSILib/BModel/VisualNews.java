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
public class VisualNews extends Document{
    private List<Picture> pictures = new ArrayList<Picture>();
    
    // Constructor
    
    public VisualNews(String headline, String body){
        super(headline, body);
    }
    
    public addPicture(Picture picture){
        pictures.add(picture);
    }
}
