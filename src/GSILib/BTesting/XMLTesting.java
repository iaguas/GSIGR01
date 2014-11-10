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
import GSILib.BSystem.BusinessSystem;
import java.util.ArrayList;
import java.util.Date;
import org.xml.sax.SAXException;

/**
 *
 * @author Alvaro
 */
public class XMLTesting {
    
    public static void main(String args[]) throws SAXException {
        
        System.out.println("---------------");
        System.out.println("***  Store  ***");
        System.out.println("---------------");
        
        System.out.println("\n+ BSystem");
        
        // Nuevo Joournalist
        
        ArrayList interestsOfAlvaro = new ArrayList();
        
        interestsOfAlvaro.add("Discutir");
        interestsOfAlvaro.add("Tocar las narices");
        interestsOfAlvaro.add("Jugar al CS");

        Journalist journalistAlvaro = new Journalist("8", "Alvaro Octal", "27/12/1993", interestsOfAlvaro);
        
        // Nuevo Journalist
        
        ArrayList interestsOfPirata = new ArrayList();
        
        interestsOfPirata.add("Comer pasta");
        interestsOfPirata.add("beber cerveza");
        interestsOfPirata.add("Cantar canciones");
        interestsOfPirata.add("trifulcas de bar");
        
        Journalist journalistPirata= new Journalist("2", "Pirata", "01/01/01", interestsOfPirata);
        
        if (journalistAlvaro.saveToXML("xml/journalist.xml"))
            System.out.println("|- [done] journalist.xml");
        
        // Nuevo Photographer
        
        Photographer photographer = new Photographer("12", "Arguitxu Arzcarrena", "01/01/1990", "Bilbao", "Carpa");
        
        if (photographer.saveToXML("xml/photographer.xml"))
            System.out.println("|- [done] photographer.xml");
        
        // Nuevo Teletype
        
        Teletype teletype = new Teletype("Anonymous hackea la web de AEDE", "Se conoce el grupo de burdos patanes fueron owneados", journalistAlvaro);
        
        if (teletype.saveToXML("xml/teletype.xml"))
            System.out.println("|- [done] teletype-xml");
        
        // Nueva Picture
        
        Picture pictureRed = new Picture("http://images.example.com/testRed.png", photographer);
        if (pictureRed.saveToXML("xml/picture.xml"))
            System.out.println("|- [done] picture.xml");
        
        Picture pictureBlue = new Picture("http://images.example.com/testBlue.png", photographer);
        
        // Nueva PrintableNews
        
        PrintableNews printableNews = new PrintableNews("255Tbps: World’s fastest network could carry all of the internet’s traffic on a single fiber", "A joint group of researchers from the Netherlands and the US have smashed the world speed record for a fiber network, pushing 255 terabits per second down a single strand of glass fiber. This is equivalent to around 32 terabytes per second — enough to transfer a 1GB movie in 31.25 microseconds (0.03 milliseconds), or alternatively, the entire contents of your 1TB hard drive in about 31 milliseconds.", journalistAlvaro);
        printableNews.addReviewer(journalistPirata);
        printableNews.addPicture(pictureRed);
        printableNews.addPicture(pictureBlue);
        
        if (printableNews.saveToXML("xml/printableNews.xml"))
            System.out.println("|- [done] printableNews.xml");
        
        // Nueva WebNews
        
        WebNews webNews = new WebNews("255Tbps: World’s fastest network could carry all of the internet’s traffic on a single fiber", "A joint group of researchers from the Netherlands and the US have smashed the world speed record for a fiber network, pushing 255 terabits per second down a single strand of glass fiber. This is equivalent to around 32 terabytes per second — enough to transfer a 1GB movie in 31.25 microseconds (0.03 milliseconds), or alternatively, the entire contents of your 1TB hard drive in about 31 milliseconds.", journalistAlvaro, "http://example.com/1/");
        webNews.addKeyWord("fibra");
        webNews.addKeyWord("internet");
        webNews.addPicture(pictureRed);
        webNews.addPicture(pictureBlue);
        
        if (webNews.saveToXML("xml/webNews.xml"))
            System.out.println("|- [done] webNews.xml");
        
        // Nuevo BusinessSystem
        
        BusinessSystem bs = new BusinessSystem();
        
        bs.addJournalist(journalistAlvaro);
        bs.addJournalist(journalistPirata);
        
        bs.addPhotographer(photographer);
        
        bs.addPicture(pictureRed);
        bs.addPicture(pictureBlue);
        
        bs.insertNews(printableNews);
        bs.insertNews(teletype);
        bs.insertNews(webNews);
        
        Date date = new Date(2014,11,4);
        bs.createNewspaper(date);
        bs.addNewsToIssue(bs.getNewspaper(date), printableNews);
        
        if (bs.saveToXML("xml/businessSystem.xml"))
            System.out.println("|- [done] businessSystem.xml\n");
        
        // Lectura
        
        System.out.println("---------------");
        System.out.println("*** Lectura ***");
        System.out.println("---------------");
        
        // Leer Periodista
        
        System.out.println("\nJournalist:\n");
        
        System.out.print(new Journalist(journalistAlvaro.toXML()));
        
        System.out.println("\nPhotographer:\n");
        
        System.out.print(new Photographer(photographer.toXML()));
        
    }
}
