/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net;

import GSILib.BModel.Newspaper;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.documents.visualNews.WebNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BSystem.BusinessSystem;
import GSILib.net.Message.Request;
import GSILib.net.Modelers.PathHandler;
import GSILib.net.Modelers.WebPage;
import java.io.File;
import java.io.IOException;
import static java.lang.System.exit;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Alvaro
 */
public class GetHandler {
    
    private PathHandler pathHandler;
    private WebPage webPage;
    
    private String localDir;
    private String status = "200 OK";
    private String contentType;
    
    public GetHandler(Request request, BusinessSystem bs, String localDir) throws IOException{
        
        this.pathHandler = new PathHandler(request.getPath());
        this.localDir = localDir;
        
        if(request.countLines() < 100){
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
                    this.webPage = new WebPage("404 Not Found", new File(this.localDir + "templates/errors/404.html"));
                }
            }
            else{

                // El cliente pide una pagina virtual
                
                if (this.pathHandler.getMode().equals("Protected")){
                    
                    // 403
                    
                    this.status = "403 Access Denied";
                    this.webPage = new WebPage("403 Access Denied", new File(this.localDir + "templates/errors/403.html"));
                    
                }
                else if (this.pathHandler.getMode().equals("PrintableNews")){

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
                        this.webPage = new WebPage("404 Not Found", new File(this.localDir + "templates/errors/404.html"));
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

                        // 404 - Not Found

                        this.status = "404 Not Found";
                        this.webPage = new WebPage("404 Not Found", new File(this.localDir + "templates/errors/404.html"));
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
                else if(this.pathHandler.getMode().equals("Journalist")){

                    // El cliente pide un Journalist

                    Journalist journalist = bs.findJournalist(this.pathHandler.getJournalistID());

                    if (journalist != null){

                        // EL Journalist existe

                        this.webPage = new WebPage("Journalist | " + journalist.getName(), journalist.getHTMLBody());

                        this.webPage.append("<hr><h3>Has written...</h3><br><ul>");

                        PrintableNews[] printableNews = bs.getPrintableNewsFromAuthor(journalist);

                        if(printableNews != null){
                            for(PrintableNews singlePrintableNews : printableNews){
                                this.webPage.append("<li><a href=\"/newspapers/0/0/0/" + singlePrintableNews.getId() + "/\">" + singlePrintableNews.getHeadline() + "</a>");
                            }
                        }
                        else{
                            this.webPage.append("nothing, fire him");
                        }
                        this.webPage.append("</ul>");
                    }
                    else{

                        // 404 - Not Found

                        this.status = "404 Not Found";
                        this.webPage = new WebPage("404 Not Found", new File(this.localDir + "templates/errors/404.html"));
                    }
                }
                else if(this.pathHandler.getMode().equals("Journalists")){

                    // El cliente pide los Journalists

                    String html = "<h2>Journalists</h2><hr><ul>";

                    Journalist[] journalists = bs.getJournalists();

                    if (journalists != null){
                        for (Journalist journalist : journalists){
                            html = html.concat("<li><a href=\" " + journalist.getId() + "/\">" + journalist.getName() + "</a></li>");
                        }
                    }
                    html = html.concat("</ul>");

                    this.webPage = new WebPage("Journalists", html);
                }
                else{
                    
                    // 501 - No Implementado
                    
                    this.status = "501 Not Implemented";
                    this.webPage = new WebPage("501 Not Implemented", new File(this.localDir + "templates/errors/501.html")); 
                }
            }
        }
        else{
            
            // Sintoma de que hemos tenido que dejar de escuchar
            
            this.status = "413 Request too long";
            this.webPage = new WebPage(new File(this.localDir + "templates/413.html"));
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
