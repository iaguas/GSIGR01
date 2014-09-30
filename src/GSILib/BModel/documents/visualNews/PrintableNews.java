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
 * This is the class PrintableNews.
 * Is a type of notice VisualNews which have pictures, reviewers adding to a 
 * headline, title and author.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class PrintableNews extends VisualNews{
    
    private List<Journalist> reviewers = new ArrayList<Journalist>();
    
    /**
     * Class constructor that makes an object with headline, body and author.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     */
    public PrintableNews(String headline, String body, Journalist journalist) {
        super(headline, body, journalist);
    }
    
    /**
     * Adds a reviewer (journalist) to the associated printable news
     * @param journalist the desired reviewer to add
     * @return true if the reviewer isn't added to the printable news yet
     */
    public boolean addReviewer(Journalist journalist){
        if (! this.journalists.contains(journalist)){
            return this.reviewers.add(journalist);
        }
        return false;
    }
    
    /**
     * Removes a reviewer (journalist) from the associated printable news
     * @param journalist the existing desired reviewer to remove from printable news
     * @return true if journalist removed correctly
     */
    public boolean removeReviewer(Journalist journalist){
        return this.reviewers.remove(journalist);
    }
    
    /**
     * Gets a list of reviewer (journalists) from the associated printable news
     * @return the list of reviewers from the associated printable news
     */
    public Journalist[] getReviewers(){
        Journalist[] reviewers = null;
        int nextIndex = 0;
        
        for (int i = 0; i < this.reviewers.size(); i++){
            reviewers[nextIndex] = this.reviewers.get(i);
            nextIndex++;   
        }
        return reviewers;
    }
    
    @Override
    public String toString(){
        return "PrintableNews ID: " + this.getId() + "\n  Headline: " + 
                this.getHeadline() + "\n  Body: " + this.getBody() + 
                "\n  Journalist: " + this.getAuthor() + "\n  Pictures" + 
                this.getPictures() + "\n  Reviewers: " + this.reviewers;
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param pn a printablenews
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(PrintableNews pn){
        return this.getId().equals(pn.getId());
    }
    
}
