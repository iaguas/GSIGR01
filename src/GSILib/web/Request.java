/* 
 * Práctica 04 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alvaro
 */
public class Request {
    
    private String pattern = "(GET|POST) ([^\\s]+) ([^\\s]+)";
    private String order;
    private String file;
    private String mode;
    
    /**
     * TODO: JavaDoc
     * @param request 
     */
    public Request(String request){
        
        Pattern pattern = Pattern.compile(this.pattern);
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {

            this.order = matcher.group(1);
            this.file = matcher.group(2);
            this.mode = matcher.group(3);
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
    public String getFile(){
        return this.file;
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getMode(){
        return this.mode;
    }
}
