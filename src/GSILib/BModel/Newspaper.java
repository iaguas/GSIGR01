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
 * @author Alvaro
 */
public class Newspaper {
    
    private List<PrintableNews> news = new ArrayList<PrintableNews>();
    
    // Constructor
    
    public Newspaper(){
        
        // nulo
    }

    public void addNews(PrintableNews printableNews){
        this.news.add(printableNews);
    }
    
    public boolean isPublishable(){
        return this.news.size() >= 20;
    }
}
