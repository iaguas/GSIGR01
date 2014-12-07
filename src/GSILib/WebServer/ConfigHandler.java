/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.WebServer;

import GSILib.Serializable.XMLHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * This class makes posible introuduce the configuration information to BusinessServer.
 * @author Alvaro
 */
public class ConfigHandler {
    
    private int port = 0;
    private String localDir = null, loadDataPath = null;
    
    /**
     * Constructor which gets RMI configuration from an XML document
     * @param path Path of the XML file to read
     * @throws IOException handles errors asociated to IO
     * @throws SAXException handles errors asociated to XML Handling
     */
    public ConfigHandler(String path) throws IOException, SAXException{
        String ConfigFromXML = new String(Files.readAllBytes(Paths.get(path)));
        
        XMLHandler xml = new XMLHandler(ConfigFromXML);
        
        Element xmlConfig = (Element) xml.engine.getElementsByTagName("Config").item(0);
        this.port = Integer.parseInt(xmlConfig.getAttribute("port"));
        this.localDir = xmlConfig.getAttribute("localDir");
        this.loadDataPath = xmlConfig.getAttribute("loadDataDir");
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
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getLoadDataPath(){
        return this.loadDataPath;
    }
}

