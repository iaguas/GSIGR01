/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net.Message;

import java.io.UnsupportedEncodingException;
import static java.lang.System.exit;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a modeler of a HTTP Request and implements it funcionality.
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Request {
    
    private final String request;
    
    private final String patterns = "(GET|HEAD|POST|PUT|DELETE|TRACE|OPTIONS|CONNECT) ([^\\ ]+) ([^\\\n]+)";
    private String order, path, mode;
    private HashMap<String, String> post;
    
    /**
     * Class constructor with request parameter
     * @param request HTTP request
     */
    public Request(String request){
        // Establecemos los parámetros de la clase.
        this.request = request;
    }
    
    /**
     * This method process a HTTP Request correctly inicializated.
     * @throws UnsupportedEncodingException  exception derivated from unsupported encoding.
     */
    public void processRequest() throws UnsupportedEncodingException{
        // Inicializamos el comparador de expresiones regulares.
        Pattern pattern = Pattern.compile(this.patterns);
        Matcher matcher = pattern.matcher(this.request);
        
        //Comprobamos la petición
        if (matcher.find()) {
            // Peticion valida
            this.order = matcher.group(1);
            this.path = matcher.group(2);
            this.mode = matcher.group(3);
            
            // Generamos, si es post, un HashMap de variables
            if (this.order.equals("POST")){
                this.post = this.getPostHashMap(this.getPOSTData());
            }   
        }
        else{
            // Peticion invalida    
            exit(0);
        }
    }
    
    /**
     * Gets the URL request order.
     * @return order An URL request order.
     */
    public String getOrder(){
        return this.order;
    }
    
    /**
     * Gets the path of the requested web directory.
     * @return path path of the requested web directory.
     */
    public String getPath(){
        return this.path;
    }
    
    /**
     * Gets mode of the URL request.
     * @return mode mode of the URL request.
     */
    public String getMode(){
        return this.mode;
    }
   
    /**
     * Gets a reduced URL request
     * @return reduced URL request.
     */
    public String getReduced(){
        return this.request.split(("\r\n|\r|\n"))[0];
    }
    
    /**
     * Gets the address of the request host (web client)
     * @return String with the host identifier
     */
    public String getHost(){
        Matcher matcher = Pattern.compile("Host: ([^\\\n]+)").matcher(this.request);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else{
            return null;
        }
    }
    
    /**
     * Confirms that the host sends the request in the correct encoding
     * @return String HTTP Accept-Encoding status.
     */
    public String getAcceptEnconding(){
        Matcher matcher = Pattern.compile("Accept-Encoding: ([^\\\n]+)").matcher(this.request);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else{
            return null;
        }
    }
    
    /**
     * Gets the request acknoledgement 
     * @return String HTTP Accept status.
     */
    public String getAccept(){
        Matcher matcher = Pattern.compile("Accept: ([^\\\n]+)").matcher(this.request);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else{
            return null;
        }
    }
    
    /**
     * Identifies the user agent (web client) of the request host
     * @return String with the name of the user agent
     */
    public String getUserAgent(){
        Matcher matcher = Pattern.compile("User-Agent: ([^\\\n]+)").matcher(this.request);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else{
            return null;
        }
    }
    
    /**
     * Validates the web client's language
     * @return validation of the web client language
     */
    public String getAcceptLanguaje(){
        Matcher matcher = Pattern.compile("Accept-Language: ([^\\\n]+)").matcher(this.request);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else{
            return null;
        }
    }
    
    /**
     * Gets data on the cache control
     * @return String HTTP Cache-control status.
     */
    public String getCacheControl(){
        Matcher matcher = Pattern.compile("Cache-Control: ([^\\\n]+)").matcher(this.request);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else{
            return null;
        }
    }
    
    /**
     * Gets the connection status
     * @return String HTTP Connection status.
     */
    public String getConnection(){
        Matcher matcher = Pattern.compile("Connection: ([^\\\n]+)").matcher(this.request);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else{
            return null;
        }
    }
    
    /**
     * Gets the data sent by POST for such case
     * @return String HTTP POST-Data status.
     */
    public String getPOSTData(){
        Matcher matcher = Pattern.compile("POST-Data: ([^\\\n]+)").matcher(this.request);
        if (matcher.find()) {
            return matcher.group(1);
        }
        else{
            return null;
        }
    }
    
    /**
     * Returns number of lines of the request message
     * @return int number of lines
     */
    public int countLines(){
        return this.request.split("\r\n|\r|\n").length;
    }
    
    /**
     * Verifies whether the request is a POST request by a given key
     * @param key key of a POST request.
     * @return A string, in case the request is POST
     */
    public String getPOST(String key){
        return this.post.get(key);
    }
    
    @Override
    public String toString(){
        return this.request;
    }
    
    /**
     * Transforms a POST request String into a HashMap
     * @param postString string of a post request
     * @return post request in String HashMap form
     */
    private HashMap<String, String> getPostHashMap(String postString) throws UnsupportedEncodingException{
        
        HashMap<String, String> post = new HashMap();
        
        String[] postVarables = postString.split("&");
        for(String variable : postVarables){
            
            // Tratamos cada variable y la insertamos en una HashMap de clave valor
            
            Matcher matcher = Pattern.compile("([^\\=]+)=([^\\n]+)").matcher(variable);
            if (matcher.find()) {
                post.put(URLDecoder.decode(matcher.group(1), "ISO-8859-1"), URLDecoder.decode(matcher.group(2), "ISO-8859-1"));
            }
            else{
                System.err.println("Error decoding a POST variable");
            }
        }
        
        return post;
    }
}
