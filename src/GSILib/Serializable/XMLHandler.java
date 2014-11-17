/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.Serializable;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Alvaro
 */
public final class XMLHandler {
    // XML Engine
    public org.w3c.dom.Document engine;
    // XML Store Mode
    public final String storeMode = "full"; // {"full","relational"}
    
    // TODO : JavaDoc 
    public XMLHandler(String in) throws SAXException {
        
        this.loadDocument();
        
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            this.engine = db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public XMLHandler(){
        this.loadDocument();
    }
    
    protected void loadDocument(){
        //get an instance of factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //get an instance of builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //create an instance of DOM
            this.engine = db.newDocument();
        }catch(ParserConfigurationException pce) {
            //dump it
            System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
            System.exit(1);
        }
    }
}
