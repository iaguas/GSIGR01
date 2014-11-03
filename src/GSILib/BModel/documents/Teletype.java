/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel.documents;

import GSILib.BModel.Document;
import GSILib.BModel.workers.Journalist;
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
 * This is the class Teletype.
 * This is a document type in which you can't find pictures or any other extra 
 * material. They are only formed by a headline, a body and an author.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Teletype extends Document implements XMLRepresentable{
    // XML Engine
    private org.w3c.dom.Document xml;
    // XML Store Mode
    static final String XMLStoreMode = "full"; // {"full","relational"}
    
    /**
     * Class constructor that makes an object with headline, body and author.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     */
    public Teletype(String headline, String body, Journalist journalist){
        // Llamamos al constructor de la clase padre.
        super(headline, body, journalist);
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
     * Helper method which creates a XML element <Teletype>
     * @return XML element snippet representing a teletype
     */
    public Element getElement(org.w3c.dom.Document xml){

        Element xmlTeletype = xml.createElement("Teletype");

        // Para una raiz Teletype, introducimos otra raiz Headline
        
        Element xmlTeletypeHeadline = xml.createElement("Headline");
        Text teletypeHeadline = xml.createTextNode(this.getHeadline());
        xmlTeletypeHeadline.appendChild(teletypeHeadline);
        xmlTeletype.appendChild(xmlTeletypeHeadline);

        // Para una raiz Teletype, introducimos otra raiz Body
        
        Element xmlTeletypeBody = xml.createElement("Body");
        Text teletypeBody = xml.createTextNode(this.getBody());
        xmlTeletypeBody.appendChild(teletypeBody);
        xmlTeletype.appendChild(xmlTeletypeBody);
        
        if (this.XMLStoreMode.equals("relational")){
            
            // Para una raiz Journalist, introducimos su id como atributo
            
            Element xmlTeletypeJournalist = xml.createElement("Journalist");
            xmlTeletypeJournalist.setAttribute("id", this.getAuthor().getId());
            xmlTeletype.appendChild(xmlTeletypeJournalist);
        }
        else if(this.XMLStoreMode.equals("full")){
            
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
     * @return the xml string of this teletype.
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
     * Stores this teletype in XML.
     * @return if the teletype was successfully stored into the xml file.
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
     * Stores this teletype in XML.
     * @return if the teletype was successfully stored into the xml file.
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
}
