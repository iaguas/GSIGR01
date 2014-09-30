/**
 * This is the class Teletype.
 */
package GSILib.BModel.documents;

import GSILib.BModel.Document;
import GSILib.BModel.workers.Journalist;

/**
 *
 * @author Alvaro Gil & Iñigo Aguas & Iñaki Garcia
 */
public class Teletype extends Document{
    
    /**
     * Class constructor
     * @param headline
     * @param body
     * @param journalist
     */
    public Teletype(String headline, String body, Journalist journalist){
        super(headline, body, journalist);
    }
    
    // Overdrive methods equals and toString.
    @Override
    public String toString(){
        return "Teletype ID: " + this.getId() + "\n  Headline: " + this.getHeadline()
                + "\n  Body: " + this.getBody() + "\n  Journalist: " +
                this.getAuthor();
    }
    
      /** Equals
      * @param tp a teletype
      * @return true if they are the same object.
      */
    public boolean equals(Teletype tp){
        return this.getId().equals(tp.getId());
    }
}
