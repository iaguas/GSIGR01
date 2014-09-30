/**
 * This is the class PrintableNews.
 */
package GSILib.BModel.documents.visualNews;

import GSILib.BModel.documents.VisualNews;
import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia
 */
public class PrintableNews extends VisualNews{
    
    private List<Journalist> reviewers = new ArrayList<Journalist>();
    
    /**
     * Class constructor
     * @param headline
     * @param body
     * @param journalist
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
    
    // Overdrive methods equals and toString.
    @Override
    public String toString(){
        return "PrintableNews ID: " + this.getId() + "\n  Headline: " + 
                this.getHeadline() + "\n  Body: " + this.getBody() + 
                "\n  Journalist: " + this.getAuthor() + "\n  Pictures" + 
                this.getPictures() + "\n  Reviewers: " + this.reviewers;
    }
    
     /** Equals
      * @param pn a printablenews
      * @return true if they are the same object.
      */
    public boolean equals(PrintableNews pn){
        return this.getId().equals(pn.getId());
    }
    
}
