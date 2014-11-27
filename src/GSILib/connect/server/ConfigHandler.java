/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.connect.server;

import GSILib.BModel.workers.Journalist;
import GSILib.Serializable.XMLHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alvaro
 */
public class ConfigHandler {
    
    private int port;
    
    /**
     * TODO: JavaDoc
     * @param path
     * @throws IOException
     * @throws SAXException 
     */
    public ConfigHandler(String path) throws IOException, SAXException{
        String ConfigFromXML = new String(Files.readAllBytes(Paths.get(path)));
        
        XMLHandler xml = new XMLHandler(ConfigFromXML);
        
        Element xmlConfig = (Element) xml.engine.getElementsByTagName("Config").item(0);
        this.port = Integer.parseInt(xmlConfig.getAttribute("port"));
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public int getPort(){
        return this.port;
    }
}
