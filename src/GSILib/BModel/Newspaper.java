/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel;

import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * This is the class Newspaper.
 * It represent a set of PrintableNews which form a newspaper. There is once by day.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Newspaper implements XMLRepresentable{
    
    // Atributos de la clase.
    private Date date = new Date(); // Fecha de publicación del periodico.
    private List<PrintableNews> news; // Lista de noticias publicadas.
    // XML Engine
    private org.w3c.dom.Document xml;
    // XML Store Mode
    static final String XMLStoreMode = "full"; // {"full","relational"}
    
    /**
     * Class constructor of a void newspaper.
     */
    public Newspaper(){
        // Creamos un periodico vacio.
        this.news = new ArrayList<>();
    }
    
    /**
     * Returns all the PrintableNews in the newspaper.
     * @return An array with the PrintableNews in the newspaper.
     */
    public PrintableNews[] getPrintableNews(){
        if(this.news.isEmpty())
            return null;
        return this.news.toArray(new PrintableNews[this.news.size()]);
    }
    
    /**
     * Adds a printable news to the associated newspaper
     * @param pn The printable news which are part of the newspaper.
     * @return true if printable news added correctly to newspaper, false otherwise.
     */
    public boolean addNews(PrintableNews pn){
        // Añadimos una noticia a la colección del periodico.
        return this.news.add(pn);
    }
    
    /**
     * Verifies whether a newspaper is publishable
     * @return true if the number of pages is at least 20, false otherwise.
     */
    public boolean isPublishable(){
        // Comprobamos si el periodico tiene al menos 20 noticas.
        return this.news.size() >= 20;
    }
    
    /**
     * Returns all the authors (journalists) of the news in the newspaper.
     * @return An array with the journalists who have written news in the newspaper.
     */
    public Journalist[] getAuthors(){
        // Creamos una tabla periodista y la dejamos a null por si no hubiera a quien meter.
        Journalist[] authorsOfANewspaper = null;
        // Creamos el íncide para recorrer la tabla.
        int nextIndex = 0;
        // Recorremos las noticias buscando a los periodistas que las escriben
        // Añadimos a estos a la tabla de periodistas.
        for (int i = 0; i < this.news.size(); i++){
            authorsOfANewspaper[nextIndex] = this.news.get(i).getAuthor();
            nextIndex++;
        }
        // Devolvemos la tabla que hemos creado.
        return authorsOfANewspaper;
    }
    
    /**
     * Returns publication date of a newspaper.
     * @return A date in which the newspaper has been published.
     */
    public Date getDate(){
        // Devuelve la fecha del periodico.
        return this.date;
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
     * Helper method which creates a XML element <Newspaper>
     * @return XML element snippet representing a newspaper
     */
    public Element getElement(org.w3c.dom.Document xml){

        Element xmlNewspaper = xml.createElement("Newspaper");

        // Para una raiz Newspaper, introducimos su date como atributo
        
        xmlNewspaper.setAttribute("date", this.getDate().toString());
        
        if (this.XMLStoreMode.equals("relational")){
            for(PrintableNews printableNews : this.getPrintableNews()){
                
                // Para una raiz Newspaper, introducimos otra raiz PrintableNews
                
                Element xmlNewspaperPrintableNews = xml.createElement("PrintableNews");
                xmlNewspaperPrintableNews.setAttribute("id", printableNews.getId().toString());
                xmlNewspaper.appendChild(xmlNewspaperPrintableNews);
            }
        }
        else if(this.XMLStoreMode.equals("full")){
            for(PrintableNews printableNews : this.news){

                // Para una raiz Newspaper, introducimos otra raiz PrintableNews

                xmlNewspaper.appendChild(printableNews.getElement(xml));
            }
        }
        else{
            System.err.print("unrecognized method");
        }
        
        return xmlNewspaper;
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
            
    @Override
    public String toString(){
        // Devolvemos un string con los datos del periodico.
        return "Newspaper date: " + this.date + "News: " + this.news;
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param n a newspaper.
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Newspaper n){
        // Comparamos y devolvemos si es el mismo periodico o no.
        return this.getDate().equals(n.getDate());
    }
}
