/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net.Modelers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alvaro
 */
public class PathHandler {
    
    // mode = {"PrintableNews" | "Newspaper" | "Newspapers" | "SingleWebNews" 
    //       | "WebNews" | "Journalist" | "Journalists" | "CreateJournalist" 
    //       | "CreateNewspaper" | "CreatePrintableNews" | "CreateWebNews" 
    //       | "Protected" | "null"}
    
    private String path, mode = null; 
    
    // Patterns
    
    private final String printableNewsPattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/";
    private final String newspaperPattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/";
    private final String webNewsPattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/";
    private final String journalistPattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/";
    private final String directoryPattern = "^\\/([^\\/]+)\\/";
    
    // Directories
    
    private String year, month, day, printableNewsID, webNewsURL, journalistID;
    
    
    /**
     * TODO JavaDoc
     * @param path 
     */
    public PathHandler(String path){
        
        this.path = path;
        
        // Filtramos y clasificamos segun lo que pide
        
        Matcher matcher = null;
        if (Pattern.compile("^\\/newspapers\\/").matcher(this.path).find()) {
            
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
        else if(Pattern.compile("^\\/webnews\\/").matcher(this.path).find()){

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
        else if (Pattern.compile("^\\/journalists\\/").matcher(this.path).find()){

            // Pide algo del arbol de Journalist

            matcher = Pattern.compile(this.journalistPattern).matcher(this.path);
            if (matcher.find()) {

                // Pide un Journalist

                this.journalistID = matcher.group(2);

                this.mode = "Journalist";
            }
            else{

                matcher = Pattern.compile(this.directoryPattern).matcher(this.path);
                if (matcher.find()) {

                    // Pide los Journalists

                    this.mode = "Journalists";
                }
            }
        }
        else if (Pattern.compile("^\\/create\\/").matcher(this.path).find()){

            // Pide algo del arbol de Create

            if (Pattern.compile("^\\/create\\/journalist\\/").matcher(this.path).find()) {

                // Pide crear un Journalist

                this.mode = "CreateJournalist";
            }
            else if (Pattern.compile("\\/create\\/newspaper\\/").matcher(this.path).find()) {

                // Pide crear un Newspaper

                this.mode = "CreateNewspaper";
            }
            else if (Pattern.compile("^\\/create\\/printablenews\\/").matcher(this.path).find()) {

                // Pide crear una PrintableNews

                this.mode = "CreatePrintableNews";
            }
            else if (Pattern.compile("^\\/create\\/webnews\\/").matcher(this.path).find()) {

                // Pide crear una WebNews

                this.mode = "CreateWebNews";
            }
        }
        else if (Pattern.compile("^\\/templates\\/").matcher(this.path).find()){
            
            // Directorio protegido
            
            this.mode = "Protected";
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
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getJournalistID(){
        return this.journalistID;
    }
}
