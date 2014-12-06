/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.WebServer.Modelers;

/**
 *
 * @author Alvaro
 */
public class WebPage {
    
    private String content = "text/html";
    private String charset = "utf-8";
    private String title, body;
    
    public WebPage(String title, String body){
        this.title = title;
        this.body = body;
    }
    
    //------------------------------------------------------------------------------
    //  SET
    //------------------------------------------------------------------------------
    
    /**
     * TODO: JavaDoc
     * @param content 
     */
    public void setContent(String content){
        this.content = content;
    }
    
    /**
     * TODO: JavaDoc
     * @param charset 
     */
    public void seyChatset(String charset){
        this.charset = charset;
    }
    
    /**
     * TODO: JavaDoc
     * @param title 
     */
    public void setTitle(String title){
        this.title = title;
    }
    
    /**
     * TODO: JavaDoc
     * @param body 
     */
    public void setBody(String body){
        this.body = body;
    }
    
    @Override
    public String toString(){
        return  "<html>\n" +
                "   <head>\n" +
                "       <meta http-equiv = \"content-type\" content = \"" + this.content + "; charset=" + this.charset + "\"/>\n" +
                "       <title>" + this.title + "</title>\n" +
                "   </head>\n" +
                "   <body>\n" +
                "       " + this.body + "\n" +
                "   </body>\n" +
                "</html>";
    }
}
