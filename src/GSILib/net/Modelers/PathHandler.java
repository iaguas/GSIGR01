// ******************************** REVISADA **********************************
/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net.Modelers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This handler takes an URL and analize it, convertering it in an object.
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class PathHandler {
    
    // mode = {"PrintableNews" | "Newspaper" | "Newspapers" | "SingleWebNews" 
    //       | "WebNews" | "Journalist" | "Journalists" | "CreateJournalist" 
    //       | "CreateNewspaper" | "CreatePrintableNews" | "CreateWebNews" 
    //       | "JournalistToFile" | "NewspaperToFile" | "PrintableNewsToFile"
    //       | "WebNewsToFile" | "Protected" | "Teapot" | "null"}
    
    private final String path;
    private String mode = null; 
    
    // Patterns
    private final String printableNewsPattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/";
    private final String printableNewsToFilePattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/\\?format=([^\\/]+)";
    private final String newspaperPattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/";
    private final String newspaperToFilePattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/([^\\/]+)\\/\\?format=([^\\/]+)";
    private final String webNewsPattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/";
    private final String webNewsToFilePattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/\\?format=([^\\/]+)";
    private final String journalistPattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/";
    private final String journalistToFilePattern = "^\\/([^\\/]+)\\/([^\\/]+)\\/\\?format=([^\\/]+)";
    private final String directoryPattern = "^\\/([^\\/]+)\\/";
    
    // Paramethers
    private String year, month, day, printableNewsID, webNewsURL, journalistID, fileType;
    
    
    /**
     * Class constructor with web resource path parameter
     * @param path ask to server by user.
     */
    public PathHandler(String path){
        // Establecemos las variables de la clase.
        this.path = path;
    }
    
    
    /**
     * Creates, filters and classifies web resources upon their directory name, 
     * type, naming format, and data relative to each type
     */
    public void processPath(){
        // Filtramos y clasificamos segun lo que pide
        
        // Se nos pide la página principal.
        if(this.path.equals("/")){
            this.mode = "NewspapersIndex";
        }
        else{
            // Creamos el comprobador de expresiones regulares.
            Matcher matcher = null;
            // Pide algo del arbol de Newspaper
            if (Pattern.compile("^\\/newspapers\\/").matcher(this.path).find()) {

                // Comprobamos si pide una PrintableNews a fichero.
                matcher = Pattern.compile(this.printableNewsToFilePattern).matcher(this.path);
                if (matcher.find()) {
                    this.mode = "PrintableNewsToFile";
                    this.year = matcher.group(2);
                    this.month = matcher.group(3);
                    this.day = matcher.group(4);
                    this.printableNewsID = matcher.group(5);
                    this.fileType = matcher.group(6);
                }
                else{
                    // Comprobamos si pide un Newspaper a fichero.
                    matcher = Pattern.compile(this.newspaperToFilePattern).matcher(this.path);
                    if (matcher.find()) {
                        this.mode = "NewspaperToFile";
                        this.year = matcher.group(2);
                        this.month = matcher.group(3);
                        this.day = matcher.group(4);
                        this.fileType = matcher.group(5);
                    }
                    else{
                        // Comprobamos si pide una PrintableNews
                        matcher = Pattern.compile(this.printableNewsPattern).matcher(this.path);
                        if (matcher.find()) {
                            this.mode = "PrintableNews";
                            this.year = matcher.group(2);
                            this.month = matcher.group(3);
                            this.day = matcher.group(4);
                            this.printableNewsID = matcher.group(5);
                        }
                        else{
                            // Comprobamos si pide un Newspaper.
                            matcher = Pattern.compile(this.newspaperPattern).matcher(this.path);
                            if (matcher.find()) {
                                this.mode = "Newspaper";
                                this.year = matcher.group(2);
                                this.month = matcher.group(3);
                                this.day = matcher.group(4);
                            }
                            else{
                                // Comprobamos si pide los Newspapers
                                matcher = Pattern.compile(this.directoryPattern).matcher(this.path);
                                if (matcher.find()) {
                                    this.mode = "Newspapers";
                                }
                            }
                        }
                    }
                }
            }

            // Pide algo del arbol de WebNews
            else if(Pattern.compile("^\\/webnews\\/").matcher(this.path).find()){


                // Comprobamos si pide una WebNews a fichero.
                matcher = Pattern.compile(this.webNewsToFilePattern).matcher(this.path);
                if (matcher.find()) {
                    this.mode = "WebNewsToFile";
                    this.webNewsURL = matcher.group(2);
                    this.fileType = matcher.group(3);
                }
                else{
                    // Comprobamos si pide una WebNews
                    matcher = Pattern.compile(this.webNewsPattern).matcher(this.path);
                    if (matcher.find()) {
                        this.mode = "SingleWebNews";
                        this.webNewsURL = matcher.group(2);
                    }
                    else{
                        // Comprobamos si pide las WebNews.
                        matcher = Pattern.compile(this.directoryPattern).matcher(this.path);
                        if (matcher.find()) {
                            this.mode = "WebNews";
                        }
                    }
                }
            }

            // Pide algo del arbol de Journalist
            else if (Pattern.compile("^\\/journalists\\/").matcher(this.path).find()){
                // Comprobamos si pide un Journalist en fichero.
                matcher = Pattern.compile(this.journalistToFilePattern).matcher(this.path);
                if (matcher.find()) {
                    this.mode = "JournalistToFile";
                    this.journalistID = matcher.group(2);
                    this.fileType = matcher.group(3);
                }
                else{
                    // Comprobamos si pide un Journalist
                    matcher = Pattern.compile(this.journalistPattern).matcher(this.path);
                    if (matcher.find()) {
                        this.mode = "Journalist";
                        this.journalistID = matcher.group(2);
                    }
                    else{
                        // Comprobamos si pide la lista de Journalists.
                        matcher = Pattern.compile(this.directoryPattern).matcher(this.path);
                        if (matcher.find()) {
                            this.mode = "Journalists";
                        }
                    }
                }
            }

            // Pide algo del arbol de Create
            else if (Pattern.compile("^\\/create\\/").matcher(this.path).find()){
                // Comprobamos si pide crear un Journalist.
                if (Pattern.compile("^\\/create\\/journalist\\/").matcher(this.path).find()) {
                    this.mode = "CreateJournalist";
                }
                // Comprobamos si pide crear un Newspaper.
                else if (Pattern.compile("\\/create\\/newspaper\\/").matcher(this.path).find()) {
                    this.mode = "CreateNewspaper";
                }
                // Comprobamos si pide crear una PrintableNews
                else if (Pattern.compile("^\\/create\\/printablenews\\/").matcher(this.path).find()) {
                  this.mode = "CreatePrintableNews";
                }
                // Comprobamos si pide crear una WebNews.
                else if (Pattern.compile("^\\/create\\/webnews\\/").matcher(this.path).find()) {
                   this.mode = "CreateWebNews";
                }
            }

            // Pide algo del árbol Templates.
            else if (Pattern.compile("^\\/templates\\/").matcher(this.path).find()){
                this.mode = "Protected";
            }

            // Pide algo del árbol de Teapot.  
            else if(Pattern.compile("^\\/areyouateapot\\?").matcher(this.path).find()){
                // 418
                this.mode = "Teapot";
            }
        }
    }
    
    
    /**
     * Gets the path for a requested web resource
     * @return path asked.
     */
    public String getPath(){
        return this.path;
    }
    
    
    /**
     * Gets the action requested by the web client (mode)
     * @return mode of request.
     */
    public String getMode(){
        return this.mode;
    }
    
    
   /**
     * Gets the newspaper date by translating the naming format for Newspapers
     * @return a date of a news paper.
     */
    public String getNewspaperDate(){
        return this.year + "/" + this.month + "/" +  this.day;
    }
    
    
    /**
     * Gets a PrintableNews ID
     * @return printableNewsID printablenews ID requested
     */
    public String getPrintableNewsID(){
        return this.printableNewsID;
    }
    
    
    /**
     * Gets the URL of a requested WebNews
     * @return webNewsURL webnews URL requested
     */
    public String getWebNewsURL(){
        return this.webNewsURL;
    }
    
    
    /**
     * Gets a Journalist ID
     * @return journalistID journalist ID requested
     */
    public String getJournalistID(){
        return this.journalistID;
    }
    
    
    /**
     * Gets file type of the requested file
     * @return fileType fileType requested
     */
    public String getFileType(){
        return this.fileType;
    }
}
