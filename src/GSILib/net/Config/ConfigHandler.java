/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net.Config;

import GSILib.Serializable.XMLHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class makes posible introuduce the configuration information to BusinessServer.
 * @author Alvaro
 */
public class ConfigHandler {
    
    private String loadDataPath;
    private List<ServerConfig> serverConfigs = new ArrayList();
    
    /**
     * Constructor which gets WebServer configuration from an XML document
     * @param path Path of the XML file to read
     * @throws IOException handles errors asociated to IO
     * @throws SAXException handles errors asociated to XML Handling
     */
    public ConfigHandler(String path) throws IOException, SAXException{
        String ConfigFromXML = new String(Files.readAllBytes(Paths.get(path)));
        
        XMLHandler xml = new XMLHandler(ConfigFromXML);
        
        Element xmlConfig = (Element) xml.engine.getElementsByTagName("ServerConfigs").item(0);
        this.loadDataPath = ((Element) xmlConfig).getAttribute("loadDataDir");
        
        NodeList xmlServerConfigs = xmlConfig.getElementsByTagName("ServerConfig");
        
        for (int i = 0; i < xmlServerConfigs.getLength(); i++) {
            this.serverConfigs.add(new ServerConfig((Element) xmlServerConfigs.item(i)));
        } 
    }
    
    /**
     * TODO: JavaDoc
     */
    public ConfigHandler(){
        this.serverConfigs.add(new ServerConfig());
    }
   
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getLoadDataPath(){
        return this.loadDataPath;
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public ServerConfig[] getServerConfigs(){
        if(this.serverConfigs.isEmpty())
            return null;
        return this.serverConfigs.toArray(new ServerConfig[this.serverConfigs.size()]);
    }
}

