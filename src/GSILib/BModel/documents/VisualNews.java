/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.BModel.documents;

import GSILib.BModel.Document;
import GSILib.BModel.Picture;
import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class VisualNews.
 * This is an abstraction of a PrintableNews and Webnews that is a Document with
 * pictures.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class VisualNews extends Document{
    private List<Picture> pictures = new ArrayList<>();
    
    /**
     * Class constructor that makes an object with headline, body and author.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
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
