// ******************************** REVISADA **********************************
/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net.Config;

import org.w3c.dom.Element;

/**
 *
 * @author Alvaro
 */
public class ServerConfig {
    
    private int port;
    private String domain;
    
    /**
     * Constructor which gets WebServer configuration from an XML document
     * @param xmlConfig a XML Element of a ServerConfig
     */
    public ServerConfig(Element xmlConfig) {
        // Si se pueden leer, se leen, sino los default
        if (! xmlConfig.getAttribute("port").isEmpty())
            this.port = Integer.parseInt(xmlConfig.getAttribute("port"));
        else 
            port = 8080;
        if (! xmlConfig.getAttribute("localDir").isEmpty())
            this.domain = xmlConfig.getAttribute("localDir");
        else
            domain = "web/";
    }
    
    /**
     * TODO: JavaDoc
     */
    public ServerConfig(){
        // Constructor básico con parámetros predeterminados.
        port = 8080;
        domain = "web/";
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
    public String getDomain(){
        return this.domain;
    }
}
