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
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This is the class PrintableNews.
 * Is a type of notice VisualNews which have pictures, reviewers adding to a 
 * headline, title and author.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class PrintableNews extends VisualNews implements XMLRepresentable, Serializable{
    
    // Atributo de la clase.
    private List<Journalist> reviewers = new ArrayList<>(); // Revisores de la noticia.
    
    /**
     * Class constructor that makes an object with headline, body and author.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     */
    public PrintableNews(String headline, String body, Journalist journalist) {
        // Llamamos al constructor de la superclase.
        super(headline, body, journalist);
    }
    
    /**
     * Constructor which obtains Element type object from and XML Document containing "PrintableNews"
     * tagged nodes, which are PrintableNews type objects; inherits VisualNews attributes.
     * @param printableNewsFromXML XML document to parse, which contains nodes 
     * corresponding to the class PrintableNews
     * @throws SAXException exception derived from XML file reading
     */
    public PrintableNews(String printableNewsFromXML) throws SAXException{
        
        // Creamos una VisualNews nula
        
        super();
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler(printableNewsFromXML);
        
        Element xmlPrintableNews = (Element) xml.engine.getElementsByTagName("PrintableNews").item(0);
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlPrintableNews);
    }
    
    /**
     * Constructor which obtains the attribute values from Element type object
     * extracted from and XML file containing PrintableNews; inherits VisualNews attributes.
     * @param xmlPrintableNews Element type object which contains useful data
     */
    public PrintableNews(Element xmlPrintableNews){
        
        // Creamos una VisualNews nula
        
        super();
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlPrintableNews);
         
    }
    
    /**
     * Constructor which obtains the attribute values from Element type object
     * extracted from and XML file containing PrintableNews, and associates the
     * desired Journalist to associate.
     * @param xmlPrintableNews Element type object which contains useful data
     * @param journalist Journalist to associate to PrintableNews
     */
    public PrintableNews(Element xmlPrintableNews, Journalist journalist){
        
        // Creamos una VisualNews nula
        
        super();
        
        // VisualNews rellena sus datos
        
        super.loadFromElement(xmlPrintableNews, journalist);
         
    }
    
    /**
     * Imports list of reviewers and journalists from the Element gathered from 
     * an XML Document, which are asociated to each PrintableNews
     * @param xmlPrintableNews Element type containing "PrintableNews" tagged nodes
     */
    protected void loadFromElement(Element xmlPrintableNews){
        
        // VisualNews rellena sus datos
        
        super.loadFromElement(xmlPrintableNews);
        
        // PrintableNews rellena sus datos
        
        NodeList reviewersNodes = ((Element) xmlPrintableNews.getElementsByTagName("Reviewers").item(0)).getElementsByTagName("Journalist");

        for (int i = 0; i < reviewersNodes.getLength(); i++) {
            Node reviewerNode = reviewersNodes.item(i);
            
            this.reviewers.add(new Journalist((Element) reviewerNode));
            
        } 
    }
    
    /**
     * Adds a reviewer (journalist) to the associated printable news
     * @param journalist the desired reviewer to add
     * @return true if the reviewer isn't added to the printable news yet, false otherwise.
     */
    public boolean addReviewer(Journalist journalist){
        // Comprobamos que el periodista no esté de autor y lo añadimos como revisor.
        if (! this.journalists.contains(journalist)){
            return this.reviewers.add(journalist);
        }
        return false;
    }
    
    /**
     * Removes a reviewer (journalist) from the associated printable news
     * @param journalist the existing desired reviewer to remove from printable news
     * @return true if journalist removed correctly, false otherwise.
     */
    public boolean removeReviewer(Journalist journalist){
        // Eliminamos al revisor de la colección de revisores.
        return this.reviewers.remove(journalist);
    }
    
    /**
     * Gets a list of reviewer (journalists) from the associated printable news
     * @return the list of reviewers from the associated printable news
     */
    public Journalist[] getReviewers(){            
        // Devolvemos los revisores.
        if (this.reviewers.isEmpty())
            return null;
        return this.reviewers.toArray(new Journalist[this.reviewers.size()]);
    }
    
    /**
     * Helper method which creates a XML element <PrintableNews>
     * @return XML element snippet representing a printableNews
     */
    public Element getElement(XMLHandler xml){

        Element xmlPrintableNews = xml.engine.createElement("PrintableNews");

        // Para una raiz PrintableNews, introducimos su headline como atributo
        
        xmlPrintableNews.setAttribute("headline", this.getHeadline());

        // Para una raiz PrintableNews, introducimos su body como atributo
        
        xmlPrintableNews.setAttribute("body", this.getBody());
        
        // Para una raiz PrintableNews, introducimos otra raiz Journalist
        
        if (xml.storeMode.equals("relational")){
            
            // Para una raiz Journalist, introducimos su id como atributo
            
            Element xmlPrintableNewsJournalist = xml.engine.createElement("Journalist");
            xmlPrintableNewsJournalist.setAttribute("id", this.getAuthor().getId());
            xmlPrintableNews.appendChild(xmlPrintableNewsJournalist);
        }
        else if(xml.storeMode.equals("full")){
            
            // Para una raiz Teletype, introducimos otra raiz Journalist
            
            xmlPrintableNews.appendChild(this.getAuthor().getElement(xml));
        }
        else{
            System.err.print("unrecognized method");
        }
        
        // Para una raiz PrintableNews, introducimos otra raiz Reviewers
        
        Element xmlPrintableNewsReviewers = xml.engine.createElement("Reviewers");
        
        if (xml.storeMode.equals("relational")){
            for(Journalist reviewer : this.reviewers){
                
                // Para una raiz Reviewers, introducimos su id como atributo
                
                Element xmlPrintableNewsReviewer = xml.engine.createElement("Journalist");
                xmlPrintableNewsReviewer.setAttribute("id", reviewer.getId());
                xmlPrintableNewsReviewers.appendChild(xmlPrintableNewsReviewer);
            }
        }
        else if(xml.storeMode.equals("full")){
            for(Journalist reviewer : this.reviewers){
                
                // Para una raiz Reviewers, introducimos otra raiz Journalist
                
                xmlPrintableNewsReviewers.appendChild(reviewer.getElement(xml));
            }
        }
        else{
            System.err.print("unrecognized method");
        }
        xmlPrintableNews.appendChild(xmlPrintableNewsReviewers);
        
        // Para una raiz PrintableNews, introducimos otra raiz Pictures
        
        Element xmlPrintableNewsPictures = xml.engine.createElement("Pictures");
        
        if (xml.storeMode.equals("relational")){
            for(Picture picture : this.pictures){
                
                // Para una raiz Pictures, introducimos su url como atributo
                
                Element xmlPrintableNewsPicture = xml.engine.createElement("Picture");
                xmlPrintableNewsPicture.setAttribute("url", picture.getUrl());
                xmlPrintableNewsPictures.appendChild(xmlPrintableNewsPicture);
            }
        }
        else if(xml.storeMode.equals("full")){
            for(Picture picture : this.pictures){
                
                // Para una raiz Pictures, introducimos otra raiz Picture
                
                xmlPrintableNewsPictures.appendChild(picture.getElement(xml));
            }
        }
        else{
            System.err.print("unrecognized method");
        }
        xmlPrintableNews.appendChild(xmlPrintableNewsPictures);
        
        return xmlPrintableNews;
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
    
    public void copyValuesFrom(PrintableNews newPritableNews){
        if (newPritableNews.getReviewers() != null)
            this.reviewers.addAll(Arrays.asList(newPritableNews.getReviewers()));
        super.copyValuesFrom(newPritableNews);
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param pn a printablenews
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(PrintableNews pn){
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(pn.getId());
    }
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos de la noticia imprimible.
        return "+ PrintableNews ID: " + this.getId() + "\n"
                + "|- Headline: " + this.getHeadline() + "\n"
                + "|- Body: " + this.getBody() + "\n"
                + "|- Journalist: " + this.getAuthor() + "\n"
                + "|- Pictures" + this.pictures + "\n"
                + "|- Reviewers: " + this.reviewers + "\n";
    }
}
