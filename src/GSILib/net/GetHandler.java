// ******************************** REVISADA **********************************
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
import GSILib.net.Modelers.HTTPStatusHandler;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;

/**
 *
 * @author Alvaro
 */
public class GetHandler {
    private final BusinessSystem bs;
    private PathHandler pathHandler;
    private WebPage webPage;
    private final HTTPStatusHandler httpStatusHandler;
    
    private final String localDir;
    private String status = "200 OK";
    private String contentType;
    
    /**
     * TODO JAVADOC:
     * @param bs
     * @param localDir 
     */
    public GetHandler(BusinessSystem bs, String localDir){
        // Crear parámetros de la clase.
        this.bs = bs;
        this.localDir = localDir;
        this.httpStatusHandler = new HTTPStatusHandler(this, localDir);
    }
    
    
    /** 
     * TODO: JAVADOC
     * @param request
     * @throws IOException
     * @throws JSONException 
     */
    public void processGetPetition(Request request) throws IOException, JSONException{
        this.pathHandler = new PathHandler(request.getPath());
        this.pathHandler.processPath();
        if (request.countLines() < 100){
            if (this.pathHandler.getMode() == null){

                // El cliente pide un fichero local
                File file = new File(this.localDir + this.pathHandler.getPath());

                if (file.isDirectory()){
                    file = new File(this.localDir + this.pathHandler.getPath() + "index.html");
                }
                
                // El archivo existe
                if (file.exists()){
                    this.webPage = new WebPage(file);
                    this.contentType = this.guessContentType(this.pathHandler.getPath());
                }
                else{
                    // 404
                    httpStatusHandler.showError(404);
                }
            }
            // El cliente pide una pagina virtual
            else{
                if (this.pathHandler.getMode().equals("Protected")){   
                    // 403
                    httpStatusHandler.showError(404);
                }
                // El cliente pide una PrintableNews
                else if (this.pathHandler.getMode().equals("PrintableNews")){
                    PrintableNews printableNews = bs.getPrintableNews(Integer.parseInt(this.pathHandler.getPrintableNewsID()));
                    this.webPage = new WebPage(printableNews.getHeadline(), printableNews.getHTMLBody());
                }
                // El cliente pide una PrintableNews serializada
                else if (this.pathHandler.getMode().equals("PrintableNewsToFile")){
                    PrintableNews printableNews = bs.getPrintableNews(Integer.parseInt(this.pathHandler.getPrintableNewsID()));
                    
                    if (printableNews != null){
                        // El cliente pide un XML
                        if (this.pathHandler.getFileType().equals("xml")){
                            this.webPage = new WebPage(printableNews.toXML());
                            this.webPage.setContent("application/xml");
                            System.out.println(this.webPage);
                        }
                        // El cliente pide un JSON
                        else if (this.pathHandler.getFileType().equals("json")){
                            this.webPage = new WebPage(printableNews.getJSONObject().toString(4));
                            this.webPage.setContent("application/json");
                        }
                        else{
                            // 415 - Unsuported Type of File
                            httpStatusHandler.showError(415);
                        }
                    }
                    else{
                        // 404 - Not found
                        httpStatusHandler.showError(404);
                    }
                }
                // El cliente pide un Newspaper
                else if (this.pathHandler.getMode().equals("Newspaper")){
                    Newspaper newspaper = bs.getNewspaper(this.pathHandler.getNewspaperDate());
                    // El Newspaper exite
                    if (newspaper != null){
                        this.webPage = new WebPage("Newspaper | " + newspaper.getDate(), newspaper.getHTMLBody());
                    }
                    else{
                        // 404 - Not Found
                        httpStatusHandler.showError(404);
                    }
                }
                // El cliente pide los Newspapers
                else if (this.pathHandler.getMode().equals("Newspapers")){
                    String html = " <div class=\"list-group\"><li class=\"list-group-item disabled\">Newspapers</li>";

                    Newspaper[] newspapers = bs.getNewspapers();

                    if (newspapers != null){
                        for (Newspaper newspaper : newspapers){
                            html = html.concat("<a class=\"list-group-item\" href=\" " + newspaper.getDate() + "/\">" + newspaper.getDate() + "</a>");
                        }
                    }
                    html = html.concat("</div>");

                    this.webPage = new WebPage("Newspapers", html);
                }
                // El cliente pide los Newspapers para la página principal
                else if (this.pathHandler.getMode().equals("NewspapersIndex")){
                    String html = " <div class=\"list-group\"><li class=\"list-group-item disabled\">Newspapers</li>";

                    Newspaper[] newspapers = bs.getNewspapers();

                    if (newspapers != null){
                        for (Newspaper newspaper : newspapers){
                            html = html.concat("<a class=\"list-group-item\" href=\"\\newspapers\\" + newspaper.getDate() + "/\">" + newspaper.getDate() + "</a>");
                        }
                    }
                    html = html.concat("</div>");

                    this.webPage = new WebPage("Newspapers", html);
                }
                // El cliente pide un Newspaper serializado
                else if (this.pathHandler.getMode().equals("NewspaperToFile")){
                    Newspaper newspaper = bs.getNewspaper(this.pathHandler.getNewspaperDate());
                    
                    if (newspaper != null){
                        // El cliente pide un XML
                        if (this.pathHandler.getFileType().equals("xml")){
                            this.webPage = new WebPage(newspaper.toXML());
                            this.webPage.setContent("application/xml");
                        }
                        // El cliente pide un JSON
                        else if (this.pathHandler.getFileType().equals("json")){
                            this.webPage = new WebPage(newspaper.getJSONObject().toString(4));
                            this.webPage.setContent("application/json");
                        }
                        else{
                            // 415 - Unsuported Type of File
                            this.status = "415 Unsuported Type of File";
                            this.webPage = new WebPage("415 Unsuported Type of File", new File(this.localDir + "templates/errors/415.html"));
                        }
                    }
                    else{
                        // 404 - Not found
                        httpStatusHandler.showError(404);
                    }
                }
                // El cliente pide una WebNews
                else if (this.pathHandler.getMode().equals("SingleWebNews")){
                    WebNews webNews = bs.getWebNews(this.pathHandler.getWebNewsURL());
                    // La WebNews exite
                    if (webNews != null){
                        this.webPage = new WebPage("WebNews | " + webNews.getHeadline(), webNews.getHTMLBody());
                    }
                    else{
                        // 404 - Not found
                        httpStatusHandler.showError(404);
                    }
                }
                // El cliente pide las WebNews
                else if (this.pathHandler.getMode().equals("WebNews")){
                    String html = " <div class=\"list-group\"><li class=\"list-group-item disabled\">WebNews</li>";

                    WebNews[] webNews = bs.getWebNews();

                    if (webNews != null){
                        for (WebNews singleWebNews : webNews){
                            html = html.concat("<a class=\"list-group-item\" href=\" " + singleWebNews.getUrl() + "/\">" + singleWebNews.getHeadline() + "</a>");
                        }
                    }
                    html = html.concat("</div>");

                    this.webPage = new WebPage("WebNews", html);
                }
                // El cliente pide una WebNews serializado
                else if (this.pathHandler.getMode().equals("WebNewsToFile")){
                    WebNews webNews = bs.getWebNews(this.pathHandler.getWebNewsURL());

                    if (webNews != null){
                        // El cliente pide un XML
                        if (this.pathHandler.getFileType().equals("xml")){
                            this.webPage = new WebPage(webNews.toXML());
                            this.webPage.setContent("application/xml");
                        }
                        // El cliente pide un JSON
                        else if (this.pathHandler.getFileType().equals("json")){
                            this.webPage = new WebPage(webNews.getJSONObject().toString(4));
                            this.webPage.setContent("application/json");
                        }
                        else{
                            // 415 - Unsuported Type of File
                            httpStatusHandler.showError(415);
                        }
                    }
                    else{
                        // 404 - Not found
                        httpStatusHandler.showError(404);
                    }
                }
                // El cliente pide un Journalist
                else if (this.pathHandler.getMode().equals("Journalist")){
                    Journalist journalist = bs.findJournalist(this.pathHandler.getJournalistID());
                    // El Journalist existe
                    if (journalist != null){
                        // Creamos la pagina con los datos del periodista.
                        this.webPage = new WebPage("Journalist | " + journalist.getName(), journalist.getHTMLBody());
                        this.webPage.append("<div class=\"list-group\"><li class=\"list-group-item disabled\">Printable News authored</li>");
                        // Recogemos y procesamos todas las noticias del Periodista.
                        PrintableNews[] printableNews = bs.getPrintableNewsFromAuthor(journalist);
                        if(printableNews != null){
                            for(PrintableNews singlePrintableNews : printableNews){
                                this.webPage.append("<a class=\"list-group-item\" href=\"/newspapers/0/0/0/" + singlePrintableNews.getId() + "/\">" + singlePrintableNews.getHeadline() + "</a>");
                            }
                        }
                        else{
                            this.webPage.append("<li class=\"list-group-item danger\">There is no news for this Journalist, you should fire him</li>");
                        }
                        this.webPage.append("</div>");
                    }
                    else{
                        // 404 - Not found
                        httpStatusHandler.showError(404);
                    }
                }
                // El cliente pide los Journalists
                else if (this.pathHandler.getMode().equals("Journalists")){
                    String html = " <div class=\"list-group\"><li class=\"list-group-item disabled\">Journalists</li>";

                    Journalist[] journalists = bs.getJournalists();

                    if (journalists != null){
                        for (Journalist journalist : journalists){
                            html = html.concat("<a class=\"list-group-item\" href=\" " + journalist.getId() + "/\">" + journalist.getName() + "</a>");
                        }
                    }
                    html = html.concat("</div>");

                    this.webPage = new WebPage("Journalists", html);
                }
                // El cliente pide un Journalist serializado
                else if (this.pathHandler.getMode().equals("JournalistToFile")){
                    Journalist journalist = bs.findJournalist(this.pathHandler.getJournalistID());
                    // El cliente pide un XML
                    if (journalist != null){
                        if (this.pathHandler.getFileType().equals("xml")){
                            this.webPage = new WebPage(journalist.toXML());
                            this.webPage.setContent("application/xml");
                        }
                        // El cliente pide un JSON
                        else if (this.pathHandler.getFileType().equals("json")){
                            this.webPage = new WebPage(journalist.getJSONObject().toString(4));
                            this.webPage.setContent("application/json");
                        }
                        // El cliente pide una vCard
                        else if (this.pathHandler.getFileType().equals("vcard")){
                            this.webPage = new WebPage(journalist.getvCard());
                        }
                        else{
                            // 415 - Unsuported Type of File
                            httpStatusHandler.showError(415);
                        }
                    }
                    else{
                        // 404 - Not found
                        httpStatusHandler.showError(404);
                    }
                }
                // Create Journalist
                else if (this.pathHandler.getMode().equals("CreateJournalist")){
                    this.webPage = new WebPage("Create Journalist", new File(this.localDir + "templates/forms/journalist.html")); 
                }
                // Create Newspaper
                else if (this.pathHandler.getMode().equals("CreateNewspaper")){
                    this.webPage = new WebPage("Create Newspaper", new File(this.localDir + "templates/forms/newspaper.html")); 
                }
                // Create PrintableNews
                else if (this.pathHandler.getMode().equals("CreatePrintableNews")){
                    this.webPage = new WebPage("Create PrintableNews", new File(this.localDir + "templates/forms/printableNews.html")); 
                    this.webPage.replaceInTemplate("<!--journalistOptions-->", bs.getJournalistOptions());
                    this.webPage.replaceInTemplate("<!--newspaperOptions-->", bs.getNewspaperOptions());
                    
                }
                // Create WebNews
                else if (this.pathHandler.getMode().equals("CreateWebNews")){
                    this.webPage = new WebPage("Create WebNews", new File(this.localDir + "templates/forms/webNews.html")); 
                    this.webPage.replaceInTemplate("<!--journalistOptions-->", bs.getJournalistOptions());
                
                }
                else if (this.pathHandler.getMode().equals("Teapot")){
                    // 418 - Teapot
                    httpStatusHandler.showError(418);                
                }
                else{
                    // 501 - No Implementado
                    httpStatusHandler.showError(501);
                }
            }
        }
         // Sintoma de que hemos tenido que dejar de escuchar
        else{
            // 413 - Request too long
            httpStatusHandler.showError(413);
        }
    }
    
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public WebPage getWebPage(){
        return this.webPage;
    }
    
    
    /**
     * TODO: JAVADOC
     * @param wp 
     */
    public void setWebPage(WebPage wp){
        this.webPage = wp;
    }
    
    /**
     * TODO: JavaDoc
     * @return 
     */
    public String getStatus(){
        return this.status;
    }
    
    
    /**
     * TODO: JAVADOC
     * @param status 
     */
    public void setStatus(String status){
        this.status = status;
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
