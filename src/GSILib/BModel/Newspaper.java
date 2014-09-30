/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.BModel;

import GSILib.BModel.documents.visualNews.PrintableNews;
/*import GSILib.BModel.documents.visualNews.WebNews;*/
import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is the class Newspaper.
 * It represent a set of PrintableNews which form a newspaper. There is once by day.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Newspaper {
    
    private Date date = new Date();
    private List<PrintableNews> news = new ArrayList<PrintableNews>();
    
    /**
     * Class constructor
     * @param date It represents the date of the newspaper. It's unique for each one.
     */
    public Newspaper(Date date){
        this.date = date;
    }
    
    /**
     * Adds a printable news to the associated newspaper
     * @param pn The printable news which are part of the newspaper.
     * @return true if printable news added correctly to newspaper, false otherwise.
     */
    public boolean addNews(PrintableNews pn){
        return this.news.add(pn);
    }
    
    /**
     * Verifies whether a newspaper is publishable
     * @return true if the number of pages is at least 20, false otherwise.
     */
    public boolean isPublishable(){
        return this.news.size() >= 20;
    }
    
    /**
     * Returns all the authors (journalists) of the news in the newspaper.
     * @return An array with the journalists who have written news in the newspaper.
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
