// ******************************** REVISADA **********************************
/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net.Modelers;

import GSILib.net.GetHandler;
import GSILib.net.PostHandler;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author inigo
 */


public class HTTPStatusHandler {
    String localDir;
    GetHandler getHandler;
    PostHandler postHandler;
    
    HashMap<Integer, String> stateStrings;
    
    /**
     * TODO: JAVADOC
     * @param gh
     * @param localDir 
     */
    public HTTPStatusHandler(GetHandler gh, String localDir){
        this.localDir = localDir;
        this.getHandler = gh;
        this.stateStrings = new HashMap<>();
        inicializeErrorStrings();
    }
    
    
    /**
     * TODO: JAVADOC
     * @param ph
     * @param localDir 
     */
    public HTTPStatusHandler(PostHandler ph, String localDir){
        this.localDir = localDir;
        this.postHandler = ph;
        this.stateStrings = new HashMap<>();
        inicializeErrorStrings();
    }
    
    
    /**
     * TODO: JAVADOC
     * @param errorNumber 
     */
    public void showError(int errorNumber){
        // Modificamos el estado al de error.
        getHandler.setStatus(stateStrings.get(errorNumber));
        // Devolvemos la página correspondiente.
        try {
            getHandler.setWebPage(new WebPage(stateStrings.get(errorNumber), new File(localDir + "templates/errors/" + errorNumber +".html")));
        } 
        catch (IOException ex) {
            System.err.print("ERROR in " + errorNumber + " error webpage creation.\n" + ex);
        }
    }
    
  
    /**
     * TODO: JAVADOC
     */
    private void inicializeErrorStrings(){
        // Introducimos los errores posibles en la tabla de búsqueda.
        stateStrings.put(100, "100 Continue");
        stateStrings.put(101, "101 Switching Protocols ");
        stateStrings.put(200, "200 OK ");
        stateStrings.put(201, "201 Created ");
        stateStrings.put(202, "202 Accepted ");
        stateStrings.put(203, "203 Non-Authoritative Information ");
        stateStrings.put(204, "204 No Content");
        stateStrings.put(205, "205 Reset Content");
        stateStrings.put(206, "206 Partial Content");
        stateStrings.put(300, "300 Multiple Choices ");
        stateStrings.put(301, "301 Moved Permanently");
        stateStrings.put(302, "302 Found");
        stateStrings.put(303, "303 See Other");
        stateStrings.put(304, "304 Not Modified");
        stateStrings.put(305, "305 Use Proxy");
        stateStrings.put(306, "306 (Unused)");
        stateStrings.put(307, "307 Temporary Redirect");
        stateStrings.put(400, "400 Bad Request");
        stateStrings.put(401, "401 Unauthorized");
        stateStrings.put(402, "402 Payment Required");
        stateStrings.put(403, "403 Forbidden");
        stateStrings.put(404, "404 Not Found");
        stateStrings.put(405, "405 Method Not Allowed");
        stateStrings.put(406, "406 Not Acceptable");
        stateStrings.put(407, "407 Proxy Authentication Required");
        stateStrings.put(408, "408 Request Timeout");
        stateStrings.put(409, "409 Conflict");
        stateStrings.put(410, "410 Gone");
        stateStrings.put(411, "411 Length Required");
        stateStrings.put(412, "412 Precondition Failed");
        stateStrings.put(413, "413 Request Entity Too Large");
        stateStrings.put(414, "414 Request-URI Too Long ");
        stateStrings.put(415, "415 Unsupported Media Type");
        stateStrings.put(416, "416 Requested Range Not Satisfiable");
        stateStrings.put(417, "417 Expectation Failed ");
        stateStrings.put(418, "418 Teapot"); // TODO: Este error no existe.
        stateStrings.put(500, "500 Internal Server Error");
        stateStrings.put(501, "501 Not Implemented");
        stateStrings.put(502, "502 Bad Gateway ");
        stateStrings.put(503, "503 Service Unavailable");
        stateStrings.put(504, "504 Gateway Timeout");
        stateStrings.put(505, "505 HTTP Version Not Supported");
    }
}
