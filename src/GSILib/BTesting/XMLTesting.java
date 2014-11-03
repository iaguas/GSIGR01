/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BTesting;

import GSILib.BModel.Picture;
import GSILib.BModel.documents.Teletype;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.documents.visualNews.WebNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import java.util.ArrayList;

/**
 *
 * @author Alvaro
 */
public class XMLTesting {
    
    public static void main(String args[]) {
        
        // Nuevo Joournalist
        
        ArrayList interestsOfAlvaro = new ArrayList();
        
        interestsOfAlvaro.add("Discutir");
        interestsOfAlvaro.add("Tocar las narices");
        interestsOfAlvaro.add("Jugar al CS");

        Journalist journalistAlvaro = new Journalist("8", "Alvaro Octal", "27/12/1993", interestsOfAlvaro);
        
        // Nuevo Journalist
        
        ArrayList interestsOfMEV = new ArrayList();
        
        interestsOfMEV.add("Comer pasta");
        interestsOfMEV.add("beber cerveza");
        
        Journalist journalistMEV = new Journalist("5", "MEV", "01/01/01", interestsOfMEV);
        
        System.out.println(journalistAlvaro.saveToXML("journalist.xml"));
        
        // Nuevo Photographer
        
        Photographer photographer = new Photographer("12", "Arguitxu Arzcarrena", "01/01/1990", "Bilbao", "Carpa");
        
        System.out.println(photographer.saveToXML("photographer.xml"));
        
        // Nuevo Teletype
        
        Teletype teletype = new Teletype("Anonymous hackea la web de AEDE", "Se conoce el grupo de burdos patanes fueron owneados", journalistAlvaro);
        
        System.out.println(teletype.saveToXML("teletype.xml"));
        
        // Nueva Picture
        
        Picture pictureRed = new Picture("http://images.example.com/testRed.png", photographer);
        System.out.println(pictureRed.saveToXML("picture.xml"));
        
        Picture pictureBlue = new Picture("http://images.example.com/testBlue.png", photographer);
        
        // Nueva PrintableNews
        
        PrintableNews printableNews = new PrintableNews("255Tbps: World’s fastest network could carry all of the internet’s traffic on a single fiber", "A joint group of researchers from the Netherlands and the US have smashed the world speed record for a fiber network, pushing 255 terabits per second down a single strand of glass fiber. This is equivalent to around 32 terabytes per second — enough to transfer a 1GB movie in 31.25 microseconds (0.03 milliseconds), or alternatively, the entire contents of your 1TB hard drive in about 31 milliseconds.", journalistAlvaro);
        printableNews.addReviewer(journalistMEV);
        printableNews.addPicture(pictureRed);
        printableNews.addPicture(pictureBlue);
        
        System.out.println(printableNews.saveToXML("printableNews.xml"));
        
        // Nueva WebNews
        
        WebNews webNews = new WebNews("255Tbps: World’s fastest network could carry all of the internet’s traffic on a single fiber", "A joint group of researchers from the Netherlands and the US have smashed the world speed record for a fiber network, pushing 255 terabits per second down a single strand of glass fiber. This is equivalent to around 32 terabytes per second — enough to transfer a 1GB movie in 31.25 microseconds (0.03 milliseconds), or alternatively, the entire contents of your 1TB hard drive in about 31 milliseconds.", journalistAlvaro, "http://example.com/1/");
        webNews.addKeyWord("fibra");
        webNews.addKeyWord("internet");
        webNews.addPicture(pictureRed);
        webNews.addPicture(pictureBlue);
        
        System.out.println(webNews.saveToXML("webNews.xml"));
        
    }
}
