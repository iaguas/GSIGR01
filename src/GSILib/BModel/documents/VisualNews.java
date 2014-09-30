/**
 * This is the class VisualNews.
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
    
    /**
     * Class constructor
     * @param headline
     * @param body
     * @param journalist
     */
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
    
    /**
     * Shows a list of pictures from the associated visual news
     * @return list of pictures from the visual news
     */
    public List<Picture> getPictures(){
        return this.pictures;
    }
}
