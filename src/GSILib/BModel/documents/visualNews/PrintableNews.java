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
public class PrintableNews extends VisualNews{
    
    private List<Journalist> allowedJournalists = new ArrayList<Journalist>();

    public PrintableNews(String headline, String body, Journalist journalist) {
        super(headline, body, journalist);
    }
    
    public void allowJournalist(Journalist journalist){
        this.allowedJournalists.add(journalist);
    }
    public boolean disallowJournalist(Journalist journalist){
        return this.allowedJournalists.remove(journalist);
    }
}
