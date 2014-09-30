/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.BModel;

import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class Document. 
 * It represent the top of the herence tree of the documents in the system.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Document {
    
    private Integer id;
    private String headline, body;
    protected List<Journalist> journalists = new ArrayList<Journalist>();
    private List<String> prizes = new ArrayList<String>();
    
    /**
     * Class constructor for a document with headline, body and author.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     */
    protected Document(String headline, String body, Journalist journalist){
        this.headline = headline;
        this.body = body;
        this.journalists.add(journalist);
    }
    
    /**
     * Adds a journalist to the office.
     * @param jr The journalist to be added.
     * @return true if it was correctly added, false if it was null or already existing.
     */    
    public boolean addJournalist(Journalist jr){
        return this.journalists.add(jr);
    }
    
    /**
     * Adds a string-represented prize to a given document, which can be a Teleprinter, WebNew or PrintableNew.
     * @param prize the prize the document was awarded with.
     * @return true if the Document was already stored and it was correctly listed, false otherwise.
     */
    public boolean addPrize(String prize){
        return this.prizes.add(prize);
    }
    
    /**
    * Deletes a prize that has been associated with a given Document
     * @param prize The prize to be deleted.
     * @return True if it was correctly deleted, false if it wasn't or did not exist.
     */    
    public boolean removePrize(String prize){
        return this.prizes.remove(prize);
    }
    
    /**
     * Retrieves the author of a given news.
     * @return The journalist author of the document.
     */
    public Journalist getAuthor(){
        return this.journalists.get(0);
    }
    
    /** 
     * Add an unique ID to a document
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
