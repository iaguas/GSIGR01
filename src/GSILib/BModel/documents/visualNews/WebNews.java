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
 * @author Alvaro
 */
public class WebNews extends VisualNews{
    
    private String url;
    private List<String> keywords = new ArrayList<String>();
    
    // Constructor
    
    public WebNews(String headline, String body, Journalist journalist) {
        super(headline, body, journalist);
    }
    
    public boolean addKeyWord(String keyword){
        if (this.keywords.size() <= 6){
            this.keywords.add(keyword);
            return true;
        }
        return false;
    }
}
