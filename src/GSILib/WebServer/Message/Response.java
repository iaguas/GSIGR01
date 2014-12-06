/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.WebServer.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Alvaro
 */
public class Response {
    
    private String mode, contentType = "text/html", html, status;
    private Date date;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    
    /**
     * TODO: JavaDoc
     * @param mode
     * @param status 
     */
    public Response(String mode, String status){
        this.mode = mode;
        this.status = status;
        this.date = new Date();
    }
    
    /**
     * TODO: JavaDoc
     * @param mode
     * @param status
     * @param html 
     */
    public Response(String mode, String status, String html){
        this.mode = mode;
        this.status = status;
        this.html = html;
        this.date = new Date();
    }
    
    /**
     * TODO: JavaDoc
     * @param mode
     * @param status
     * @param html
     * @param contentType 
     */
    public Response(String mode, String status, String html, String contentType){
        this.mode = mode;
        this.status = status;
        this.html = html;
        this.contentType = contentType;
        this.date = new Date();
    }
    
    //------------------------------------------------------------------------------
    //  SET
    //------------------------------------------------------------------------------
    
    /**
     * TODO: JavaDoc
     * @param mode 
     */
    public void setMode(String mode){
        this.mode = mode;
    }
    
    /**
     * TODO: JavaDoc
     * @param status 
     */
    public void setStatus(String status){
        this.status = status;
    }
    
    /**
     * TODO: JavaDoc
     * @param contentType 
     */
    public void setContentType(String contentType){
        this.contentType = contentType;
    }
    
    /**
     * TODO: JavaDoc
     * @param html 
     */
    public void setHTML(String html){
        this.html = html;
    }
    
    //------------------------------------------------------------------------------
    //  GET
    //------------------------------------------------------------------------------
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getDate(){
        return this.simpleDateFormat.format(this.date);
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    private int getContentLength(){
        return this.html.length();
    }
    
    //------------------------------------------------------------------------------
    //  UTILS
    //------------------------------------------------------------------------------
    
    @Override
    public String toString(){
        return this.mode + " " + this.status + "\n"
                + "Date: " + this.getDate() + "\n"
                + "Content-Type: " + this.contentType + "\n"
                + "Content-Length: " + this.getContentLength() + "\n\n"
                + this.html;
    }
}