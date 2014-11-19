/* TODO: javadoc y comentarios */

/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */

/* REFERENCIA
https://docs.oracle.com/cd/E17276_01/html/gsg_xml/java/exceptions.html
https://docs.oracle.com/javase/tutorial/jaxp/properties/error.html
*/

package GSILib.persistence;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This is the class XMLParsingException.
 * This class handles those cases which can't be handled by the exceptions that
 * classes throw
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class XMLParsingException extends Exception{
    
    // constructor
    /*public XMLParsingException() {

    }*/
    
    public XMLParsingException(String message) {
        super(message);
    }

    public XMLParsingException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    /**
     * Getting XML DOM element
     * @param xml string
     * @return 
     * */
    public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is); 

            } catch (ParserConfigurationException | SAXException | IOException e) {
                //Log.e("Error: ", e.getMessage());
                e.getMessage();
                return null;
            }

            return doc;
    }
    
     /** Getting node value
      * @param elem element
      */
    public final String getElementValue( Node elem ) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    /**
     * Getting node value
     * @param item node
     * @param str string
     * @return 
     * */
    public String getValue(Element item, String str) {     
           NodeList n = item.getElementsByTagName(str);        
           return this.getElementValue(n.item(0));
    }
    
}
