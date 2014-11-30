/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BTesting.keyboard;

import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Alvaro
 */
public class ReadFromKeyboard {
    private static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    
    /**
     * Creates a Journalist from data read from keyboard
     * @return a Journalist
     * @throws IOException handles IO errors 
     */
    public static Journalist newJournalist() throws IOException{
        
        // Leemos el id
        
        System.out.print("id: ");
        String id = keyboard.readLine();
        
        // Leemos el name
        
        System.out.print("Introduzca el nombre: ");
        String name = keyboard.readLine();
        
        // Leemos el birthDate
        
        System.out.print("Introduzca la fecha de nacimiento: ");
        String birthDate = keyboard.readLine();
        
        // Leemos los interests
        
        System.out.print("Introduzca los intereses separados por comas: ");
        String interestsString = keyboard.readLine();
        ArrayList<String> interests = new ArrayList();
        interests.addAll(Arrays.asList(interestsString.split("\\s*,\\s*")));
        
        return new Journalist(id,name,birthDate,interests); 
    }
    
    /**
     * Creates a Photographer from data read from keyboard
     * @return a Journalist
     * @throws IOException handles IO errors 
     */
    public static Photographer newPhotographer() throws IOException{
        
        // Leemos el id
        
        System.out.print("id: ");
        String id = keyboard.readLine();
        
        // Leemos el name
        
        System.out.print("Introduzca el nombre: ");
        String name = keyboard.readLine();
        
        // Leemos el birthDate
        
        System.out.print("Introduzca la fecha de nacimiento: ");
        String birthDate = keyboard.readLine();
        
        // Leemos el regularResidence
        
        System.out.print("Introduzca la residencia habitual: ");
        String regularResidence = keyboard.readLine();
        
        // Leemos el birthDate
        
        System.out.print("Introduzca la residencia vacacional: ");
        String holidayResidence = keyboard.readLine();
                
        return new Photographer(id, name, birthDate, regularResidence, holidayResidence);
    }  
    
    public static PrintableNews newPrintableNews(Journalist journalist) throws IOException{
        
        // Leemos el id
        
        System.out.println("tip: la consola del servidor lanzará un id de noticia que existe.");
        System.out.print("id: ");
        int id = Integer.parseInt(keyboard.readLine());
        
        // Leemos la headline
        
        System.out.print("Introduzca la headline: ");
        String headline = keyboard.readLine();
        
        // Leemos el body
        
        System.out.print("Introduzca el body: ");
        String body = keyboard.readLine();
        
        PrintableNews printableNews = new PrintableNews(headline, body, journalist);
        printableNews.setId(id);
        return printableNews;
    }
}
