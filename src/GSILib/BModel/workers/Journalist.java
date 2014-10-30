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
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * This is the class Journalist.
 * He represent a worker who writes Document into de system. He has ID, name,
 * birthDate and, especialy, interest.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */

public class Journalist extends Worker implements XMLRepresentable{
    
    // Atributo de la clase
    private List<String> interests = new ArrayList<>(); // Lista de intereses.
    // XML Engine
    private org.w3c.dom.Document xml;
    
    /**
     * Class constructor
     * @param id This is an unique ID of the worker.
     * @param name The name of the worker.
     * @param birthDate The birth dathe of the worker.
     * @param interests Is a list of a keyword of interest of a Journalist.
     */
    public Journalist(String id, String name, String birthDate, ArrayList interests){
        // Utilizamos el constructor de la superclase
        super(id, name, birthDate);
        // Introducimos los intereses en esta clase, que es quien los contiene.
        this.interests = interests;
    }
    
    /**
     * Gets the interest of a journalist
     * @return the array of interests
     */
    public String[] getInterests(){
        if(this.interests.isEmpty())
            return null;
        return this.interests.toArray(new String[this.interests.size()]);
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
     * Helper method which creates a XML element <Journalist>
     * @return XML element snippet representing a journalist
     */
    public Element getElement(org.w3c.dom.Document xml){

        Element xmlJournalist = xml.createElement("Journalist");
        
        // Para una raiz Journalist, introducimos su id como atributo
        
        xmlJournalist.setAttribute("id", this.getId());

        // Para una raiz Journalist, introducimos otra raiz Name
        
        Element xmlJournalistName = xml.createElement("Name");
        Text journalistName = xml.createTextNode(this.getName());
        xmlJournalistName.appendChild(journalistName);
        xmlJournalist.appendChild(xmlJournalistName);

        // Para una raiz Journalist, introducimos otra raiz BirthDate
        
        Element xmlJournalistBirthDate = xml.createElement("BirthDate");
        Text journalistBirthDate = xml.createTextNode(this.getBirthDate());
        xmlJournalistBirthDate.appendChild(journalistBirthDate);
        xmlJournalist.appendChild(xmlJournalistBirthDate);
        
        // Para una raiz Journalist, introducimos otra raiz Interests
        
        String[] interests = this.getInterests();
        Element xmlJournalistInterests = xml.createElement("Interests");
        for(String interest : interests){
            
            // Para una raiz Interests, introducimos otra raiz Interest
            
            Element xmlJournalistInterest = xml.createElement("Interest");
            Text journalistInterest = xml.createTextNode(interest);
            xmlJournalistInterest.appendChild(journalistInterest);      
            xmlJournalistInterests.appendChild(xmlJournalistInterest);
        }
        xmlJournalist.appendChild(xmlJournalistInterests);
        
        return xmlJournalist;
    }
    
    /**
     * Gets this journalist in XML string.
     * @return the xml string of this journalist.
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
     * Stores this journalist in XML.
     * @return if the journalist was successfully stored into the xml file.
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
     * Stores this journalist in XML.
     * @return if the journalist was successfully stored into the xml file.
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
     * @param jr a journalist
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Journalist jr){
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(jr.getId());
    }
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos del periodista.
        return "+ Journalist ID: " + this.getId() + "\n"
                + "|- Name: " + this.getName() + "\n"
                + "|- BirthDate: " + this.getBirthDate() + "\n"
                + "|- Interests: " + this.interests + "\n";
    }
}
