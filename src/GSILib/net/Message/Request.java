/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net.Message;

import static java.lang.System.exit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alvaro
 */
public class Request {
    
    private String request;
    
    private String pattern = "(GET|HEAD|POST|PUT|DELETE|TRACE|OPTIONS|CONNECT) ([^\\ ]+) ([^\\\n]+)";
    private String order, path, mode;
    
    /**
     * TODO: JavaDoc
     * @param request 
     */
    public Request(String request){
        
        this.request = request;
        
        Pattern pattern = Pattern.compile(this.pattern);
        Matcher matcher = pattern.matcher(this.request);
        if (matcher.find()) {

            // Peticion valida
            
            this.order = matcher.group(1);
            this.path = matcher.group(2);
            this.mode = matcher.group(3);
        }
        else{
            
            // Peticion invalida
            
            exit(0);
        }
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getOrder(){
        return this.order;
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getPath(){
        return this.path;
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getMode(){
        return this.mode;
    }
    
    //------------------------------------------------------------------------------
    //  GET Useless Data
    //------------------------------------------------------------------------------
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getReduced(){
        return this.request.split(("\r\n|\r|\n"))[0];
    }
    
    /**
     * TODO: JavaDoc
     * @return 
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
     * TODO: JavaDoc
     * @return 
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
     * TODO: JavaDoc
     * @return 
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
     * TODO: JavaDoc
     * @return 
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
     * TODO: JavaDoc
     * @return 
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
     * TODO: JavaDoc
     * @return 
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
     * TODO: JavaDoc
     * @return 
     */
    public int countLines(){
        return this.request.split("\r\n|\r|\n").length;
    }
    
    /**
     * TODO: Dalete when testing ends
     * @return 
     */
    @Override
    public String toString(){
        return this.request;
    }
}
