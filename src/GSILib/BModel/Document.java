/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel;

import GSILib.BModel.workers.Journalist;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This is the class Document. 
 * It represent the top of the herence tree of the documents in the system.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public abstract class Document implements Serializable{
    
    // Atributos de la clase
    private Integer id; // ID único.
    private String headline, body; // Titular y texto de la noticia.
    protected List<Journalist> journalists = new ArrayList(); // Lista de periodistas.
    private List<String> prizes = new ArrayList(); // Lista de premios.
    
    /**
     * Class constructor for a document with headline, body and author.
     * @param headline headline of the news that you want to save.
     * @param body all text of the news.
     * @param journalist worker who has written the news.
     */
    protected Document(String headline, String body, Journalist journalist){
        // Construimos la clase con los datos básicos de esta.
        this.headline = headline;
        this.body = body;
        this.journalists.add(journalist);
    }
    
    /**
     * Empty class constructor
     */
    protected Document(){
        
        // Constructor nulo
    }
    
    /**
     * Imports list of Journalists from the Element gathered from an XML Document
     * containing Journalist node list
     * @param xmlDocument Element type containing "Journalist" tagged nodes
     */ 
    protected void loadFromElement(Element xmlDocument){
        
        // Document rellena sus datos
        
        this.id = Integer.parseInt(xmlDocument.getAttribute("id"));
        this.headline = xmlDocument.getAttribute("headline");
        this.body = xmlDocument.getAttribute("body");
 
        // Crea los Journalists y los añade
        
        NodeList journalistsNodes = xmlDocument.getElementsByTagName("Journalist");

        for (int i = 0; i < journalistsNodes.getLength(); i++) {
            Node journalistNode = journalistsNodes.item(i);
            
            this.journalists.add(new Journalist((Element) journalistNode));
            
        } 
    }
    
    /**
     * Lowest rank method which obtains the atribute values for Document from an
     * XML Element, and adds the participating Journalists as well. 
     * @param xmlDocument Element type which contains useful data (headline and 
     * body)
     * @param journalist parsed Journalist type object ready to add to the system
     */
    protected void loadFromElement(Element xmlDocument, Journalist journalist){
        
        // Document rellena sus datos
        
        this.headline = xmlDocument.getAttribute("headline");
        this.body = xmlDocument.getAttribute("body");
 
        // Crea los Journalists y los añade
        
        this.journalists.add(journalist);
    }
    
    /**
     * Adds a journalist to the office.
     * @param jr The journalist to be added.
     * @return true if it was correctly added, false if it was null or already existing.
     */    
    public boolean addJournalist(Journalist jr){
        // Añadimos un periodista a los posibles autores de documentos.
        return this.journalists.add(jr);
    }
    
    /** 
     * Retrieves the prizes of a given document.
     * @return The array of the prizes. If it is empty, it will be null.
     */
    public String[] getPrizes(){
        // Devolvemos el array de premios.
        if(this.prizes.isEmpty())
            return null;
        return this.prizes.toArray(new String[this.prizes.size()]);
    }
    
    /**
     * Adds a string-represented prize to a given document, which can be a Teleprinter, WebNew or PrintableNew.
     * @param prize the prize the document was awarded with.
     * @return true if the Document was already stored and it was correctly listed, false otherwise.
     */
    public boolean addPrize(String prize){
        // Añadimos un premio a la colección del sistema.
        return this.prizes.add(prize);
    }
    
    /**
    * Deletes a prize that has been associated with a given Document
     * @param prize The prize to be deleted.
     * @return True if it was correctly deleted, false if it wasn't or did not exist.
     */    
    public boolean removePrize(String prize){
        // Eliminamos el premio de la colección del sistema.
        return this.prizes.remove(prize);
    }
    
    /**
     * Retrieves the author of a given news.
     * @return The journalist author of the document.
     */
    public Journalist getAuthor(){
        // Rescatamos un autor de un documento.
        return this.journalists.get(0);
    }
    
    /** 
     * Add an unique ID to a document
     * @param id The id itself
     */
    public void setId(Integer id){
        // Disponemos el ID para cada documento.
        this.id = id;
    }
    
    /**
     * Retrieves the ID of a given document.
     * @return The ID of a document.
     */
    public Integer getId(){
        // Recuperamos el ID de un documento.
        return this.id;
    }
    
    /**
     * Retrieves the headline of a given document.
     * @return The headline of a document.
     */
    public String getHeadline(){
        // Recuperamos el titular de un documento.
        return this.headline;
    }
    
    /**
     * Retrieves the body of a given document.
     * @return The body of a document.
     */
    public String getBody(){
        // Recuperamos el texto de un documento.
        return this.body;
    }
    
    /**
     * Copies name and birthdate from a Document to an associated new one.
     * @param newDocument original Document the fields are read from
     */
    protected void copyValuesFrom(Document newDocument){
        this.body = newDocument.getBody();
        this.headline = newDocument.getHeadline();
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param d a document.
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Document d){
        // Comparamos y devolvemos si es el mismo periodico o no.
        return this.getId().equals(d.getId());
    }
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos del documento.
        return "Document ID: " + this.getId() + "\n  Headline: " + this.getHeadline()
                + "\n  Body: " + this.getBody() + "\n  Journalist: " +
                this.getAuthor();    
    }
    
    /**
     * Document to JSON object parser
     * @return json JSON object containing a Document 
     * @throws JSONException exception derived from JSON inputing
     */
    public JSONObject getJSONObject() throws JSONException{
        
        JSONObject json = new JSONObject();
        
        json.put("body", this.body);
        json.put("headline", this.headline);
        
        List<JSONObject> jsonJournalists = new ArrayList();

        for(Journalist journalist : this.journalists){
            jsonJournalists.add(journalist.getJSONObject());
        }
        
        json.put("journalists", new JSONArray(jsonJournalists));
        json.put("prizes", new JSONArray(this.prizes));
        
        return json;
    }
}
