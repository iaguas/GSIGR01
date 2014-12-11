/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.net;

import GSILib.BModel.Newspaper;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.documents.visualNews.WebNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BSystem.BusinessSystem;
import GSILib.net.Message.Request;
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
    
    private String path, localDir, status = "200 OK";
    private WebPage webPage;
    
    /**
     * TODO: JavaDoc
     * @param request
     * @param bs 
     */
    public PostHandler(Request request, BusinessSystem bs, String localDir) throws IOException{
        
        this.path = request.getPath();
        this.localDir = localDir; 
        
        // Tratamos los post
        
        String alert = null;
        Matcher matcher = null;
        if (Pattern.compile("^\\/create\\/journalist\\/").matcher(this.path).find()) {
            
            // Pide crear un Journalist
            
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
                
                // Error
                
                alert = "<div id=\"defaults-change-alert\" class=\"alert alert-danger alert-dismissible\" role=\"alert\">\n" +
                        "   <button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span></button>\n" +
                        "   Se produjo un error al crear/insertar el Journalist.\n" +
                        "</div>";
            }
            
            this.webPage = new WebPage("Create Journalist", new File(this.localDir + "templates/forms/journalist.html"));
            this.webPage.replaceInTemplate("<!--alert-->", alert);
        }
        else if (Pattern.compile("^\\/create\\/newspaper\\/").matcher(this.path).find()) {
        
            // Pide crear un Newspaper
            
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
            
            this.webPage = new WebPage("Create Newspaper", new File(this.localDir + "templates/forms/newspaper.html"));
            this.webPage.replaceInTemplate("<!--alert-->", alert);
        }
        else if (Pattern.compile("^\\/create\\/printablenews\\/").matcher(this.path).find()) {
            
            // Pide crear una PrintableNews
            
            String headline = request.getPOST("headline");
            String body = request.getPOST("body");
            Journalist journalist = bs.findJournalist(request.getPOST("journalist"));
            String newspaperDate = request.getPOST("newspaper");
            
            PrintableNews printableNews = new PrintableNews(headline, body, journalist);
            
            if (bs.insertNews(printableNews)){
                
                if (!newspaperDate.equals("0")){
                
                    // Añadir a un Newspaper

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
            
            this.webPage = new WebPage("Create PrintableNews", new File(this.localDir + "templates/forms/printableNews.html"));
            this.webPage.replaceInTemplate("<!--alert-->", alert);
            this.webPage.replaceInTemplate("<!--journalistOptions-->", bs.getJournalistOptions());
            this.webPage.replaceInTemplate("<!--newspaperOptions-->", bs.getNewspaperOptions());
        }
        else if (Pattern.compile("^\\/create\\/webnews\\/").matcher(this.path).find()) {
            
            // Pide crear una WebNews
            
            String headline = request.getPOST("headline");
            String body = request.getPOST("body");
            Journalist journalist = bs.findJournalist(request.getPOST("journalist"));
            ArrayList<String> keywords = new ArrayList();
            keywords.addAll(Arrays.asList(request.getPOST("keywords").split("\\s*,\\s*")));
            
            WebNews webNews = new WebNews(headline, body, journalist);
            if (keywords != null){
                for (String keyword : keywords){
                    webNews.addKeyWord(keyword);
                }
            }
             
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
            
            this.webPage = new WebPage("Create WebNews", new File(this.localDir + "templates/forms/webNews.html"));
            this.webPage.replaceInTemplate("<!--alert-->", alert);
            this.webPage.replaceInTemplate("<!--journalistOptions-->", bs.getJournalistOptions());
        }
        else{
            
            // 501 - No Implementado
                    
            this.status = "501 Not Implemented";
            this.webPage = new WebPage("501 Not Implemented", new File(this.localDir + "templates/errors/501.html")); 
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
}
