// ******************************** REVISADA **********************************
/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.net;

import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.documents.visualNews.WebNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BSystem.BusinessSystem;
import GSILib.net.Message.Request;
import GSILib.net.Modelers.HTTPStatusHandler;
import GSILib.net.Modelers.WebPage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alvaro
 */
public class PostHandler {
    private final BusinessSystem bs;
    private final String localDir;
    private String path, status = "200 OK";
    private WebPage webPage;
    private final HTTPStatusHandler httpStatusHandler;
    
    /**
     * TODO: JavaDoc
     * @param bs 
     * @param localDir 
     */
    public PostHandler(BusinessSystem bs, String localDir){
        this.bs = bs;
        this.localDir = localDir; 
        this.httpStatusHandler = new HTTPStatusHandler(this, localDir);
    }
    
    /**
     * TODO: JAVADOC
     * @param request
     * @throws IOException 
     */
    public void processPostPetition(Request request) throws IOException{
        // Guardamos el parámetro que falta.
        this.path = request.getPath();
        
        // Tratamos los post
        String alert;
        Matcher matcher = null;
        // Pide crear un Journalist
        if (Pattern.compile("^\\/create\\/journalist\\/").matcher(this.path).find()) {
            // Extraemos todos los datos que se han pasado por POST.
            String id = request.getPOST("id");
            String name = request.getPOST("name");
            String birthDate = request.getPOST("birthDate");
            ArrayList<String> interests = new ArrayList();
            interests.addAll(Arrays.asList(request.getPOST("interests").split("\\s*,\\s*")));
            
            // Creamos el Journalist
            Journalist journalist = new Journalist(id, name, birthDate, interests);
            
            if (bs.addJournalist(journalist)){
                // Insertado con exito
                alert = "<div id=\"defaults-change-alert\" class=\"alert alert-success alert-dismissible\" role=\"alert\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button>\n" +
                        "   Journalist <a href=\"/journalists/" + id + "/\">creado</a> con exito.\n" +
                        "</div>";
            }
            else{
                // Error insertando el periodista.
                alert = "<div id=\"defaults-change-alert\" class=\"alert alert-danger alert-dismissible\" role=\"alert\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button>\n" +
                        "   Se produjo un error al crear/insertar el Journalist.\n" +
                        "</div>";
            }
            
            // Creamos la página web de respuesta.
            this.webPage = new WebPage("Create Journalist", new File(this.localDir + "templates/forms/journalist.html"));
            this.webPage.replaceInTemplate("<!--alert-->", alert);
        }
        
        // Pide crear un Newspaper
        else if (Pattern.compile("^\\/create\\/newspaper\\/").matcher(this.path).find()) {    
            // Extraemos los datos que se han pasado por POST.
            String date = request.getPOST("date");
            
            if (bs.createNewspaper(date)){
                // Insertado con exito
                alert = "<div id=\"defaults-change-alert\" class=\"alert alert-success alert-dismissible\" role=\"alert\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button>\n" +
                        "   Newspaper <a href=\"/newspapers/" + date + "/\">creado</a> con exito.\n" +
                        "</div>";
            }
            else{
                // Error
                alert = "<div id=\"defaults-change-alert\" class=\"alert alert-danger alert-dismissible\" role=\"alert\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button>\n" +
                        "   Se produjo un error al crear/insertar el Newspaper.\n" +
                        "</div>";
            }
            
            // Creamos la página web de respuesta.
            this.webPage = new WebPage("Create Newspaper", new File(this.localDir + "templates/forms/newspaper.html"));
            this.webPage.replaceInTemplate("<!--alert-->", alert);
        }
        
        // Pide crear una PrintableNews
        else if (Pattern.compile("^\\/create\\/printablenews\\/").matcher(this.path).find()) {
            // Extraemos los datos que se han pasado por POST.
            String headline = request.getPOST("headline");
            String body = request.getPOST("body");
            Journalist journalist = bs.findJournalist(request.getPOST("journalist"));
            String newspaperDate = request.getPOST("newspaper");
            
            // Creamos e insertamos la nueva PrintableNews 
            PrintableNews printableNews = new PrintableNews(headline, body, journalist);
            if (bs.insertNews(printableNews)){
                
                // Además, añadir a un Newspaper
                if (!newspaperDate.equals("0")){
                    bs.getNewspaper(newspaperDate).addNews(printableNews);
                    // Creada e Insertada con exito
                    alert = "<div id=\"defaults-change-alert\" class=\"alert alert-success alert-dismissible\" role=\"alert\">\n" +
                            "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button>\n" +
                            "   PrintableNews <a href=\"/newspapers/" + newspaperDate + "/" + printableNews.getId() + "/\">creada</a> e <a href=\"/newspapers/" + newspaperDate + "/\">insertada</a> con exito.\n" +
                            "</div>";
                }
                else{
                    // Creada con exito
                    alert = "<div id=\"defaults-change-alert\" class=\"alert alert-success alert-dismissible\" role=\"alert\">\n" +
                            "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button>\n" +
                            "   PrintableNews <a href=\"/newspapers/0/0/0/" + printableNews.getId() + "/\">creada</a> con exito.\n" +
                            "</div>";
                }
            }
            else{
                // Error la insertar
                alert = "<div id=\"defaults-change-alert\" class=\"alert alert-danger alert-dismissible\" role=\"alert\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button>\n" +
                        "   Se produjo un error al insertar la PrintableNews.\n" +
                        "</div>";
            }
            
            // Creamos la página web de respuesta.
            this.webPage = new WebPage("Create PrintableNews", new File(this.localDir + "templates/forms/printableNews.html"));
            this.webPage.replaceInTemplate("<!--alert-->", alert);
            this.webPage.replaceInTemplate("<!--journalistOptions-->", bs.getJournalistOptions());
            this.webPage.replaceInTemplate("<!--newspaperOptions-->", bs.getNewspaperOptions());
        }
        
        // Pide crear una WebNews
        else if (Pattern.compile("^\\/create\\/webnews\\/").matcher(this.path).find()) {
            // Extraemos los datos que se han pasado por POST.
            String headline = request.getPOST("headline");
            String body = request.getPOST("body");
            Journalist journalist = bs.findJournalist(request.getPOST("journalist"));
            ArrayList<String> keywords = new ArrayList();
            keywords.addAll(Arrays.asList(request.getPOST("keywords").split("\\s*,\\s*")));
            
            // Creamos la nueva WebNews
            WebNews webNews = new WebNews(headline, body, journalist);
            if (keywords != null){
                for (String keyword : keywords){
                    webNews.addKeyWord(keyword);
                }
            }
            
            // Insertamos la WebNews
            if (bs.insertNews(webNews)){
                // Creada con exito
                alert = "<div id=\"defaults-change-alert\" class=\"alert alert-success alert-dismissible\" role=\"alert\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button>\n" +
                        "   PrintableNews <a href=\"/webnews/" + webNews.getUrl() + "/\">creada</a> con exito.\n" +
                        "</div>";
            }
            else{
                // Error al insertar
                alert = "<div id=\"defaults-change-alert\" class=\"alert alert-danger alert-dismissible\" role=\"alert\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button>\n" +
                        "   Se produjo un error al insertar la WebNews.\n" +
                        "</div>";
            }
            
            // Creamos la página web de respuesta.
            this.webPage = new WebPage("Create WebNews", new File(this.localDir + "templates/forms/webNews.html"));
            this.webPage.replaceInTemplate("<!--alert-->", alert);
            this.webPage.replaceInTemplate("<!--journalistOptions-->", bs.getJournalistOptions());
        }
        
        // No hay más formas de responder.
        else{
            // 501 - No Implementado
            httpStatusHandler.showError(501);       
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
     * TODO: JavaDoc
     * @return 
     */
    public String getStatus(){
        return this.status;
    }
}
