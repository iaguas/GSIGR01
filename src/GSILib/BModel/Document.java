/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel;

import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Document {
    
    private String headline, body;
    private List<Journalist> journalists = new ArrayList<Journalist>();
    private List<String> awards = new ArrayList<String>();
    //** firma de periodista **//
    
    // Constructor
    
    protected Document(String headline, String body, Journalist journalist){
        this.headline = headline;
        this.body = body;
        this.journalists.add(journalist);
    }
    
    public void addJournalist(Journalist journalist){
        this.journalists.add(journalist);
    }
    public void addAward(String prize){
        awards.add(prize);
    }
    public Journalist getAuthor(){
        return this.journalists.get(0);
    }
}
