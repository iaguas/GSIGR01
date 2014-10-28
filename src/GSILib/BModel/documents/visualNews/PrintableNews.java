/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
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
    
    // Atributo de la clase.
    private List<Journalist> reviewers = new ArrayList<>(); // Revisores de la noticia.
    
    /**
     * Class constructor that makes an object with headline, body and author.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     */
    public PrintableNews(String headline, String body, Journalist journalist) {
        // Llamamos al constructor de la superclase.
        super(headline, body, journalist);
    }
    
    /**
     * Adds a reviewer (journalist) to the associated printable news
     * @param journalist the desired reviewer to add
     * @return true if the reviewer isn't added to the printable news yet, false otherwise.
     */
    public boolean addReviewer(Journalist journalist){
        // Comprobamos que el periodista no esté de autor y lo añadimos como revisor.
        if (! this.journalists.contains(journalist)){
            return this.reviewers.add(journalist);
        }
        return false;
    }
    
    /**
     * Removes a reviewer (journalist) from the associated printable news
     * @param journalist the existing desired reviewer to remove from printable news
     * @return true if journalist removed correctly, false otherwise.
     */
    public boolean removeReviewer(Journalist journalist){
        // Eliminamos al revisor de la colección de revisores.
        return this.reviewers.remove(journalist);
    }
    
    /**
     * Gets a list of reviewer (journalists) from the associated printable news
     * @return the list of reviewers from the associated printable news
     */
    public Journalist[] getReviewers(){            
        // Devolvemos los revisores.
        if (this.reviewers.isEmpty())
            return null;
        return this.reviewers.toArray(new Journalist[this.reviewers.size()]);
    }
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos de la noticia imprimible.
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
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(pn.getId());
    }
    
}
