/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel.documents.visualNews;

import GSILib.BModel.documents.VisualNews;
import GSILib.BModel.workers.Journalist;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * This is the class WebNews.
 * This is a type of notice VisualNews which is identify by an URL and have a 
 * headline, body and author.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class WebNews extends VisualNews{
    // Atributos de la clase.
    private String url; // URL identificable de la clase.
    private List<String> keywords = new ArrayList<>(); // Palabras clave identificables.
    private org.w3c.dom.Document xml;
    private Object XMLStoreMode;
    
    /**
     * Class constructor that makes an object with headline, body, author and URL.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     * @param url URL that's a unique identifier of the notice.
     */
    public WebNews(String headline, String body, Journalist journalist, String url) {
        // Llamamos al constructor de la superclase.
        super(headline, body, journalist);
        // Añadimos l
        this.url = url;
    }
    
    /**
     * Adds a key word to the list of the associated WebNews
     * @param keyword key word to input
     * @return true if key word is smaller than 6 characters, and is added to 
     * the associated WebNews correctly, false otherwise.
     */ 
    public boolean addKeyWord(String keyword){
        // Comprobamos que la palabra tenga al menos 6 caracteres para añadirla.
        if (this.keywords.size() <= 6){
            this.keywords.add(keyword);
            // Advertimos que el añadido ha sido correcto.
            return true;
        }
        // Advertimos que el añadido no ha sido correcto.
        return false;
    }
    
    /**
     * Takes the URL of a webnews.
     * @return the url that identifies a webnews.
     */ 
    public List<String> getKeyWords(){
        // Devolvemos las palabras clave
        return this.keywords;        
    }
    
     /**
     * Takes the URL of a webnews.
     * @return the url that identifies a webnews.
     */ 
    public String getUrl(){
        // Devolvemos la URL
        return this.url;
    }
     
    @Override
    public String toString(){
        // Devolvemos un string con los datos de la noticia web.
        return "WebNews ID: " + this.getId() + "\n  Headline: " + 
                this.getHeadline() + "\n  Body: " + this.getBody() + 
                "\n  Journalist: " + this.getAuthor() + "\n  Pictures" + 
                this.getPictures() + "\n  URL: " + this.url + "\n  Keywords" +
                this.keywords;
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param wn a webnews
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(WebNews wn){        
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(wn.getId());
    }
    
     // TODO : JavaDoc 
    // Esta funcion simplemente calcula el arbol XML
    private void createXMLTree(){
        
        //get an instance of factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //get an instance of builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //create an instance of DOM
            this.xml = db.newDocument();
        }catch(ParserConfigurationException pce) {
            //dump it
            System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
            System.exit(1);
        }
        
        // Añadimos a la raiz un solo elemento

        this.xml.appendChild(this.getElement(this.xml));
    }
    
    // Método getElement para generar la estructura XML para cada instancia
     /**
     * Helper method which creates a XML element <WebNews>
     * @param xml
     * @return XML element snippet representing a journalist
     */
    
    /* TODO: BORRAR ESTE COMENTARIO AL TERMINAR 
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     * @param url URL that's a unique identifier of the notice.*/
    public Element getElement(org.w3c.dom.Document xml){

        Element xmlWebNews = xml.createElement("WebNews");

        // Para una raiz Webnews, introducimos otra raiz Headline
        
        Element xmlWebNewsHeadline = xml.createElement("Headline");
        Text webnewsHeadline = xml.createTextNode(this.getHeadline());
        xmlWebNewsHeadline.appendChild(webnewsHeadline);
        // Se añade una subraiz con appendChild
        xmlWebNews.appendChild(xmlWebNewsHeadline);

        // Para una raiz WebNews, introducimos otra raiz Body
        
        Element xmlWebNewsBody = xml.createElement("Body");
        Text webnewsBody = xml.createTextNode(this.getBody());
        xmlWebNewsBody.appendChild(webnewsBody);
        xmlWebNews.appendChild(xmlWebNewsBody);
        
        // Para una raiz WebNews, introducimos otra raiz Journalists
        //Element xmlWebNewsJournalists = xml.createElement("Journalists");
        
        //xmlWebNews.appendChild(xmlWebNewsJournalists
        if(this.XMLStoreMode.equals("relational")){
            // Para una raiz Journalist, introducimos su id como atributo
            Element xmlWebNewsJournalist = xml.createElement("Journalist");
            xmlWebNewsJournalist.setAttribute("id", this.getAuthor().getId());
        }
        else if(this.XMLStoreMode.equals("full")){
            
            // Para una raiz Teletype, introducimos otra raiz Journalist
            xmlWebNews.appendChild(this.getAuthor().getElement(xml));
        }
        else{
            System.err.print("unrecognized method");
        }
        
        
        //xmlWebNews.appendChild(this.getAuthor().getElement(xml));
        
        return xmlWebNews;
    }
}
