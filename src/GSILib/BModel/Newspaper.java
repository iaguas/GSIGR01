/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel;

import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.Serializable.XMLHandler;
import GSILib.Serializable.XMLRepresentable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This is the class Newspaper.
 * It represents a set of PrintableNews which from a newspaper. There is one by day.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Newspaper implements XMLRepresentable{
    
    // Atributos de la clase.
    private Date date; // Fecha de publicación del periodico.
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
    private List<PrintableNews> news = new ArrayList<>(); // Lista de noticias publicadas.
    
    /**
     * Empty class constructor.
     */
    public Newspaper(){ 
        this.date = new Date();
    }
    
    /**
     * Class constructor with date parameter
     * @param date Date type attribute for Newspaper
     */
    public Newspaper(Date date){
        this.date = date;
    }
    
    /**
     * Constructor which obtains Element type object from and XML Document containing "Newspaper"
     * tagged nodes, which are Newspaper type objects.
     * @param newspaperFromXML XML document to parse, which contains nodes corresponding to the class Newspaper
     * @throws SAXException exception derived from XML file reading
     * @throws java.text.ParseException exception derived from parsing.
     */
    public Newspaper(String newspaperFromXML) throws SAXException, ParseException{
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler(newspaperFromXML);
        
        Element xmlNewspaper = (Element) xml.engine.getElementsByTagName("Newspaper").item(0);
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlNewspaper);
    }
    
    /**
     * Imports list of Newspapers from the Element gathered from an XML Document
     * containing Newspaper node list.
     * @param xmlNewspaper Element type containing "Newspaper" tagged nodes
     * @throws java.text.ParseException exception derived from parsing.
     */
    public Newspaper(Element xmlNewspaper) throws ParseException{
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlNewspaper);
         
    }
    
    /**
     * Lowest rank method which obtains the atribute values for Newspaper from an
     * XML Element. 
     * @param xmlNewspaper Element type which contains useful data (dates and a 
     * list of PrintableNews)
     * @throws java.text.ParseException exception derivated from parsing.
     */
    protected void loadFromElement(Element xmlNewspaper) throws ParseException{
        
        // Picture rellena sus datos
        
        this.date = this.simpleDateFormat.parse(xmlNewspaper.getAttribute("date"));
        
        // Crea las PrintableNews y las añade
        
        NodeList printableNewsNodes = xmlNewspaper.getElementsByTagName("PrintableNews");

        for (int i = 0; i < printableNewsNodes.getLength(); i++) {
            Node printableNewsNode = printableNewsNodes.item(i);
            
            this.news.add(new PrintableNews((Element) printableNewsNode));
            
        }
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
    public String getDate(){
        
        return this.simpleDateFormat.format(this.date);
    }
    
    /**
     * Helper method which creates a XML element "Newspaper".
     * @param xml XML handler for the system.
     * @return XML element snippet representing a newspaper
     */
    public Element getElement(XMLHandler xml){

        Element xmlNewspaper = xml.engine.createElement("Newspaper");

        // Para una raiz Newspaper, introducimos su date como atributo
        
        xmlNewspaper.setAttribute("date", this.getDate());
        
        if (xml.storeMode.equals("relational")){
            for(PrintableNews printableNews : this.getPrintableNews()){
                
                // Para una raiz Newspaper, introducimos otra raiz PrintableNews
                
                Element xmlNewspaperPrintableNews = xml.engine.createElement("PrintableNews");
                xmlNewspaperPrintableNews.setAttribute("id", printableNews.getId().toString());
                xmlNewspaper.appendChild(xmlNewspaperPrintableNews);
            }
        }
        else if(xml.storeMode.equals("full")){
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
    
    /**
     * Creates an HTML template with the representation of a Newspaper
     * @return html fragment with the listing of PrintableNews contained in a Newspaper
     */
    public String getHTMLBody(){
        
        String html = "<div class=\"panel panel-default\">\n" +
                      "     <div class=\"panel-heading\">\n" +
                      "         <h3 class=\"panel-title\">Newspaper - " + this.getDate() + "</h3>\n" +
                      "     </div>\n" +
                      "     <div class=\"list-group\">";
        
        for(PrintableNews printableNews : this.news){
            html = html.concat("<a class=\"list-group-item\" href=\" " + printableNews.getId() + "/\">" + printableNews.getHeadline() + "</a>");
        }
        
        html = html.concat(  "     </div>" +
                             "     <div class=\"panel-footer\">\n" +
                             "         <div class=\"row\">\n" +
                             "             <div class=\"col-md-12\">\n" +
                             "                 <span class=\"pull-left\">\n" +
                             "                     <a href=\"./?format=xml\" class=\"btn btn-primary btn-xs\" role=\"button\">XML</a>\n" +
                             "                     <a href=\"./?format=json\" class=\"btn btn-primary btn-xs\" role=\"button\">JSON</a>\n" +
                             "                 </span>\n" +
                             "             </div>\n" +
                             "         </div>\n" +
                             "     </div>\n" +
                             " </div>");
            
        return html;
    }
    
    /**
     * Newspaper to JSON object parser
     * @return json JSON object containing a Newspaper
     * @throws JSONException exception derived from JSON inputing
     */
    public JSONObject getJSONObject() throws JSONException{
        
        JSONObject json = new JSONObject();
        
        json.put("date", this.getDate());
        List<JSONObject> jsonPrintableNews = new ArrayList();

        for(PrintableNews printableNews : this.news){
            jsonPrintableNews.add(printableNews.getJSONObject());
        }
        
        json.put("news", new JSONArray(jsonPrintableNews));
        
        return json;
    }
}
