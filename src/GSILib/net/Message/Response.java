// ******************************** REVISADA **********************************
/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net.Message;

import GSILib.net.Modelers.WebPage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class is a modeler of a HTTP Response and implements it funcionality.
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil. 
 */
public class Response {
    
    private String mode, contentType = "text/html", status;
    private Date date;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    private WebPage webPage;
    
    /**
     * Class constructor with mode and status parameters
     * @param mode mode of response.
     * @param status HTTP status of the response
     */
    public Response(String mode, String status){
        this.mode = mode;
        this.status = status;
        this.date = new Date();
    }
    
    /**
     * Class constructor with mode, status and webPage parameters
     * @param mode mode of response.
     * @param status HTTP status of the response
     * @param webPage webpage to use.
     */
    public Response(String mode, String status, WebPage webPage){
        this.mode = mode;
        this.status = status;
        this.webPage = webPage;
        this.date = new Date();
    }
    
    /**
     * Class constructor with mode, status, webPage and contentType parameters
     * @param mode mode of response.
     * @param status HTTP status of the response
     * @param webPage webpage to use.
     * @param contentType contentType to use.
     */
    public Response(String mode, String status, WebPage webPage, String contentType){
        this.mode = mode;
        this.status = status;
        this.webPage = webPage;
        this.contentType = contentType;
        this.date = new Date();
    }
    
    /**
     * Sets the mode of the response.
     * @param mode mode of response.
     */
    public void setMode(String mode){
        this.mode = mode;
    }
    
    /**
     * Sets the status.
     * @param status status of the response.
     */
    public void setStatus(String status){
        this.status = status;
    }
    
    /**
     * Sets the content type.
     * @param contentType content type of the response
     */
    public void setContentType(String contentType){
        this.contentType = contentType;
    }
    
    /**
     * Sets the web page.
     * @param webPage webpage of the response.
     */
    public void setWebPage(WebPage webPage){
        this.webPage = webPage;
    }
    
    /**
     * Gets the date of a response
     * @return date in String format
     */
    public String getDate(){
        return this.simpleDateFormat.format(this.date);
    }
    
    /**
     * Gets the web page length of a response
     * @return int lengh fo a response webpage.
     */
    private int getContentLength(){
        return this.webPage.toString().length();
    }
    
    @Override
    public String toString(){
        return this.mode + " " + this.status + "\n"
                + "Date: " + this.getDate() + "\n"
                + "Content-Type: " + this.contentType + "\n"
                + "Content-Length: " + this.getContentLength() + "\n\n"
                + this.webPage;
    }
}