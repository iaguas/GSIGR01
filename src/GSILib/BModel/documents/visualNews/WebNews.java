/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel.documents.visualNews;

import GSILib.BModel.documents.VisualNews;
import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia
 */
public class WebNews extends VisualNews{
    
    private String url;
    private List<String> keywords = new ArrayList<String>();
    
    // Constructor
    
    public WebNews(String headline, String body, Journalist journalist, String url) {
        super(headline, body, journalist);
        this.url = url;
    }
    
    /**
     * Adds a key word to the list of the associated WebNews
     * @param keyword key word to input
     * @return true if key word is smaller than 6 characters, and is added to the associated WebNews correctly
     */ 
    public boolean addKeyWord(String keyword){
        if (this.keywords.size() <= 6){
            this.keywords.add(keyword);
            return true;
        }
        return false;
    }
}
