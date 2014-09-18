/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel.documents.visualNews;

import GSILib.BModel.documents.VisualNews;
import GSILib.BModel.workers.Journalist;

/**
 *
 * @author Alvaro
 */
public class WebNews extends VisualNews{
    
    private String url;
    
    // Constructor
    
    public WebNews(String headline, String body, Journalist journalist) {
        super(headline, body, journalist);
    }
}
