/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.WebServer;

import GSILib.BModel.Newspaper;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.documents.visualNews.WebNews;
import GSILib.BSystem.BusinessSystem;
import GSILib.WebServer.Modelers.PathHandler;
import GSILib.WebServer.Modelers.WebPage;
import java.io.File;
import java.io.IOException;
import static java.lang.System.exit;

/**
 *
 * @author Alvaro
 */
public class ContentHandler {
    
    private PathHandler pathHandler;
    private WebPage webPage;
    
    private String localDir;
    private String status = "200 OK";
    private String contentType;
    
    public ContentHandler(String path, BusinessSystem bs, String localDir) throws IOException{
        
        this.pathHandler = new PathHandler(path);
        this.localDir = localDir;
        
        if(this.pathHandler.getMode() == null){
            
            // El cliente pide un fichero local
            
            File file = new File(this.localDir + this.pathHandler.getPath());
            
            if (file.isDirectory()){
                file = new File(this.localDir + this.pathHandler.getPath() + "index.html");
            }
            
            if (file.exists()){
                
                // El archivo existe
                
                this.webPage = new WebPage(file);
                this.contentType = this.guessContentType(this.pathHandler.getPath());
            }
            else{
                
                // 404
                
                this.status = "404 Not Found";
                this.webPage = new WebPage(new File(this.localDir + "templates/404.html"));
            }
   
        }
        else{
            
            // El cliente pide una pagina virtual
            
            if (this.pathHandler.getMode().equals("PrintableNews")){

                // El cliente pide una PrintableNews
                
                PrintableNews printableNews = bs.getPrintableNews(Integer.parseInt(this.pathHandler.getPrintableNewsID()));

                this.webPage = new WebPage(printableNews.getHeadline(), printableNews.getHTMLBody());
            }
            else if (this.pathHandler.getMode().equals("Newspaper")){

                // El cliente pide un Newspaper

                Newspaper newspaper = bs.getNewspaper(this.pathHandler.getNewspaperDate());

                if (newspaper != null){

                    // El Newspaper exite

                    this.webPage = new WebPage("Newspaper | " + newspaper.getDate(), newspaper.getHTMLBody());
                }
                else{
                    
                    // 404
                
                    this.status = "404 Not Found";
                    this.webPage = new WebPage(new File(this.localDir + "templates/404.html"));
                }
            }
            else if(this.pathHandler.getMode().equals("Newspapers")){

                // El cliente pide los Newspapers
                
                String html = "<h2>Newspapers</h2><hr><ul>";

                Newspaper[] newspapers = bs.getNewspapers();
                
                if (newspapers != null){
                    for (Newspaper newspaper : newspapers){
                        html = html.concat("<li><a href=\" " + newspaper.getDate() + "/\">" + newspaper.getDate() + "</a></li>");
                    }
                }
                html = html.concat("</ul>");
                
                this.webPage = new WebPage("Newspapers", html);
            }
            else if(this.pathHandler.getMode().equals("SingleWebNews")){

                // El cliente pide una WebNews
                
                WebNews webNews = bs.getWebNews(this.pathHandler.getWebNewsURL());

                if (webNews != null){

                    // La WebNews exite

                    this.webPage = new WebPage("WebNews | " + webNews.getHeadline(), webNews.getHTMLBody());
                }
                else{

                    // 404
                
                    this.status = "404 Not Found";
                    this.webPage = new WebPage(new File(this.localDir + "templates/404.html"));
                }
                
            }
            else if(this.pathHandler.getMode().equals("WebNews")){

                // El cliente pide las WebNews

                String html = "<h2>WebNews</h2><hr><ul>";

                WebNews[] webNews = bs.getWebNews();
                
                if (webNews != null){
                    for (WebNews singleWebNews : webNews){
                        html = html.concat("<li><a href=\" " + singleWebNews.getUrl() + "/\">" + singleWebNews.getHeadline() + "</a></li>");
                    }
                }
                html = html.concat("</ul>");
                
                this.webPage = new WebPage("WebNews", html);
            }
            else{
                System.err.println("El gestor de contenido encontró un error");
                exit(0);
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
    public WebPage getWebPage(){
        return this.webPage;
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getStatus(){
        return this.status;
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getContentType(){
        return this.contentType;
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    private String guessContentType(String path){
        
        if (path.endsWith(".html") || path.endsWith(".htm") || path.endsWith("/")) 
            return "text/html";
        else if (path.endsWith(".txt") || path.endsWith(".java")) 
            return "text/plain";
        else if (path.endsWith(".gif")) 
            return "image/gif";
        else if (path.endsWith(".class"))
            return "application/octet-stream";
        else if (path.endsWith(".jpg") || path.endsWith(".jpeg"))
            return "image/jpeg";
        else if (path.endsWith(".css"))
            return "text/css";
        else if (path.endsWith(".js"))
            return "text/javascript";
        else    
            return "text/plain";
    }
}
