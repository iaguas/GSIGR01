/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel.documents;

import GSILib.BModel.Document;
import GSILib.BModel.Picture;
import GSILib.BModel.workers.Journalist;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This is the class VisualNews.
 * This is an abstraction of a PrintableNews and WebNews that is a Document with
 * pictures.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public abstract class VisualNews extends Document implements Serializable {
    
    // Atributo de la clase
    protected List<Picture> pictures = new ArrayList<>(); // Imágenes adosadas a las noticias.
    
    /**
     * Class constructor that makes an object with inherited 
     * headline, body and author from Document.
     * @param headline headline of the news that you want to save.
     * @param body all text of the news.
     * @param journalist worker who has written the news.
     */
    protected VisualNews(String headline, String body, Journalist journalist){
        // Llamamos al constructor de la superclase.
        super(headline, body, journalist);
    }
    
    /**
     * Empty class constructor.
     */
    protected VisualNews(){
        
        // Creamos un Document nulo
        
        super();
    }
    
    /**
     * Imports visual news from the Element gathered from an XML Document
     * containing VisualNews node list.
     * @param xmlVisualNews Element type containing "VisualNews" tagged nodes
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
     * Imports visual news from the Element gathered from an XML Document
     * containing VisualNews; the desired Journalist is associated
     * @param xmlVisualNews Element type containing "VisualNews" tagged nodes
     * @param journalist Journalist which is desired to associate with the 
     * VisualNews
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
     * Copies all values from a VisualNews to an associated new one.
     * @param newVisualNews original VisualNews the fields are read from
     */
    protected void copyValuesFrom(VisualNews newVisualNews){
        super.copyValuesFrom(newVisualNews);
        if (newVisualNews.getPictures() != null)
            this.pictures.addAll(Arrays.asList(newVisualNews.getPictures()));
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
        return "+ VisualNews ID: " + this.getId() + "\n"
                + "|- Headline: " + this.getHeadline()+ "\n"
                + "|- Body: " + this.getBody() + "\n"
                + "|- Journalist: " + this.getAuthor() + "\n"
                + "|- Pictures: " + this.pictures + "\n";
    }
    
    /**
     * VisualNews to JSON object parser (use of inherited method from Document)
     * @return json JSON object containing a VisualNews
     * @throws JSONException exception derived from JSON inputing
     */
    public JSONObject getJSONObject() throws JSONException{
        
        JSONObject json = super.getJSONObject();
        
        List<JSONObject> jsonPictures = new ArrayList();

        for(Picture picture : this.pictures){
            jsonPictures.add(picture.getJSONObject());
        }
        
       json.put("Pictures", new JSONArray(jsonPictures));
        
        return json;
    }
}
