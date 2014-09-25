/**
 * This is the class Document.
 */
package GSILib.BModel;

import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia
 */
public class Document {
    
    private String headline, body;
    protected List<Journalist> journalists = new ArrayList<Journalist>();
    private List<String> prizes = new ArrayList<String>();
    //** firma de periodista **//
    
    /**
     * Class constructor
     */
    
    protected Document(String headline, String body, Journalist journalist){
        this.headline = headline;
        this.body = body;
        this.journalists.add(journalist);
    }
    
    /**
     * Adds a journalist to the office
     * @param jr The journalist to be added
     * @return true if it was correctly added, false if it was null or already existing
     */    
    public boolean addJournalist(Journalist jr){
        return this.journalists.add(jr);
    }
    
    /**
     * Adds a string-represented prize to a given document, which can be a Teleprinter, WebNew or PrintableNew.
     * @param d The new the prize is associated with.
     * @param prize the prize the document was awarded with
     * @return true if the Document was already stored and it was correctly listed, false otherwise
     */
    public boolean addPrize(String prize){
        return this.prizes.add(prize);
    }
    
    /**
    * Deletes a prize that has been associated with a given Document
     * @param d The document itself
     * @param prize The prize to be deleted
     * @return True if it was correctly deleted, false if it wasn't or did not exist.
     */    
    public boolean removePrize(String prize){
        return this.prizes.remove(prize);
    }
    
    /**
     * Retrieves the author of a given news
     * @param d The document itself
     * @return The journalist
     */
    public Journalist getAuthor(){
        return this.journalists.get(0);
    }
}
