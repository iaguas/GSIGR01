/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel.documents.visualNews;

import GSILib.BModel.Picture;
import GSILib.BModel.documents.VisualNews;
import GSILib.BModel.workers.Journalist;
import GSILib.Serializable.XMLHandler;
import GSILib.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * This is the class WebNews.
 * This is a type of notice VisualNews which is identify by an URL and have a 
 * headline, body and author.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class WebNews extends VisualNews implements XMLRepresentable{
    // Atributos de la clase.
    private String url; // URL identificable de la clase.
    private List<String> keywords = new ArrayList<>(); // Palabras clave identificables.
    
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
     * Constructor which obtains Element type object from an XML Document containing "WebNews"
     * tagged nodes, which are WebNews type objects.
     * @param webNewsFromXML XML document to parse, which contains nodes 
     * corresponding to the class WebNews
     * @throws SAXException exception derived from XML file reading
     */
    public WebNews(String webNewsFromXML) throws SAXException{
        
        // Creamos una VisualNews nula
        
        super();
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler(webNewsFromXML);
        
        Element xmlWebNews = (Element) xml.engine.getElementsByTagName("WebNews").item(0);
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlWebNews);
    }
    
    /**
     * Constructor which imports a Element type object, which contains a 
     * WebNews type object data; inherits VisualNews object type's attributes.
     * @param xmlWebNews Element type object which contains useful data
     */
    public WebNews(Element xmlWebNews){
        
        // Creamos una VisualNews nula
        
        super();
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlWebNews);
         
    }
    
    /**
     * Lowest rank method which obtains the atribute values for WebNews from an
     * XML Element. 
     * @param xmlWebNews Element type which contains useful data (url and keyword(s))
     */
    public WebNews(Element xmlWebNews, Journalist journalist){
        
        // Creamos una VisualNews nula
        
        super();
        
        // VisualNews rellena sus datos
        
        super.loadFromElement(xmlWebNews, journalist);
         
    }
    
    /**
     * Imports list of url and list of keywords from the Element gathered from 
     * an XML Document, which are asociated to each WebNews
     * @param xmlWebNews Element type containing "WebNews" tagged nodes
     */
    protected void loadFromElement(Element xmlWebNews){
        
        // VisualNews rellena sus datos
        
        super.loadFromElement(xmlWebNews);
        
        // WebNews rellena sus datos
        
        this.url = xmlWebNews.getAttribute("url");
        
        NodeList keywordsNodes = ((Element) xmlWebNews.getElementsByTagName("keywords").item(0)).getElementsByTagName("keyword");

        for (int i = 0; i < keywordsNodes.getLength(); i++) {
            Node keywordNode = keywordsNodes.item(i);
            if (keywordNode.getNodeType() == Node.ELEMENT_NODE) {
                this.keywords.add(keywordNode.getTextContent());
            }
        }
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
    
    /**
     * Helper method which creates a XML element <WebNews>
     * @return XML element snippet representing a webNews
     */
    public Element getElement(XMLHandler xml){

        Element xmlWebNews = xml.engine.createElement("WebNews");

        // Para una raiz PrintableNews, introducimos su headline como atributo
        
        xmlWebNews.setAttribute("headline", this.getHeadline());

        // Para una raiz PrintableNews, introducimos su body como atributo
        
        xmlWebNews.setAttribute("body", this.getBody());
        
        // Para una raiz PrintableNews, introducimos su url como atributo
        
        xmlWebNews.setAttribute("url", this.getUrl());
        
        // Para una raiz WebNews, introducimos otra raiz Journalist
        
        if (xml.storeMode.equals("relational")){
            
            // Para una raiz Journalist, introducimos su id como atributo
            Element xmlWebNewsJournalist = xml.engine.createElement("Journalist");
            xmlWebNewsJournalist.setAttribute("id", this.getAuthor().getId());
            xmlWebNews.appendChild(xmlWebNewsJournalist);
        }
        else if(xml.storeMode.equals("full")){
            
            // Para una raiz Teletype, introducimos otra raiz Journalist
            xmlWebNews.appendChild(this.getAuthor().getElement(xml));
        }
        else{
            System.err.print("unrecognized method");
        }
        
        // Para una raiz WebNews, introducimos otra raiz Keywords
        
        Element xmlWebNewsKeywords = xml.engine.createElement("keywords");
        for(String keyword : this.keywords){
            
            // Para una raiz Keywords, introducimos las keyword
            
            Element xmlWebNewsKeyword = xml.engine.createElement("keyword");
            Text webNewsKeyword = xml.engine.createTextNode(keyword);
            xmlWebNewsKeyword.appendChild(webNewsKeyword);
            xmlWebNewsKeywords.appendChild(xmlWebNewsKeyword);
        }
        
        xmlWebNews.appendChild(xmlWebNewsKeywords);
        
        // Para una raiz WebNews, introducimos otra raiz Pictures
        
        Element xmlWebNewsPictures = xml.engine.createElement("Pictures");
        
        if (xml.storeMode.equals("relational")){
            for(Picture picture : this.getPictures()){
                
                // Para una raiz Pictures, introducimos su url como atributo
                
                Element xmlWebNewsPicture = xml.engine.createElement("Picture");
                xmlWebNewsPicture.setAttribute("url", picture.getUrl());
                xmlWebNewsPictures.appendChild(xmlWebNewsPicture);
            }
        }
        else if(xml.storeMode.equals("full")){
            for(Picture picture : this.getPictures()){
                
                // Para una raiz Pictures, introducimos otra raiz Picture
                
                xmlWebNewsPictures.appendChild(picture.getElement(xml));
            }
        }
        else{
            System.err.print("unrecognized method");
        }
        xmlWebNews.appendChild(xmlWebNewsPictures);
        
        return xmlWebNews;
    }
    
    /**
     * Gets this journalist in XML string.
     * @return the xml string of this journalist.
     */
    @Override
    public String toXML() {
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler();
        
        Writer out = new StringWriter();
        try{
            OutputFormat format = new OutputFormat(xml.engine);
            format.setIndenting(true);
            
            XMLSerializer serializerToString = new XMLSerializer(out , format);
            serializerToString.serialize(this.getElement(xml));

        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return out.toString();
    }
    
    /**
     * Stores this journalist in XML.
     * @return if the journalist was successfully stored into the xml file.
     */
    @Override
    public boolean saveToXML(File file) {
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler();
        
        try{
            
            OutputFormat format = new OutputFormat(xml.engine);
            format.setIndenting(true);
            
            XMLSerializer serializerTofile = new XMLSerializer(
                new FileOutputStream(file)
                , format);
            serializerTofile.serialize(this.getElement(xml));
            
            return true;
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return false;
    }

    /**
     * Stores this journalist in XML.
     * @return if the journalist was successfully stored into the xml file.
     */
    @Override
    public boolean saveToXML(String filePath) {
       
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler();
        
        try{
            
            OutputFormat format = new OutputFormat(xml.engine);
            format.setIndenting(true);
            XMLSerializer serializerTofile = new XMLSerializer(
                new FileOutputStream(
                    new File(filePath))
                , format);
            serializerTofile.serialize(this.getElement(xml));
            
            return true;
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return false;
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
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos de la noticia web.
        return "+ WebNews ID: " + this.getId() + "\n"
                + "|- Headline: " + this.getHeadline() + "\n"
                + "|- Body: " + this.getBody() + "\n"
                + "|- Journalist: " + this.getAuthor() + "\n"
                + "|- Pictures" + this.pictures + "\n"
                + "|- URL: " + this.url + "\n"
                + "|- Keywords" + this.keywords + "\n";
    }
}
