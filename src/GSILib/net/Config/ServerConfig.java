/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net.Config;

import org.w3c.dom.Element;

/**
 * This class gets WebServer configuration from a file
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class ServerConfig {
    
    private int port;
    private String localDir, domain;
    
    /**
     * Constructor which gets WebServer configuration from an XML document.
     * @param xmlConfig a XML Element of a ServerConfig
     */
    public ServerConfig(Element xmlConfig) {
        // Si se pueden leer, se leen, sino los default
        if (! xmlConfig.getAttribute("port").isEmpty())
            this.port = Integer.parseInt(xmlConfig.getAttribute("port"));
        else 
            this.port = 8080;
        if (! xmlConfig.getAttribute("localDir").isEmpty())
            this.localDir = xmlConfig.getAttribute("localDir");
        else
            this.localDir = "web/";
        if (! xmlConfig.getAttribute("domain").isEmpty())
            this.domain = xmlConfig.getAttribute("domain");
        else
            this.domain = "";
    }
    
    /**
     * Class constructor which sets the default server configuration.
     */
    public ServerConfig(){
        
        this.port = 8080;
        this.localDir = "web/";
        this.domain = "localhost:8080";
    }
    
    /**
     * Gets connection port.
     * @return port
     */
    public int getPort(){
        return this.port;
    }
    
    /**
     * Gets the current directory.
     * @return localDir
     */
    public String getLocalDir(){
        return this.localDir;
    }
    
    /**
     * Gets the current domain.
     * @return domain
     */
    public String getDomain(){
        return this.domain;
    }
}
