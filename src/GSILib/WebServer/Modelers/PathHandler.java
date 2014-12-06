/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.WebServer.Modelers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alvaro
 */
public class PathHandler {
    
    private String path, mode = null; // mode = {"PrintableNews" | "Newspaper" | "Newspapers" | "SingleWebNews" | "WebNews" | "null"}
    
    // Patterns
    
    private final String printableNewsPattern = "\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/";
    private final String newspaperPattern = "\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/";
    private final String webNewsPattern = "\\/([^\\/]+)\\/([^\\/]+)\\/";
    private final String directoryPattern = "\\/([^\\/]+)\\/";
    
    // Directories
    
    private String year, month, day, printableNewsID, webNewsURL;
    
    
    /**
     * TODO JavaDoc
     * @param path 
     */
    public PathHandler(String path){
        
        this.path = path;
        
        // Filtramos y clasificamos segun lo que pide
     
        
        Matcher matcher = Pattern.compile("\\/newspapers\\/").matcher(this.path);
        if (matcher.find()) {
            
            // Pide algo del arbol de Newspaper
            
            matcher = Pattern.compile(this.printableNewsPattern).matcher(this.path);
            if (matcher.find()) {

                // Pide una PrintableNews

                this.mode = "PrintableNews";

                this.year = matcher.group(2);
                this.month = matcher.group(3);
                this.day = matcher.group(4);
                this.printableNewsID = matcher.group(5);
            }
            else{

                matcher = Pattern.compile(this.newspaperPattern).matcher(this.path);
                if (matcher.find()) {

                    // Pide un Newspaper

                    this.mode = "Newspaper";

                    this.year = matcher.group(2);
                    this.month = matcher.group(3);
                    this.day = matcher.group(4);
                }
                else{

                    matcher = Pattern.compile(this.directoryPattern).matcher(this.path);
                    if (matcher.find()) {

                        // Pide los Newspapers

                        this.mode = "Newspapers";
                    }
                }
            }
        }
        else{
            matcher = Pattern.compile("\\/webnews\\/").matcher(this.path);
            if (matcher.find()) {
            
                // Pide algo del arbol de WebNews
                
                matcher = Pattern.compile(this.webNewsPattern).matcher(this.path);
                if (matcher.find()) {
                    
                    // Pide una WebNews
                    
                    this.webNewsURL = matcher.group(2);
                    
                    this.mode = "SingleWebNews";
                }
                else{

                    matcher = Pattern.compile(this.directoryPattern).matcher(this.path);
                    if (matcher.find()) {

                        // Pide las WebNews

                        this.mode = "WebNews";
                    }
                }
            }
        }
    }
    
    //------------------------------------------------------------------------------
    //  GET
    //------------------------------------------------------------------------------
    
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
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getNewspaperDate(){
        return this.year + "/" + this.month + "/" +  this.day;
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getPrintableNewsID(){
        return this.printableNewsID;
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getWebNewsURL(){
        return this.webNewsURL;
    }
}
