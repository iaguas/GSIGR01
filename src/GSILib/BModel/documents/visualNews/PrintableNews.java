/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
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

    public PrintableNews(String headline, String body, Journalist journalist) {
        super(headline, body, journalist);
    }
    
    public boolean addReviewer(Journalist journalist){
        if (! this.journalists.contains(journalist)){
            return this.reviewers.add(journalist);
        }
        return false;
    }
    public boolean removeReviewer(Journalist journalist){
        return this.reviewers.remove(journalist);
    }
    public Journalist[] getReviewers(){
        Journalist[] reviewers = null;
        int nextIndex = 0;
        
        for (int i = 0; i < this.reviewers.size(); i++){
            reviewers[nextIndex] = this.reviewers.get(i);
            nextIndex++;   
        }
        return reviewers;
    }
}
