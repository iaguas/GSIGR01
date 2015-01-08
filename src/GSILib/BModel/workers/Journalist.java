/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel.workers;

import GSILib.BModel.Worker;
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
import java.util.Base64;
import java.util.List;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * This is the class Journalist.
 * It represents a worker who writes Document into the system. It has ID, name,
 * birthDate and, especialy, interest.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Journalist extends Worker implements XMLRepresentable, Serializable {
    
    // Atributo de la clase
    private List<String> interests = new ArrayList<>(); // Lista de intereses.
    
    /**
     * Class constructor
     * @param id This is an unique ID of the worker.
     * @param name The name of the worker.
     * @param birthDate The birth dathe of the worker.
     * @param interests Is a list of a keyword of interest of a Journalist.
     */
    public Journalist(String id, String name, String birthDate, ArrayList interests){
        // Utilizamos el constructor de la superclase
        super(id, name, birthDate);
        // Introducimos los intereses en esta clase, que es quien los contiene.
        this.interests = interests;
    }
    
    /**
     * Class constructor
     * @param journalistFromXML This is a xml String which represents a Jorunalist
     * @throws org.xml.sax.SAXException exception derived from data consistence.
     */
    public Journalist(String journalistFromXML) throws SAXException{
        
        // Creamos un worker nulo
        
        super();
        
        // Instanciamos el motor de XML
        
        XMLHandler xml = new XMLHandler(journalistFromXML);
        
        Element xmlJournalist = (Element) xml.engine.getElementsByTagName("Journalist").item(0);
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlJournalist);
    }
        
    /**
     * Constructor which obtains the attribute values from Element type object
     * extracted from and XML file containing Journalist; inherits Worker attributes.
     * @param xmlJournalist Element type object which contains useful data
     */
    public Journalist(Element xmlJournalist){
        
        // Creamos un worker nulo
        
        super();
        
        // Cargamos los valores del Elemento
        
        this.loadFromElement(xmlJournalist);
         
    }
    
    /**
     * Lowest rank method which obtains the atribute values for Journalist from an
     * XML Element. 
     * @param xmlJournalist Element type which contains useful data (interest(s))
     */
    @Override
    protected void loadFromElement(Element xmlJournalist){
        
        // Worker rellena sus datos
        
        super.loadFromElement(xmlJournalist);
        
        // Journalist rellena sus datos
        
        NodeList interestsNodes = (NodeList) xmlJournalist.getElementsByTagName("interest");

        for (int i = 0; i < interestsNodes.getLength(); i++) {
            Node interestNode = interestsNodes.item(i);
            if (interestNode.getNodeType() == Node.ELEMENT_NODE) {
                this.interests.add(interestNode.getTextContent());
            }
        }
    }
    
    /**
     * Gets the interest of a journalist
     * @return the array of interests
     */
    public String[] getInterests(){
        if(this.interests.isEmpty())
            return null;
        return this.interests.toArray(new String[this.interests.size()]);
    }
    
    
    
    /**
     * Sets the interests for a Journalist
     * @param interests List of interests to asociate with a Journalist
     */
    public void setInterests(ArrayList<String> interests){
        // Borro lo que había en la lista si contenia algo.
        if(! this.interests.isEmpty())
            this.interests.clear();
        
        // Pueblo de nuevo la lista con los argumentos pasados.
        this.interests.addAll(interests);
    }
    
    /**
     * Helper method which creates a XML element "Journalist"
     * @param xml XML Handler for system.
     * @return XML element snippet representing a journalist
     */
    public Element getElement(XMLHandler xml){

        Element xmlJournalist = xml.engine.createElement("Journalist");
        
        // Para una raiz Journalist, introducimos su id como atributo
        
        xmlJournalist.setAttribute("id", this.getId());

        // Para una raiz Journalist, introducimos su name como atributo
        
        xmlJournalist.setAttribute("name", this.getName());

        // Para una raiz Journalist, introducimos su birthdate como atributo
        
        xmlJournalist.setAttribute("birthDate", this.getBirthDate());
        
        // Para una raiz Journalist, introducimos n raices interest
        
        String[] interests = this.getInterests();
        if (interests != null){
            for(String interest : interests){

                // Para una raiz Interests, introducimos otra raiz Interest

                Element xmlJournalistInterest = xml.engine.createElement("interest");
                Text journalistInterest = xml.engine.createTextNode(interest);
                xmlJournalistInterest.appendChild(journalistInterest);      
                xmlJournalist.appendChild(xmlJournalistInterest);
            }
        }
        
        return xmlJournalist;
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
     * Copies all the fields from a Journalist to an associated new one.
     * @param newJournalist original Journalist the fields are read from
     */
    public void copyValuesFrom(Journalist newJournalist){
        super.copyValuesFrom(newJournalist);
        if (newJournalist.getInterests() != null)
            this.interests.addAll(Arrays.asList(newJournalist.getInterests()));
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param jr a journalist
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Journalist jr){
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(jr.getId());
    }
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos del periodista.
        return "+ Journalist ID: " + this.getId() + "\n"
                + "|- Name: " + this.getName() + "\n"
                + "|- BirthDate: " + this.getBirthDate() + "\n"
                + "|- Interests: " + this.interests + "\n";
    }
    
    /**
     * Creates an HTML template with the representation of a Journalist
     * @return html fragment with the values of a Journalist
     */
    public String getHTMLBody(){
        
        String html = " <div class=\"row\">" +
                      "     <div class=\"media\">\n" +
                      "         <a class=\"pull-left\" href=\"#\">\n" +
                      "             <img class=\"media-object dp img-circle\" src=\"http://placehold.it/100x100\" style=\"width: 100px;height:100px;\">\n" +
                      "         </a>\n" +
                      "         <div class=\"media-body\">\n" +
                      "             <h3 class=\"media-heading\">" + this.getName() + " - <small>" + this.getBirthDate() + "</small></h3>\n" +
                      "             <h5>" + 
                      "                 <a href=\"./?format=xml\" class=\"btn btn-primary btn-xs\" role=\"button\">XML</a>" +
                      "                 <a href=\"./?format=json\" class=\"btn btn-primary btn-xs\" role=\"button\">JSON</a>" +
                      "                 <a class=\"btn btn-primary btn-xs\" download=\"" + super.getName() + ".vcf\"\n" +
                      "                     href=\"data:text/plain;base64," + new String(Base64.getEncoder().encode(this.getvCard() .getBytes())) + "\">vCard\n" +
                      "                 </a>" +
                      "             </h5>\n" +
                      "             <hr style=\"margin:8px auto\">\n";
        
        for (String interest : this.interests){
            html = html.concat("<span class=\"label label-default\">" + interest + "</span>\n");
        }
            
        return html.concat("</div></div></div><br>");
    }
    
    @Override
    public JSONObject getJSONObject() throws JSONException{
        
        JSONObject json = super.getJSONObject();

        json.put("interests", new JSONArray(this.interests));
        
        return json;
    }
    
    /**
     * Returns a contact card of a Journalist
     * @return vCard Journalist vCard
     */
    public String getvCard(){
        String vCard = "begin:VCARD\n" +
                       "version:3.0\n" +
                       "fn:" + super.getName() + "\n" +
                       "bday:" + super.getBirthDate() + "\n" +
                       "end:vcard\n\n";
        
        return vCard;
    }
}
