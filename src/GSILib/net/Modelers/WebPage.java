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
 * Creates a WebPage by several collected HTML templates
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class WebPage {
    
    private String content = "text/html";
    private String charset = "utf-8";
    private String title, body, template = "web/templates/bootstrap.html";
    
    private String file;
    
    private String mode = null; // mode = {"html" | "file" | "null"}
    
    /**
     * Class constructor with title and body paramters
     * @param title title of webpage.
     * @param body body of webpage.
     */
    public WebPage(String title, String body){
        // Parámetros básicos de la clase.
        this.mode = "html";
        this.title = title;
        this.body = body;
    }
    
    /**
     * Class constructor with title and file parameters
     * @param title title of webpage.
     * @param file file of the template of body of webpage.
     * @throws IOException exception derived from io.
     */
    public WebPage(String title, File file) throws IOException{  // TODO: Sin excepción
        // Parámetros básicos de la clase.
        this.mode = "html";
        this.title = title;
        this.body = this.getTemplate(file.getPath());
    }
    
    /**
     * Class constructor with file parameter
     * @param file file of the template of body of webpage.
     * @throws java.io.FileNotFoundException exception derived from not found file
     * @throws java.io.IOException exception derived from io
     */
    public WebPage(File file) throws FileNotFoundException, IOException{ // TODO: Sin excepción
        // Parámetros básicos de la clase.
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
     * Class constructor with file path parameter
     * @param file file of the template of body of webpage.
     */
    public WebPage(String file){
        // Parámetros básicos de la clase.
        this.mode = "file";
        this.file = file;
    }
    
    /**
     * Gets the HTML template based on a file
     * @param path ask by user.
     * @return HTML file in String form
     * @throws IOException 
     */
    private String getTemplate(String path) throws IOException{
        // Devolvemos la plantilla.
        return new String(Files.readAllBytes(Paths.get(path)));
    }
    
    /**
     * Concatenates two HTML bodies into the first one
     * @param html file whose body is to be concatenated
     */
    public void append(String html){
        // Concatenamos el string actual con el introducido.
        this.body = this.body.concat(html);
    }
    
    /**
     * Sets the content for a given HTML template
     * @param content content of webpage.
     */
    public void setContent(String content){
        // Establecemos el contenido.
        this.content = content;
    }
    
    /**
     * Sets the charset for a given HTML template
     * @param charset charset of webpage.
     */
    public void setCharset(String charset){
        // Establecemos el tipo de conjunto de caracteres.
        this.charset = charset;
    }
    
    /**
     * Sets the title for a given HTML template
     * @param title title of webpage
     */
    public void setTitle(String title){
        // Establecemos el título.
        this.title = title;
    }
    
    /**
     * Sets the body for a given HTML template
     * @param body body of webpage.
     */
    public void setBody(String body){
        // Establecemos el cuerpo de la página.
        this.body = body;
    }
    
    /**
     * Replaces oldString for a newString in an HTML template
     * @param oldString string to replase.
     * @param newString string with replase.
     */
    public void replaceInTemplate(String oldString, String newString){
        // Remplazamos un string por otro.
        this.body = this.body.replace(oldString, newString);
    }
    
    @Override
    public String toString(){
        // En función del modo elegido, guardamos la información de clase en el fichero.
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
        // Si ha habido problemas, devolvemos un string nulo.
        return null;
    }
}
