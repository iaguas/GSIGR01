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
    protected List<Journalist> journalists = new ArrayList<Journalist>();
    private List<String> prizes = new ArrayList<String>();
    //** firma de periodista **//
    
    // Constructor
    
    protected Document(String headline, String body, Journalist journalist){
        this.headline = headline;
        this.body = body;
        this.journalists.add(journalist);
    }
    
    public boolean addJournalist(Journalist journalist){
        return this.journalists.add(journalist);
    }
    public boolean addPrize(String prize){
        return this.prizes.add(prize);
    }
    public boolean removePrize(String prize){
        return this.prizes.remove(prize);
    }
    
    // Get privates
    
    public Journalist getAuthor(){
        return this.journalists.get(0);
    }
}
