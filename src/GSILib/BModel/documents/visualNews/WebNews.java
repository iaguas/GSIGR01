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
 * This is the class WebNews.
 * This is a type of notice VisualNews which is identify by an URL and have a 
 * headline, body and author.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class WebNews extends VisualNews{
    // Atributos de la clase.
    private String url; // URL identificable de la clase.
    private List<String> keywords = new ArrayList<>(); // Palabras clave identificables.
    
    /**
     * Class constructor that makes an object with headline, body, author and URL.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     * @param url URL that's a unique identifier of the notice.
     */
    public WebNews(String headline, String body, Journalist journalist, String url) {
        // Llamamos al constructor de la superclase.
        super(headline, body, journalist);
        // Añadimos l
        this.url = url;
    }
    
    /**
     * Adds a key word to the list of the associated WebNews
     * @param keyword key word to input
     * @return true if key word is smaller than 6 characters, and is added to 
     * the associated WebNews correctly, false otherwise.
     */ 
    public boolean addKeyWord(String keyword){
        // Comprobamos que la palabra tenga al menos 6 caracteres para añadirla.
        if (this.keywords.size() <= 6){
            this.keywords.add(keyword);
            // Advertimos que el añadido ha sido correcto.
            return true;
        }
        // Advertimos que el añadido no ha sido correcto.
        return false;
    }
    
    /**
     * Takes the URL of a webnews.
     * @return the url that identifies a webnews.
     */ 
    public List<String> getKeyWords(){
        // Devolvemos las palabras clave
        return this.keywords;        
    }
    
     /**
     * Takes the URL of a webnews.
     * @return the url that identifies a webnews.
     */ 
    public String getUrl(){
        // Devolvemos la URL
        return this.url;
    }
     
    @Override
    public String toString(){
        // Devolvemos un string con los datos de la noticia web.
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
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(wn.getId());
    }
}
