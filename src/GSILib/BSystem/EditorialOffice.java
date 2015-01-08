/*
 * This document is for its sole use in academic environments.
 * For any further use contact the author at carlos.lopez@unavarra.es
 */

package GSILib.BSystem;

import GSILib.BModel.*;
import GSILib.BModel.workers.*;
import GSILib.BModel.documents.*;
import GSILib.BModel.documents.visualNews.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * This interface retrieves the basic behaviour of an editorial office.
 * It does support basic operations of introducing, retrieving and listing the most
 *		basic information in the System
 * @author carlos.lopez
 */
public interface EditorialOffice{
    
    
    /*
     * Management of workers
     */
    
    /**
     * Adds a journalist to the office
     * @param jr The journalist to be added
     * @return true if it was correctly added, false if it was null or already existing
     */
    public boolean addJournalist(Journalist jr);
    
    /**
     * Checks whether one journalist is already listed in the office
     * @param jr The journalist
     * @return true if it exist, false if it was null or non-existing
     */
    public boolean existJournalist(Journalist jr);
    
    /**
     * Deletes the journalist from the editorial office.
     * @param jr The journalist to be removed
     * @return true if it was found and removed, false otherwise
     */
    public boolean removeJournalist(Journalist jr);
    
    /**
     * Returns a journalist after its ID
     * @param ID The ID of the journalist
     * @return The journalist, or null if it was non-existing
     */
    public Journalist findJournalist(String ID);
    
    /**
     * Returns the list of Journalists stored in the system
     * @return a list of Journalists
     */
    public Journalist[] getJournalists();
    
    /**
     * Adds a photographer to the office
     * @param pr the photographer
     * @return true if it was added, false if it was null or already existing
     */
    public boolean addPhotographer(Photographer pr);
    
    /**
     * Checks whether a photographer exists in the office
     * @param pr the photographer
     * @return true if it does, false if it does not or if pr was null
     */
    public boolean existPhotographer(Photographer pr);
    
    /**
     * Removes a photographer from the office
     * @param jr false if it was non-existing, true if it was found and removed
     * @return true if it was found and removed, false otherwise
     */
    public boolean removePhotographer(Photographer jr);
    
    /**
     * Returns a photographer after its ID
     * @param ID The ID of the photographer
     * @return The photographer, or null if it was non-existing
     */
    public Photographer findPhotographer(String ID);
    
    
    /*
     * Management of contents
     */    
    
    /**
     * Insert a web-new in the office library
     * @param wn the new to be inserted
     * @return true if it was correctly stored, false otherwise
     */
    public boolean insertNews(WebNews wn);
    
    /**
     * Insert a printable-new in the office library
     * @param pn the new to be inserted
     * @return true if it was correctly stored, false otherwise
     */
    public boolean insertNews(PrintableNews pn);
    
    /**
     * Inserts a teleprinter in the office library
     * @param tp the new to be inserted
     * @return true if it was correctly stored, false otherwise
     */
    public boolean insertNews(Teletype tp);
    
    /**
     * Removes a web-new from the office library
     * @param wn the web new to be removed
     * @return true if it was found and removed, false otherwise
     */
    public boolean removeNews(WebNews wn);
    
    /**
     * Removes a printable-new from the office library
     * @param pn the web new to be removed
     * @return true if it was found and removed, false otherwise
     */
    public boolean removeNews(PrintableNews pn);
    
    /**
     * Removes a teleprinter from the office library
     * @param tp the web new to be removed
     * @return true if it was found and removed, false otherwise
     */
    public boolean removeNews(Teletype tp);
    
    /**
     * Adds a string-represented prize to a given document, which can be a Teleprinter, WebNew or PrintableNew.
     * @param d The new the prize is associated with.
     * @param prize the prize the document was awarded with
     * @return true if the Document was already stored and it was correctly listed, false otherwise
     */
    public boolean addPrize(Document d, String prize);
    
    /**
    * Deletes a prize that has been associated with a given Document
     * @param d The document itself
     * @param prize The prize to be deleted
     * @return True if it was correctly deleted, false if it wasn't or did not exist.
     */
    public boolean removePrize(Document d, String prize);
    
    /**
     * Retrieves the author of a given news
     * @param d The document itself
     * @return The journalist
     */
    public Journalist getAuthor(Document d);
    

    
    /**
     * Retrieves all the documents in the system written by a journalist
     * @param j Journalist to be inquiried
     * @return An array of documents
     */
    public Document[] getDocuments(Journalist j);
    
    /**
     * Retrives all the PrintableNews in the system written by a journalist (variant of getDocuments(j) method)
     * @param j Journalist to be inquired
     * @return An array of printable news
     */
    public PrintableNews[] getPrintableNewsFromAuthor(Journalist j);
    
    /**
     * Adds a journalist as reviewer of an existing news. If the news or the journalist did not exist 
	 *		in the system the operation must have no effect and return false.
     * @param pn	The printable news to which we are adding a reviewer.
     * @param rw 	The reviewer
	 * @return true if and only if (a) the news and the journalist existed, (b) the latter wasn't listed as a reviewer of the former,
	 *				and (c) it was correctly added as such.
     */
    public boolean addReviewer(PrintableNews pn,Journalist rw);
    
    /**
     * Removes a reviewer from a given PrintableNews. If the PrintableNews did not exist in the system,
	 *		the operation must have no effect and return false.
     * @param pn    printable news
     * @param rw 	reviewer to be removed
     * @return true if and only if (a) the news and the journalist existed, (b) the latter was listed as a reviewer of the former,
	 *				and (c) it was correctly removed.
     */
    public boolean removeReviewer(PrintableNews pn,Journalist rw);
    
    /**
     * Retrieves the list of Journalist that have revised a document
     * @param pn The printable piece of news
     * @return The list of journalists, or null if pn wasn't stored in the system
     */
    public Journalist[] listReviewers(PrintableNews pn);
    
    /**
     * Adds a picture to the system. Note that a Picture cannot be introduced twice.
     * @param p The printable piece of news
     * @return True if it could be added by the system, false otherwise. 
     */
    public boolean addPicture(Picture p);
    
    /**
     * Remove a picture from the system
     * @param p The picture to be removed
     * @return true if it existed and was correctly removed from the system
     */
    public boolean removePicture(Picture p);
    
    /**
     * Retrieves the pictures associated with a given photographer
     * @param p The photographer
     * @return A list of the pictures
     */
    public Picture[] getPictures(Photographer p);
    
    /**
     * Retrieves the information on the author of a Picture. If the Picture is not stored in the system,
	 *		the result is null.
     * @param p The picture
     * @return The photographer associated with the picture
     */
    public Photographer getAuthor(Picture p);
    
    /**
     * Retrieves a webResource (WebNews or Picture) from its URL. // UML?
     * @param URL	The URL
     * @return 	The elemento, or null if no element matched the search.
     */
    public WebResource getWebResource(String URL);
    
    /**
     * Retrieves a PrintableNews form his ID
     * @param printableNewsID The printableNewsID
     * @return 	The element, or null if no element matched the search.
     */
    public PrintableNews getPrintableNews(int printableNewsID);
    
    /**
     * Returns the list of Webnews stored in the system
     * @return a list of Webnews
     */
    public WebNews[] getWebNews();
    
    /**
     * Gets the WebNews associated to a given URL
     * @param URL resource with a WebNews
     * @return webnews requested.
     */
    public WebNews getWebNews(String URL);
	
    /**
     * Returns all the WebNews in the system indexed by (an exact) keyword,
     *  up to upper/lower case matters.
     * @param keyword   The search term, which must be non-empty
     * @return An array with the news.
     */
    WebNews[] getIndexedNews(String keyword);
	
	
    /*
     * Management of Newspapers
     */
    
    /**
     * Create a newspaper for the specified date
     * @param d The ¿¿data?? of the newspaper. The system only allows one single newspaper per Date
     * @return true if and only if the paper was correctly created.
     */
    public boolean createNewspaper(Date d);
    
    /**
     * Creates a Newspaper for the specified date given in String format
     * @param date String to be converted into Date
     * @return true if and only if the newspaper was correctly created
     */
    public boolean createNewspaper(String date);
    
    /**
     * Adds a Newspaper to the system
     * @param newspaper to be added to the System
     * @return instance of a Newspaper
     */
    public Newspaper insertNewspaper(Newspaper newspaper);
    
    /**
     * Retrieves the newspaper associated with a given date
     * @param d The date
     * @return The instance of JournalIssue for that date, or null if it does not exist.
     */
    public Newspaper getNewspaper(Date d);
    
    /**
     * Retrieves the newspaper associated with a given date
     * @param d The string of a date
     * @return The instance of JournalIssue for that date, or null if it does not exist.
     */
    public Newspaper getNewspaper(String d);
    
    /**
     * Returns a list of Newspapers of a given date, which is the partial key
     * @param date The date that filters the Newspapers
     * @return a list of Newspapers which have the given date
     */
    public Newspaper[] getNewspapersByPartialKey(String date);
    
    /**
     * Returns all the Newspapers.
     * @return An array with the Newspapers of BusinessSystem.
     */
    public Newspaper[] getNewspapers();
    
    /**
     * Deletes the newspaper from a specified date.
     * @param d The date for which the newspaper must be deleted
     * @return true if newspaper is deleted correctly
     */
    public boolean deleteNewspaper(Date d);
    
    /**
     * Adds a given news to a specific journal issue. In case the PrintableNews did not exist in the system,
     * 		it must also be stored. However, if the JournalIssue did not exist in the system, 
     *		the operation must have no effect and return a false result.
     * @param np	Issue at which the pn must be added
     * @param pn 	Printable news to be added to the journal issue
     * @return true if and only if the printable news could be added to the journal issue.
     */
    public boolean addNewsToIssue(Newspaper np,PrintableNews pn);
    
    /**
     * Retrieves the list of journalist that have authored some document in a given JournalIssue
     * @param np	The journal issue for which we seek the Journalist list
     * @return 	An array containing the journalists.
     */
    Journalist[] getJournalist(Newspaper np);
    
    /**
     * Retrieves the array of prizes that a Document earned
     * @param d	The Document
     * @return 	An array containing the prizes.
     */
    public String[] listPrizes(Document d);
    
    /**
     * Retrieves the picture with that url
     * @param url The picture's url
     * @return The picture object.
     */
    public Picture getPicture(String url);
    
    /**
     * Creates an HTML fragment with a listing of selectable Journalists
     * @return HTML fragment to be shown in a web page for the listing of Journalists
     */
    public String getJournalistOptions();
    
    /**
     * Creates an HTML fragment with a listing of selectable Newspapers
     * @return HTML fragment to be shown in a web page for the listing of Newspapers
     */
    public String getNewspaperOptions();
}
