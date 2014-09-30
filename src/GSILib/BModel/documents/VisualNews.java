/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel.documents;

import GSILib.BModel.Document;
import GSILib.BModel.Picture;
import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia
 */
public class VisualNews extends Document{
    private List<Picture> pictures = new ArrayList<Picture>();
    
    // Constructor
    
    protected VisualNews(String headline, String body, Journalist journalist){
        super(headline, body, journalist);
    }
    
    /**
     * Adds a picture to the associated visual news
     * @param picture desired picture to add
     */
    public void addPicture(Picture picture){
        pictures.add(picture);
    }
    
    public List<Picture> getPictures(){
        return this.pictures;
    }
}
