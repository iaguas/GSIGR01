/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BTesting;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import GSILib.BModel.workers.Journalist;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Alvaro
 */
public class JournalistToXML{ 

    private Document xml;

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

        // Creamos el elemento raiz Journalists
        Element xmlRoot =  this.xml.createElement("Journalists");
        this.xml.appendChild(xmlRoot);
        
        ArrayList interests = new ArrayList();
        
        interests.add("Discutir");
        interests.add("Tocar las narices");
        interests.add("Jugar al CS");
        
        Journalist journalist = new Journalist("8", "Alvaro Octal", "27/12/1993", interests);

        xmlRoot.appendChild( createJournalistElement(journalist) );
    }
    
    /**
     * Helper method which creates a XML element <Journalist>
     * @param journalist The Journalist for which we need to create an xml representation
     * @return XML element snippet representing a journalist
     */
    private Element createJournalistElement(Journalist journalist){

        Element xmlJournalist = this.xml.createElement("Journalist");
        
        // Para una raiz Journalist, introducimos su id como atributo
        
        xmlJournalist.setAttribute("id", journalist.getId());

        // Para una raiz Journalist, introducimos otra raiz Name
        
        Element xmlJournalistName = this.xml.createElement("Name");
        Text journalistName = this.xml.createTextNode(journalist.getName());
        xmlJournalistName.appendChild(journalistName);
        xmlJournalist.appendChild(xmlJournalistName);

        // Para una raiz Journalist, introducimos otra raiz BirthDate
        
        Element xmlJournalistBirthDate = this.xml.createElement("BirthDate");
        Text journalistBirthDate = this.xml.createTextNode(journalist.getBirthDate());
        xmlJournalistBirthDate.appendChild(journalistBirthDate);
        xmlJournalist.appendChild(xmlJournalistBirthDate);
        
        // Para una raiz Journalist, introducimos otra raiz Interests
        
        String[] interests = journalist.getInterests();
        Element xmlJournalistInterests = this.xml.createElement("Interests");
        for(String interest : interests){
            
            // Para una raiz Interests, introducimos otra raiz Interest
            
            Element xmlJournalistInterest = this.xml.createElement("Interest");
            Text journalistInterest = this.xml.createTextNode(interest);
            xmlJournalistInterest.appendChild(journalistInterest);      
            xmlJournalistInterests.appendChild(xmlJournalistInterest);
        }
        xmlJournalist.appendChild(xmlJournalistInterests);
        
        return xmlJournalist;
    }
    
    // Document to XML
    
    private void printToFile(){

        try{
            
            OutputFormat format = new OutputFormat(this.xml);
            format.setIndenting(true);

            // Mostrar por terminal
            XMLSerializer serializerToConsole = new XMLSerializer(System.out, format);
            serializerToConsole.serialize(this.xml);


            // Almacenar en un fichero
            XMLSerializer serializerTofile = new XMLSerializer(
                new FileOutputStream(
                    new File("journalist.xml"))
                , format);
            serializerTofile.serialize(this.xml);
            
            // Almacenar en una variable
            Writer out = new StringWriter();
            XMLSerializer serializerToString = new XMLSerializer(out , format);
            serializerToString.serialize(this.xml);
            System.out.println(out.toString());

        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }
    
    // XML to Document
    
    private Document parseXmlFile(String in) throws SAXException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String args[]) throws SAXException {
        
        // Nuevo Journalist
        
        ArrayList interestsOfPirata = new ArrayList();
        
        interestsOfPirata.add("Comer pasta");
        interestsOfPirata.add("beber cerveza");
        interestsOfPirata.add("Cantar canciones");
        interestsOfPirata.add("trifulcas de bar");
        
        Journalist journalistPirata= new Journalist("2", "Pirata", "01/01/01", interestsOfPirata);
        
        System.out.println(journalistPirata.saveToXML("xml/test.xml"));
        
        Journalist journalistPirataCopia = new Journalist(journalistPirata.toXML());
        
        System.out.print(journalistPirataCopia.toString());
        
    }
    
}
