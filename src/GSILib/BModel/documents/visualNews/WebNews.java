/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.BModel.documents.visualNews;

import GSILib.BModel.documents.VisualNews;
import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class WebNews.
 * This is a type of notice VisualNews which is identify by an URL and have a 
 * headline, body and author.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class WebNews extends VisualNews{
    
    private String url;
    private List<String> keywords = new ArrayList<String>();
    
    /**
     * Class constructor that makes an object with headline, body, author and URL.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     * @param url URL that's a unique identifier of the notice.
     */
    public WebNews(String headline, String body, Journalist journalist, String url) {
        super(headline, body, journalist);
        this.url = url;
    }
    
    /**
     * Adds a key word to the list of the associated WebNews
     * @param keyword key word to input
     * @return true if key word is smaller than 6 characters, and is added to the associated WebNews correctly
     */ 
    public boolean addKeyWord(String keyword){
        if (this.keywords.size() <= 6){
            this.keywords.add(keyword);
            return true;
        }
        return false;
    }
     
    @Override
    public String toString(){
        return "WebNews ID: " + this.getId() + "\n  Headline: " + 
                this.getHeadline() + "\n  Body: " + this.getBody() + 
                "\n  Journalist: " + this.getAuthor() + "\n  Pictures" + 
                this.getPictures() + "\n  URL: " + this.url + "\n  Keywords" +
                this.keywords;
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param wn a webnews
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(WebNews wn){
        return this.getId().equals(wn.getId());
    }
}
