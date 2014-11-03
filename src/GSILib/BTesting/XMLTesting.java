/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BTesting;

import GSILib.BModel.documents.Teletype;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import java.util.ArrayList;

/**
 *
 * @author Alvaro
 */
public class XMLTesting {
    
    public static void main(String args[]) {
        
        ArrayList interests = new ArrayList();
        
        interests.add("Discutir");
        interests.add("Tocar las narices");
        interests.add("Jugar al CS");

        Journalist journalist = new Journalist("8", "Alvaro Octal", "27/12/1993", interests);
        
        System.out.println(journalist.saveToXML("journalist.xml"));
        
        Photographer photographer = new Photographer("12", "Arguitxu Arzcarrena", "01/01/1990", "Bilbao", "Carpa");
        
        System.out.println(photographer.saveToXML("photographer.xml"));
        
        Teletype teletype = new Teletype("Anonymous hackea la web de AEDE", "Se conoce el grupo de burdos patanes fueron owneados", journalist);
        
        System.out.println(teletype.saveToXML("teletype.xml"));
    }
}
