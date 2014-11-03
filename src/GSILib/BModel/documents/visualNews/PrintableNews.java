/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel.documents.visualNews;

import GSILib.BModel.documents.VisualNews;
import GSILib.BModel.workers.Journalist;
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
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * This is the class PrintableNews.
 * Is a type of notice VisualNews which have pictures, reviewers adding to a 
 * headline, title and author.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class PrintableNews extends VisualNews implements XMLRepresentable{
    // XML Engine
    private org.w3c.dom.Document xml;
    // XML Store Mode
    static final String XMLStoreMode = "full"; // {"full","relational"}
    
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
    
    /**
     * Helper method which creates a XML element <PrintableNews>
     * @return XML element snippet representing a printableNews
     */
    public Element getElement(org.w3c.dom.Document xml){

        Element xmlPrintableNews = xml.createElement("PrintableNews");

        // Para una raiz PrintableNews, introducimos otra raiz Headline
        
        Element xmlPrintableNewsHeadline = xml.createElement("Headline");
        Text printableNewsHeadline = xml.createTextNode(this.getHeadline());
        xmlPrintableNewsHeadline.appendChild(printableNewsHeadline);
        xmlPrintableNews.appendChild(xmlPrintableNewsHeadline);

        // Para una raiz PrintableNews, introducimos otra raiz Body
        
        Element xmlPrintableNewsBody = xml.createElement("Body");
        Text printableNewsBody = xml.createTextNode(this.getBody());
        xmlPrintableNewsBody.appendChild(printableNewsBody);
        xmlPrintableNews.appendChild(xmlPrintableNewsBody);
        
        // Para una raiz PrintableNews, introducimos otra raiz Journalist
        
        if (this.XMLStoreMode.equals("relational")){
            
            // Para una raiz Journalist, introducimos su id como atributo
            Element xmlPrintableNewsJournalist = xml.createElement("Journalist");
            xmlPrintableNewsJournalist.setAttribute("id", this.getAuthor().getId());
            xmlPrintableNews.appendChild(xmlPrintableNewsJournalist);
        }
        else if(this.XMLStoreMode.equals("full")){
            
            // Para una raiz Teletype, introducimos otra raiz Journalist
            xmlPrintableNews.appendChild(this.getAuthor().getElement(xml));
        }
        else{
            System.err.print("unrecognized method");
        }
        
        // Para una raiz PrintableNews, introducimos otra raiz Reviewers
        
        Element xmlPrintableNewsReviewers = xml.createElement("Reviewers");
        
        if (this.XMLStoreMode.equals("relational")){
            for(Journalist reviewer : this.reviewers){
                // Para una raiz Journalist, introducimos su id como atributo
                Element xmlPrintableNewsReviewer = xml.createElement("Journalist");
                xmlPrintableNewsReviewer.setAttribute("id", reviewer.getId());
                xmlPrintableNewsReviewers.appendChild(xmlPrintableNewsReviewer);
            }
        }
        else if(this.XMLStoreMode.equals("full")){
            for(Journalist reviewer : this.reviewers){
                // Para una raiz Teletype, introducimos otra raiz Journalist
                xmlPrintableNewsReviewers.appendChild(reviewer.getElement(xml));
            }
        }
        else{
            System.err.print("unrecognized method");
        }
        xmlPrintableNews.appendChild(xmlPrintableNewsReviewers);
        
        return xmlPrintableNews;
    }
    
    /**
     * Gets this journalist in XML string.
     * @return the xml string of this teletype.
     */
    @Override
    public String toXML() {
        
        // Almacenar en una variable
        
        this.createXMLTree();
        
        Writer out = new StringWriter();
        try{
            OutputFormat format = new OutputFormat(this.xml);
            format.setIndenting(true);
            
            XMLSerializer serializerToString = new XMLSerializer(out , format);
            serializerToString.serialize(this.xml);

        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return out.toString();
    }
    
    /**
     * Stores this teletype in XML.
     * @return if the teletype was successfully stored into the xml file.
     */
    @Override
    public boolean saveToXML(File file) {
        
        // Almacenar en un fichero
        
        this.createXMLTree();
        
        try{
            
            OutputFormat format = new OutputFormat(this.xml);
            format.setIndenting(true);
            
            XMLSerializer serializerTofile = new XMLSerializer(
                new FileOutputStream(file)
                , format);
            serializerTofile.serialize(this.xml);
            
            return true;
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return false;
    }

    /**
     * Stores this teletype in XML.
     * @return if the teletype was successfully stored into the xml file.
     */
    @Override
    public boolean saveToXML(String filePath) {
       
        // Almacenar en un fichero
        
        this.createXMLTree();
        
        try{
            
            OutputFormat format = new OutputFormat(this.xml);
            format.setIndenting(true);
            XMLSerializer serializerTofile = new XMLSerializer(
                new FileOutputStream(
                    new File(filePath))
                , format);
            serializerTofile.serialize(this.xml);
            
            return true;
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        
        return false;
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
        return "PrintableNews ID: " + this.getId() + "\n  Headline: " + 
                this.getHeadline() + "\n  Body: " + this.getBody() + 
                "\n  Journalist: " + this.getAuthor() + "\n  Pictures" + 
                this.getPictures() + "\n  Reviewers: " + this.reviewers;
    }
    
    
    
}
