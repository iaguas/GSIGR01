/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel;

import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.documents.visualNews.WebNews;
import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia
 */
public class Newspaper {
    
    private List<PrintableNews> news = new ArrayList<PrintableNews>();
    
    // Constructor
    
    public Newspaper(){
        
        // nulo
    }
    
    /**
     * Adds a printable news to the associated newspaper
     * @param pn The printable news
     * @return if printable news added correctly to newspaper
     */
    public boolean addNews(PrintableNews pn){
        return this.news.add(pn);
    }
    
    /**
     * Verifies whether a newspaper is publishable
     * @return true if the number of pages is at least 20
     */
    public boolean isPublishable(){
        return this.news.size() >= 20;
    }
    
    /**
     * Returns all the authors (journalists) of the news in the newspaper
     * @return An array with the journalists who have written news in the newspaper
     */
    public Journalist[] getAuthors(){
        Journalist[] authorsOfANewspaper = null;
        int nextIndex = 0;
        
        for (int i = 0; i < this.news.size(); i++){
            authorsOfANewspaper[nextIndex] = this.news.get(i).getAuthor();
            nextIndex++;
            
        }
        return authorsOfANewspaper;
    }
}
