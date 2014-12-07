/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.WebServer.Config;

import GSILib.Serializable.XMLHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Alvaro
 */
public class ServerConfig {
    
    private int port = 8080;
    private String localDir = "web/";
    
    /**
     * Constructor which gets WebServer configuration from an XML document
     * @param xmlConfig a XML Element of a ServerConfig
     * @throws IOException handles errors asociated to IO
     * @throws SAXException handles errors asociated to XML Handling
     */
    public ServerConfig(Element xmlConfig) throws IOException, SAXException{

        // Si se pueden leer, se leen, sino los default
        
        if (! xmlConfig.getAttribute("port").isEmpty())
            this.port = Integer.parseInt(xmlConfig.getAttribute("port"));
        if (! xmlConfig.getAttribute("localDir").isEmpty())
            this.localDir = xmlConfig.getAttribute("localDir");
        
    }
    
    /**
     * TODO: JavaDoc
     */
    public ServerConfig(){
        
        // Constructor nulo
    }
    
    /**
     * Gets connection port
     * @return port
     */
    public int getPort(){
        return this.port;
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getLocalDir(){
        return this.localDir;
    }
}
