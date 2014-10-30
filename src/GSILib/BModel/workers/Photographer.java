/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel.workers;

import GSILib.BModel.Worker;
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
 * This is the class Photographer.
 * He represent a worker who takes pictures into de system. He has ID, name,
 * birthDate and, especialy, its residence and holyday residence.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Photographer extends Worker implements XMLRepresentable{
    private String regularResidence, holidayResidence;
    // XML Engine
    private org.w3c.dom.Document xml;
    
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

        this.xml.appendChild(this.getElement());
    }
    
    /**
     * Helper method which creates a XML element <Photographer>
     * @return XML element snippet representing a photographer
     */
    public Element getElement(){

        Element xmlPhotographer = this.xml.createElement("Photographer");
        
        // Para una raiz Photographer, introducimos su id como atributo
        
        xmlPhotographer.setAttribute("id", this.getId());

        // Para una raiz Photographer, introducimos otra raiz Name
        
        Element xmlPhotographerName = this.xml.createElement("Name");
        Text photographerName = this.xml.createTextNode(this.getName());
        xmlPhotographerName.appendChild(photographerName);
        xmlPhotographer.appendChild(xmlPhotographerName);

        // Para una raiz Photographer, introducimos otra raiz BirthDate
        
        Element xmlPhotographerBirthDate = this.xml.createElement("BirthDate");
        Text photographerBirthDate = this.xml.createTextNode(this.getBirthDate());
        xmlPhotographerBirthDate.appendChild(photographerBirthDate);
        xmlPhotographer.appendChild(xmlPhotographerBirthDate);
        
        // Para una raiz Photographer, introducimos otra raiz RegularResidence
        
        Element xmlPhotographerRegularResidence = this.xml.createElement("RegularResidence");
        Text photographerRegularResidence = this.xml.createTextNode(this.getRegularResidence());
        xmlPhotographerRegularResidence.appendChild(photographerRegularResidence);
        xmlPhotographer.appendChild(xmlPhotographerRegularResidence);
        
        // Para una raiz Photographer, introducimos otra raiz HolidayResidence
        
        Element xmlPhotographerHolidayResidence = this.xml.createElement("HolidayResidence");
        Text photographerHolidayResidence = this.xml.createTextNode(this.getHolidayResidence());
        xmlPhotographerHolidayResidence.appendChild(photographerHolidayResidence);
        xmlPhotographer.appendChild(xmlPhotographerHolidayResidence);
        
        return xmlPhotographer;
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
                + "|- Holiday residence: " + this.holidayResidence;
    }
}