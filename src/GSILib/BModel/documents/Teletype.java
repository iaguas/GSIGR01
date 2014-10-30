/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.BModel.documents;

import GSILib.BModel.Document;
import GSILib.BModel.workers.Journalist;
import GSILib.Serializable.XMLRepresentable;
import java.io.File;

/**
 * This is the class Teletype.
 * This is a document type in which you can't find pictures or any other extra 
 * material. They are only formed by a headline, a body and an author.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Teletype extends Document implements XMLRepresentable{
    
    /**
     * Class constructor that makes an object with headline, body and author.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     */
    public Teletype(String headline, String body, Journalist journalist){
        // Llamamos al constructor de la clase padre.
        super(headline, body, journalist);
    }
    
    @Override
    public String toString(){
        // Devolvemos un string con los datos del teletipo.
        return "Teletype ID: " + this.getId() + "\n  Headline: " + this.getHeadline()
                + "\n  Body: " + this.getBody() + "\n  Journalist: " +
                this.getAuthor();
    }
    
    /** 
     * Equals. Known if 2 object are the same.
     * @param tp a teletype
     * @return true if they are the same object, false otherwise.
     */
    public boolean equals(Teletype tp){
        // Comparamos y devolvemos si son iguales o no.
        return this.getId().equals(tp.getId());
    }

    @Override
    public String toXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveToXML(File f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveToXML(String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
