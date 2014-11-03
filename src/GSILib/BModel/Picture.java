/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel;

import GSILib.BModel.workers.Photographer;
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
import org.w3c.dom.Element;
import org.w3c.dom.Text;

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
    // XML Engine
    private org.w3c.dom.Document xml;
    // XML Store Mode
    static final String XMLStoreMode = "full"; // {"full","relational"}
    
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
    
    // TODO : JavaDoc 
    // Esta funcion simplemente calcula el arbol XML
    private void createXMLTree(){
        
        //get an instance of factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //get an instance of builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //create an instance of DOM
            this.xml = db.newDocument();
        }catch(ParserConfigurationException pce) {
            //dump it
            System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
            System.exit(1);
        }
        
        // Añadimos a la raiz un solo elemento

        this.xml.appendChild(this.getElement(this.xml));
    }
    
    /**
     * Helper method which creates a XML element <Photographer>
     * @return XML element snippet representing a photographer
     */
    public Element getElement(org.w3c.dom.Document xml){

        Element xmlPicture = xml.createElement("Picture");
        
        // Para una raiz Picture, introducimos su url como atributo
        
        xmlPicture.setAttribute("url", this.getUrl());
        
        // Para una raiz Picture, introducimos otra raiz Photographer

        if (this.XMLStoreMode.equals("relational")){
            
            // Para una raiz Photographer, introducimos su id como atributo
            
            Element xmlPicturePhotographer = xml.createElement("Photographer");
            xmlPicturePhotographer.setAttribute("id", this.getAuthor().getId());
            xmlPicture.appendChild(xmlPicturePhotographer);
            
        }
        else if(this.XMLStoreMode.equals("full")){
            
            // Para una raiz Picture, introducimos otra raiz Photographer
            
            xmlPicture.appendChild(this.getAuthor().getElement(xml));
        }
        else{
            System.err.print("unrecognized method");
        }
        
        return xmlPicture;
    }
    
    /**
     * Gets this photographer in XML string.
     * @return the xml string of this photographer.
     */
    @Override
    public String toXML() {
        
        // Almacenar en una variable
        
        this.createXMLTree();
        
        Writer out = new StringWriter();
        try{
            OutputFormat format = new OutputFormat(this.xml);
            format.setIndenting(true);
            
            XMLSerializer serializerToString = new XMLSerializer(out , format);
            serializerToString.serialize(this.xml);

        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return out.toString();
    }
    
    /**
     * Stores this photographer in XML.
     * @return if the photographer was successfully stored into the xml file.
     */
    @Override
    public boolean saveToXML(File file) {
        
        // Almacenar en un fichero
        
        this.createXMLTree();
        
        try{
            
            OutputFormat format = new OutputFormat(this.xml);
            format.setIndenting(true);
            
            XMLSerializer serializerTofile = new XMLSerializer(
                new FileOutputStream(file)
                , format);
            serializerTofile.serialize(this.xml);
            
            return true;
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return false;
    }

    /**
     * Stores this photographer in XML.
     * @return if the photographer was successfully stored into the xml file.
     */
    @Override
    public boolean saveToXML(String filePath) {
       
        // Almacenar en un fichero
        
        this.createXMLTree();
        
        try{
            
            OutputFormat format = new OutputFormat(this.xml);
            format.setIndenting(true);
            XMLSerializer serializerTofile = new XMLSerializer(
                new FileOutputStream(
                    new File(filePath))
                , format);
            serializerTofile.serialize(this.xml);
            
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
        return "Picture URL: " + this.getUrl();
    }
}
