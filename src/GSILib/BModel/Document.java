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
    
    private Integer id;
    private String headline, body;
    protected List<Journalist> journalists = new ArrayList<Journalist>();
    private List<String> prizes = new ArrayList<String>();
    //** TODO firma de periodista **//
    
    /**
     * Class constructor
     * @param headline
     * @param body
     * @param journalist
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
     * @param prize the prize the document was awarded with
     * @return true if the Document was already stored and it was correctly listed, false otherwise
     */
    public boolean addPrize(String prize){
        return this.prizes.add(prize);
    }
    
    /**
    * Deletes a prize that has been associated with a given Document
     * @param prize The prize to be deleted
     * @return True if it was correctly deleted, false if it wasn't or did not exist.
     */    
    public boolean removePrize(String prize){
        return this.prizes.remove(prize);
    }
    
    /**
     * Retrieves the author of a given news
     * @return The journalist
     */
    public Journalist getAuthor(){
        return this.journalists.get(0);
    }
    
    /** 
     * Add an unique id to a document
     * @param id The id itself
     */
    public void setId(Integer id){
        this.id = id;
    }
    
    /**
     * Retrieves the ID of a given document.
     * @return The ID of a document.
     */
    public Integer getId(){
        return this.id;
    }
    
    /**
     * Retrieves the headline of a given document.
     * @return The headline of a document.
     */
    public String getHeadline(){
        return this.headline;
    }
    
    /**
     * Retrieves the body of a given document.
     * @return The body of a document.
     */
    public String getBody(){
        return this.body;
    }
}
