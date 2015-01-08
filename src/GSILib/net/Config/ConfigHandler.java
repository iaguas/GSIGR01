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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class makes posible introuduce the configuration information to BusinessServer.
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class ConfigHandler {
    
    private String loadDataPath;
    private List<ServerConfig> serverConfigs = new ArrayList();
    
    /**
     * Empty class constructor.
     */
    public ConfigHandler(){}
    
    /**
     * Adds a server configuration (ServerConfig) to this class' list of server configurations (serverConfigs)
     * @return true if and only if a server configuration is added correctly
     */
    public boolean setConfig(){
        this.serverConfigs.add(new ServerConfig());
        return true;
    }
    
    /** 
     * Sets a server configuration stored in an XML file, given by a path.
     * @param path Location of the XML file containing a server configuration
     * @return true if and only if XML correctly read
     */
    public boolean setConfig(String path){
        try{
            // Obtenemos el string de configuración en XML
            String ConfigFromXML = new String(Files.readAllBytes(Paths.get(path)));
            // Creamos el manejador de objetos XML.
            XMLHandler xml = new XMLHandler(ConfigFromXML);
            
            // Obtenemos los elementos del archivo XML.
            Element xmlConfig = (Element) xml.engine.getElementsByTagName("ServerConfigs").item(0);
            this.loadDataPath = ((Element) xmlConfig).getAttribute("loadDataDir");
            // Obtenemos los servidores.
            NodeList xmlServerConfigs = xmlConfig.getElementsByTagName("ServerConfig");
            for (int i = 0; i < xmlServerConfigs.getLength(); i++) {
                this.serverConfigs.add(new ServerConfig((Element) xmlServerConfigs.item(i)));
            } 
        }
        catch(IOException | SAXException e){
            System.err.println("ERROR in charge of configuration file. Se ejecuta la configuración por defecto \n " + e);
            this.serverConfigs.add(new ServerConfig());
            return false;
        }
        // Ha ido bien.
        return true;
    }
   
    /**
     * Returns the path of the XML server configuration file.
     * @return The path of the XML file
     */
    public String getLoadDataPath(){
        // Devolvemos la ruta del fichero.
        return this.loadDataPath;
    }
    
   /**
     * Returns the list of the saved server configurations.
     * @return a list of ServerConfigs
     */
    public ServerConfig[] getServerConfigs(){
        // Devolvemos la configuración de los servidores (puede haber más de uno).
        if(this.serverConfigs.isEmpty())
            return null;
        return this.serverConfigs.toArray(new ServerConfig[this.serverConfigs.size()]);
    }
}

