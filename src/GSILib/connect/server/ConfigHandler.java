/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.server;

import GSILib.Serializable.XMLHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * This class makes posible introuduce the configuration information to BusinessServer.
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class ConfigHandler {
    
    private int port;
    private String HRTag, VLTag;
    
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
        this.HRTag = xmlConfig.getAttribute("HRTag");
        this.VLTag = xmlConfig.getAttribute("VLTag");
    }
    
    /**
     * Gets connection port
     * @return port
     */
    public int getPort(){
        return this.port;
    }
    
    /**
     * Gets connection HR tag
     * @return HRTag
     */
    public String getHRTag(){
        return this.HRTag;
    }
    
    /**
     * Gets connection VL tag
     * @return VLTag
     */
    public String getVLTag(){
        return this.VLTag;
    }
}
