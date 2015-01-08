/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel.workers;

import GSILib.BModel.Worker;
import GSILib.Serializable.XMLHandler;
import GSILib.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * This is the class Photographer.
 * He represent a worker who takes pictures into de system. He has ID, name,
 * birthDate and, especialy, its residence and holyday residence.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Photographer extends Worker implements XMLRepresentable, Serializable{
    private String regularResidence, holidayResidence;
    
    /**
     * Class constructor
     * @param id This is an unique ID of the worker.
     * @param name The name of the worker.
     * @param birthDate The birth dathe of the worker.
     * @param regularResidence The regular residence of a photographer.
     * @param holidayResidence The residence during the holydays of a photographer.
     */
    public Photographer(String id, String name, String birthDate, String regularResidence, String holidayResidence){
        
        super(id, name, birthDate);
        
        this.regularResidence = regularResidence;
        this.holidayResidence = holidayResidence;
    }
    
    /**
     * Class constructor
     * @param photographerFromXML This is a xml String which represents a Photographer.
     * @throws org.xml.sax.SAXException exception derivated from data consistence.
     */
    public Photographer(String photographerFromXML) throws SAXException{
        
        // Creamos un worker nulo
        
        super();
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler(photographerFromXML);
        
        Element xmlPhotographer = (Element) xml.engine.getElementsByTagName("Photographer").item(0);
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlPhotographer);
        
    }
    
    /**
     * Imports photographers from the Element gathered from an XML Document
     * containing Photographer node list; Worker type atributes are inherited
     * @param xmlPhotographer Element type containing "Photographer" tagged nodes
     */
    public Photographer(Element xmlPhotographer){
        
        // Creamos un worker nulo
        
        super();
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlPhotographer);
         
    }
    
    /**
     * Lowest rank method which obtains the atribute values for Photographer from an
     * XML Element. 
     * @param xmlPhotographer Element type which contains useful data 
     * (regularResidence and holidayResidence)
     */
    protected void loadFromElement(Element xmlPhotographer){
        
        // Worker rellena sus datos
        
        super.loadFromElement(xmlPhotographer);
        
        // Photographer rellena sus datos
        
        this.regularResidence = xmlPhotographer.getAttribute("regularResidence");
        this.holidayResidence = xmlPhotographer.getAttribute("holidayResidence");
    }
    
    /**
     * Gets the regular residence of the associated photographer
     * @return the regular residence of the associated photographer
     */
    public String getRegularResidence(){
        // Devolvemos la residencia habitual.
        return this.regularResidence;
    }
    
    /**
     * Gets the holiday residence of the associated photographer
     * @return the holiday residence of the associated photographer
     */
    public String getHolidayResidence(){
        // Devolvemos la residencia vacacional.
        return this.holidayResidence;
    }
    
    /**
     * Helper method which creates a XML element "Photographer"
     * @param xml XML handler for system.
     * @return XML element snippet representing a photographer
     */
    public Element getElement(XMLHandler xml){

        Element xmlPhotographer = xml.engine.createElement("Photographer");
        
        // Para una raiz Photographer, introducimos su id como atributo
        
        xmlPhotographer.setAttribute("id", this.getId());

        // Para una raiz Photographer, introducimos su name como atrubuto
        
        xmlPhotographer.setAttribute("name", this.getName());

        // Para una raiz Photographer, introducimos su birthDate como atributo
        
        xmlPhotographer.setAttribute("birthDate", this.getBirthDate());
        
        // Para una raiz Photographer, introducimos su regularResidence como atributo
        
        xmlPhotographer.setAttribute("regularResidence", this.getRegularResidence());
        
        // Para una raiz Photographer, introducimos su holidayResidence como atributo
        
        xmlPhotographer.setAttribute("holidayResidence", this.getHolidayResidence());
        
        return xmlPhotographer;
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
     * Sets the regular residence for a Photographer
     * @param rr Regular residence address
     */
    public void setRegularResidence(String rr){
        this.regularResidence = rr;
    }
    
    /**
     * Sets the holiday residence for a Photographer
     * @param hr Holiday residence address
     */
    public void setHolidayResidence(String hr){
        this.holidayResidence = hr;
    }
    
    public void copyValuesFrom(Photographer newPhotographer){
        super.copyValuesFrom(newPhotographer);
        this.regularResidence = newPhotographer.getRegularResidence();
        this.holidayResidence = newPhotographer.getHolidayResidence();
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param p a photographer
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Photographer p){
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(p.getId());
    }
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos del fotografo.
        return "+ Photographer ID: " + this.getId() + "\n"
                + "|- Name: " + this.getName() + "\n"
                + "|- BirthDate: " + this.getBirthDate() + "\n"
                + "|- Regular residence: " + this.regularResidence + "\n"
                + "|- Holiday residence: " + this.holidayResidence + "\n";
    }
    
    @Override
    public JSONObject getJSONObject() throws JSONException{
        
        JSONObject json = super.getJSONObject();

        json.put("regularResidence", this.regularResidence);
        json.put("holidayResidence", this.holidayResidence);
        
        return json;
    }
}
