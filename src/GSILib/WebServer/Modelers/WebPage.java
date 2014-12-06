/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.WebServer.Modelers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import static java.lang.System.exit;

/**
 *
 * @author Alvaro
 */
public class WebPage {
    
    private String content = "text/html";
    private String charset = "utf-8";
    private String title, body;
    
    private String file;
    
    private String mode = null; // mode = {"html" | "file" | "null"}
    
    /**
     * TODO: JavaDoc
     * @param title
     * @param body 
     */
    public WebPage(String title, String body){
        
        this.mode = "html";
        
        this.title = title;
        this.body = body;
    }
    
    /**
     * TODO: JavaDoc
     * @param file 
     */
    public WebPage(File file) throws FileNotFoundException, IOException{
        
        this.mode = "file";
        
        BufferedReader reader = new BufferedReader( new FileReader (file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        while( ( line = reader.readLine() ) != null ) {
            stringBuilder.append( line );
            stringBuilder.append( ls );
        }
        
        this.file = stringBuilder.toString();
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
        
        if (this.mode.equals("html")){
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
        else if (this.mode.equals("file")){
            return this.file;
        }
        
        return null;
    }
}
