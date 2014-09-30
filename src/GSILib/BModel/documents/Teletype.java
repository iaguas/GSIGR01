/* 
 * Práctica 01 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.BModel.documents;

import GSILib.BModel.Document;
import GSILib.BModel.workers.Journalist;

/**
 * This is the class Teletype.
 * This is a document type in which you can't find pictures or any other extra 
 * material. They are only formed by a headline, a body and an author.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class Teletype extends Document{
    
    /**
     * Class constructor that makes an object with headline, body and author.
     * @param headline headline of the notice that you want to save.
     * @param body all text of the notice.
     * @param journalist worker who has written the notice.
     */
    public Teletype(String headline, String body, Journalist journalist){
        super(headline, body, journalist);
    }
    
    @Override
    public String toString(){
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
        return this.getId().equals(tp.getId());
    }
}
