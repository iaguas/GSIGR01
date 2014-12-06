/* 
 * Práctica 05 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.WebServer.Modelers;

import GSILib.BModel.Newspaper;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BSystem.BusinessSystem;
import GSILib.BTesting.UselessDataTesting;
import static java.lang.System.exit;

/**
 *
 * @author Alvaro
 */
public class ContentHandler {
    
    private PathHandler pathHandler;
    private BusinessSystem bs;
    private WebPage webPage;
    
    private int status = 200;
    
    public ContentHandler(String path){
        
        this.pathHandler = new PathHandler(path);
        this.bs = UselessDataTesting.getTestingBusinessSystem();
        
        if(this.pathHandler.getMode() == null){
            
            // El cliente pide un fichero local
            
            this.webPage = new WebPage("Has pedido un archivo", "Pero aun no servimos archivos");
   
        }
        else{
            
            // El cliente pide una pagina virtual
            
            if (this.pathHandler.getMode().equals("PrintableNews")){

                // El cliente pide una PrintableNews
                
                PrintableNews printableNews = this.bs.getPrintableNews(Integer.parseInt(this.pathHandler.getPrintableNewsID()));

                this.webPage = new WebPage(printableNews.getHeadline(), printableNews.getHTMLBody());
            }
            else if (this.pathHandler.getMode().equals("Newspaper")){

                // El cliente pide un Newspaper

                Newspaper newspaper = this.bs.getNewspaper(this.pathHandler.getNewspaperDate());

                if (newspaper != null){

                    // El newspaper exite

                    this.webPage = new WebPage("Newspaper | " + newspaper.getDate(), newspaper.getHTMLBody());
                }
                else{

                    // 404
                }
            }
            else if(this.pathHandler.getMode().equals("Newspapers")){

                this.webPage = new WebPage("Has pedido los Newspapers", "Pero aun no servimos Newspapers");

                // El cliente pide los Newspapers


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
    public int getStatus(){
        return this.status;
    }
}
