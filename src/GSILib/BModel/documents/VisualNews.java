/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.BModel.documents;

import GSILib.BModel.Document;
import GSILib.BModel.Picture;
import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class VisualNews.
 * This is an abstraction of a PrintableNews and Webnews that is a Document with
 * pictures.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class VisualNews extends Document{
    
    // Atributo de la clase
    private List<Picture> pictures = new ArrayList<>(); // Imágenes adosadas a las noticias.
    
    /**
     * Class constructor that makes an object with headline, body and author.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     */
    protected VisualNews(String headline, String body, Journalist journalist){
        // Llamamos al constructor de la superclase.
        super(headline, body, journalist);
    }
    
    /**
     * Adds a picture to the associated visual news
     * @param picture desired picture to add
     */
    public void addPicture(Picture picture){
        // Añadimos una foto a la lista de fotos.
        pictures.add(picture);
    }
    
    /**
     * Shows a list of pictures from the associated visual news
     * @return list of pictures from the visual news
     */
    public List<Picture> getPictures(){
        // Devolvemos la lista de fotos.
        return this.pictures;
    }  

    public String toString(){
        // Devolvemos un string con los datos del teletipo.
        return "VisualNews ID: " + this.getId() + "\n  Headline: " + this.getHeadline()
                + "\n  Body: " + this.getBody() + "\n  Journalist: " +
                this.getAuthor() + "Pictures: " + this.pictures;
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param vn a teletype
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(VisualNews vn){
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(vn.getId());
    }
}