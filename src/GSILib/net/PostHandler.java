/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.net;

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
            
            this.webPage = new WebPage("Journalist Created", new File(this.localDir + "templates/forms/journalist.html"));
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
            
            this.webPage = new WebPage("Newspaper Created", new File(this.localDir + "templates/forms/newspaper.html"));
            this.webPage.replaceInTemplate("<!--alert-->", alert);
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
