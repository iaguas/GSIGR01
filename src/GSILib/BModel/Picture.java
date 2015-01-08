/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel;

import GSILib.BModel.workers.Photographer;
import GSILib.Serializable.XMLHandler;
import GSILib.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * This is the class Picture.
 * It respresent a picture in the system throw it URL.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Picture implements XMLRepresentable{
    
    // Atributos de la clase
    private String url; // Identificador único, la URL.
    private Photographer author; // Fotografo autor de la foto.
    
    /**
     * Class constructor in which you have to put the URL and the photographer.
     * @param url An unique identifier of a picture.
     * @param photographer Author of the picture.
     */
    public Picture(String url, Photographer photographer){
        // Introducimos los datos suministrados por el constructor.
        this.url = url;
        this.author = photographer;
    }
    
   /**
     * Constructor which obtains Element type object from and XML Document containing "Picture"
     * tagged nodes, which are Picture type objects.
     * @param pictureFromXML XML document to parse, which contains nodes 
     * corresponding to the class Picture
     * @throws SAXException exception derived from XML file reading
     */
    public Picture(String pictureFromXML) throws SAXException{
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler(pictureFromXML);
        
        Element xmlPicture = (Element) xml.engine.getElementsByTagName("Picture").item(0);
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlPicture);
    }
    
    /**
     * Imports list of pictures from the Element gathered from an XML Document
     * containing Picture node list.
     * @param xmlPicture Element type containing "Picture" tagged nodes
     */
    public Picture(Element xmlPicture){
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlPicture);
         
    }
    
    /**
     * Constructor which obtains Element type object from and XML Document containing "Picture"
     * tagged nodes, which are Picture type objects.
     * @param xmlPicture Element type object, which contains useful data (url) 
     * @param photographer Photographer type object which is going to be asociated
     * to the Picture (to its "author" field)
     */
    public Picture(Element xmlPicture, Photographer photographer){
        
        // Picture rellena sus datos
        
        this.url = xmlPicture.getAttribute("url");
        
        this.author = photographer;
         
    }
    
    /**
     * Lowest rank method which obtains the atribute values for Picture from an
     * XML Element. 
     * @param xmlPicture Element type which contains useful data (url and author
     * (Photographer))
     */
    protected void loadFromElement(Element xmlPicture){
        
        // Picture rellena sus datos
        
        this.url = xmlPicture.getAttribute("url");
        
        this.author = new Photographer((Element) xmlPicture.getElementsByTagName("Photographer").item(0));
    }
    
    /**
     * Retrieves the information on the URL of a Picture. 
     * If the Picture is not stored in the system, the result is null.
     * @return The URL associated with the picture
     */
    public String getUrl(){
        // Devolvemos la URL
        return this.url;
    }
    
    /**
     * Retrieves the information on the author of a Picture. 
     * If the Picture is not stored in the system, the result is null.
     * @return The photographer associated with the picture
     */    
    public Photographer getAuthor(){
        // Devolvemos el autor.
        return this.author;
    }
    
    /**
     * Helper method which creates a XML element "Photographer"
     * @param xml XML handler for the system.
     * @return XML element snippet representing a photographer
     */
    public Element getElement(XMLHandler xml){

        Element xmlPicture = xml.engine.createElement("Picture");
        
        // Para una raiz Picture, introducimos su url como atributo
        
        xmlPicture.setAttribute("url", this.getUrl());
        
        // Para una raiz Picture, introducimos otra raiz Photographer

        if (xml.storeMode.equals("relational")){
            
            // Para una raiz Photographer, introducimos su id como atributo
            
            Element xmlPicturePhotographer = xml.engine.createElement("Photographer");
            xmlPicturePhotographer.setAttribute("id", this.getAuthor().getId());
            xmlPicture.appendChild(xmlPicturePhotographer);
            
        }
        else if(xml.storeMode.equals("full")){
            
            // Para una raiz Picture, introducimos otra raiz Photographer
            
            xmlPicture.appendChild(this.getAuthor().getElement(xml));
        }
        else{
            System.err.print("unrecognized method");
        }
        
        return xmlPicture;
    }
    
    /**
     * Gets this journalist in XML string.
     * @return the xml string of this journalist.
     */
    @Override
    public String toXML() {
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler();
        
        Writer out = new StringWriter();
        try{
            OutputFormat format = new OutputFormat(xml.engine);
            format.setIndenting(true);
            
            XMLSerializer serializerToString = new XMLSerializer(out , format);
            serializerToString.serialize(this.getElement(xml));

        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return out.toString();
    }
    
    /**
     * Stores this journalist in XML.
     * @return if the journalist was successfully stored into the xml file.
     */
    @Override
    public boolean saveToXML(File file) {
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler();
        
        try{
            
            OutputFormat format = new OutputFormat(xml.engine);
            format.setIndenting(true);
            
            XMLSerializer serializerTofile = new XMLSerializer(
                new FileOutputStream(file)
                , format);
            serializerTofile.serialize(this.getElement(xml));
            
            return true;
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return false;
    }

    /**
     * Stores this journalist in XML.
     * @return if the journalist was successfully stored into the xml file.
     */
    @Override
    public boolean saveToXML(String filePath) {
       
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler();
        
        try{
            
            OutputFormat format = new OutputFormat(xml.engine);
            format.setIndenting(true);
            XMLSerializer serializerTofile = new XMLSerializer(
                new FileOutputStream(
                    new File(filePath))
                , format);
            serializerTofile.serialize(this.getElement(xml));
            
            return true;
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return false;
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param p a picture
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Picture p){
        // Comparamos y devolvemos si son iguales o no.
        return this.getUrl().equals(p.getUrl());
    }

    @Override
    public String toString(){
        // Devolvemos un string con los datos de la imagen.
        return "+ Picture URL: " + this.getUrl() + "\n"
                + "|- Photographer: " + this.getAuthor() + "\n";
    }
    
    /**
     * Picture to JSON object parser
     * @return json JSON object containing a Picture
     * @throws JSONException exception derived from JSON inputing
     */
    public JSONObject getJSONObject() throws JSONException{
        
        JSONObject json = new JSONObject();

        json.put("url", this.url);
        json.put("Photographer", this.author.getJSONObject());
        
        return json;
    }
}
