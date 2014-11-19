/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel.documents;

import GSILib.BModel.Document;
import GSILib.BModel.Picture;
import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This is the class VisualNews.
 * This is an abstraction of a PrintableNews and Webnews that is a Document with
 * pictures.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public abstract class VisualNews extends Document {
    
    // Atributo de la clase
    protected List<Picture> pictures = new ArrayList<>(); // Imágenes adosadas a las noticias.
    
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
     * TODO: JavaDoc
     */
    protected VisualNews(){
        
        // Creamos un Document nulo
        
        super();
    }
    
    /**
     * TODO: JavaDoc
     */ 
    protected void loadFromElement(Element xmlVisualNews){
        
        super.loadFromElement(xmlVisualNews);
       
        // VisualNews rellena sus datos
        
        NodeList picturesNodes = ((Element) xmlVisualNews.getElementsByTagName("Pictures").item(0)).getElementsByTagName("Picture");

        for (int i = 0; i < picturesNodes.getLength(); i++) {
       
            Node pictureNode = picturesNodes.item(i);
            
            this.pictures.add(new Picture((Element) pictureNode));
            
        } 
    }
    
    /**
     * TODO: JavaDoc
     */ 
    protected void loadFromElement(Element xmlVisualNews, Journalist journalist){
        
        super.loadFromElement(xmlVisualNews, journalist);
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
    public Picture[] getPictures(){
        // Devolvemos la lista de fotos.
        if (this.pictures.isEmpty())
            return null;
        return this.pictures.toArray(new Picture[this.pictures.size()]);
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
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos del teletipo.
    }
}