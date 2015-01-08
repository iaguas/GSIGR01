/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel.documents;

import GSILib.BModel.Document;
import GSILib.BModel.workers.Journalist;
import GSILib.Serializable.XMLHandler;
import GSILib.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * This is the class Teletype.
 * This is a document type in which you can't find pictures or any other extra 
 * material. They are only formed by a headline, a body and an author.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Teletype extends Document implements XMLRepresentable{
    
    /**
     * Class constructor that makes an object inheriting 
     * headline, body and author from Document.
     * @param headline headline of the news that you want to save.
     * @param body all text of the news.
     * @param journalist worker who has written the news.
     */
    public Teletype(String headline, String body, Journalist journalist){
        // Llamamos al constructor de la clase padre.
        super(headline, body, journalist);
    }
    
    /**
     * Constructor which obtains Element type object from an XML Document containing "Teletype"
     * tagged nodes, which are Teletype type objects.
     * @param teletypeFromXML XML document to parse, which contains nodes 
     * corresponding to the class Teletype
     * @throws SAXException exception derived from XML file reading
     */
    public Teletype(String teletypeFromXML) throws SAXException{
        
        // Creamos un Document nulo
        
        super();
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler(teletypeFromXML);
        
        Element xmlTeletype = (Element) xml.engine.getElementsByTagName("Teletype").item(0);
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlTeletype);
    }
    
    /**
     * Imports teletypes from the Element gathered from an XML Document
     * containing Teletype node list; Document type atributes are inherited
     * @param xmlTeletype Element type containing "Teletype" tagged nodes
     */
    public Teletype(Element xmlTeletype){
        
        // Creamos un Document nulo
        
        super();
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlTeletype);
         
    }
    
    /**
     * Constructor which obtains Element type object from and XML Document containing "Teletype"
     * tagged nodes -which are Teletype type objects-, and the Journalist which 
     * is desired to associate.
     * @param xmlTeletype Element type object which contains nodes 
     * corresponding to the class Teletype
     * @param journalist Journalist which is to associate
     * @throws SAXException exception derived from XML file reading
     */
    public Teletype(Element xmlTeletype, Journalist journalist) throws SAXException{
     
        // Document rellena sus datos
        
        super.loadFromElement(xmlTeletype, journalist);
    }
    
    /**
     * Imports teletypes from the Element gathered from an XML Document
     * containing Picture node list.
     * @param xmlTeletype Element type containing "Teletype" tagged nodes
     */
    protected void loadFromElement(Element xmlTeletype){
        
        // Document rellena sus datos
        
        super.loadFromElement(xmlTeletype);
    }
    
    /**
     * Helper method which creates a XML element "Teletype"
     * @param xml XML handler for system.
     * @return XML element snippet representing a teletype
     */
    public Element getElement(XMLHandler xml){

        Element xmlTeletype = xml.engine.createElement("Teletype");
        
        // Para una raiz Teletype, introducimos su id como atributo
        
        xmlTeletype.setAttribute("id", String.valueOf(super.getId()));

        // Para una raiz Teletype, introducimos otra raiz Headline
        
        xmlTeletype.setAttribute("headline", this.getHeadline());

        // Para una raiz Teletype, introducimos otra raiz Body
        
        xmlTeletype.setAttribute("body", this.getBody());
        
        if (xml.storeMode.equals("relational")){
            
            // Para una raiz Journalist, introducimos su id como atributo
            
            Element xmlTeletypeJournalist = xml.engine.createElement("Journalist");
            xmlTeletypeJournalist.setAttribute("id", this.getAuthor().getId());
            xmlTeletype.appendChild(xmlTeletypeJournalist);
        }
        else if(xml.storeMode.equals("full")){
            
            // Para una raiz Teletype, introducimos otra raiz Journalist
            
            xmlTeletype.appendChild(this.getAuthor().getElement(xml));
        }
        else{
            System.err.print("unrecognized method");
        }
        
        return xmlTeletype;
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
     * @param tp a teletype
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Teletype tp){
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(tp.getId());
    }
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos del teletipo.
        return "+ Teletype ID: " + this.getId() + "\n"
                + "|- Headline: " + this.getHeadline() + "\n"
                + "|- Body: " + this.getBody() + "\n"
                + "|- Journalist: " + this.getAuthor() + "\n";
    }
    
    
    /**
     * Teletype to JSON object parser (use of inherited method from Document)
     * @return json JSON object containing a Teletype
     * @throws JSONException exception derived from JSON inputing
     */
    public JSONObject getJSONObject() throws JSONException{
        return super.getJSONObject();
    }
}
