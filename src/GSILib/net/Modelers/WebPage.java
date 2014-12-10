/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net.Modelers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alvaro
 */
public class WebPage {
    
    private String content = "text/html";
    private String charset = "utf-8";
    private String title, body, template = "web/templates/bootstrap.html";
    
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
     * @param title
     * @param file
     * @throws IOException 
     */
    public WebPage(String title, File file) throws IOException{
        
        this.mode = "html";
        
        this.title = title;
        this.body = this.getTemplate(file.getPath());
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
    
    /**
     * TODO: JavaDoc
     * @param file 
     */
    public WebPage(String file){
        
        this.mode = "file";
        this.file = file;
    }
    
    /**
     * TODO: JavaDoc
     * @param path
     * @return
     * @throws IOException 
     */
    private String getTemplate(String path) throws IOException{
        return new String(Files.readAllBytes(Paths.get(path)));
    }
    
    /**
     * TODO: JavaDoc
     * @param html 
     */
    public void append(String html){
        this.body = this.body.concat(html);
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
            try {
                return this.getTemplate(this.template).replace("{{charset}}", this.charset).replace("{{content}}", this.content).replace("{{title}}", this.title).replace("{{body}}", this.body);
            } catch (IOException ex) {
                Logger.getLogger(WebPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (this.mode.equals("file")){
            return this.file;
        }
        
        return null;
    }
}
