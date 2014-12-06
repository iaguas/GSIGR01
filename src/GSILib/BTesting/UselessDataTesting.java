/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BTesting;

import GSILib.BModel.Picture;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.BSystem.BusinessSystem;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Alvaro
 */
public class UselessDataTesting {
    
    public static BusinessSystem getTestingBusinessSystem(){
        
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

        Journalist journalistPirata = new Journalist("2", "Pirata", "01/01/01", interestsOfPirata);

        Photographer photographer = new Photographer("12", "Arguitxu Arzcarrena", "01/01/1990", "Bilbao", "Carpa");

        // Nueva Picture

        Picture pictureRed = new Picture("http://images.example.com/testRed.png", photographer);
        Picture pictureBlue = new Picture("http://images.example.com/testBlue.png", photographer);

        // Nueva PrintableNews

        PrintableNews printableNews = new PrintableNews("255Tbps: World’s fastest network could carry all of the internet’s traffic on a single fiber", "A joint group of researchers from the Netherlands and the US have smashed the world speed record for a fiber network, pushing 255 terabits per second down a single strand of glass fiber. This is equivalent to around 32 terabytes per second — enough to transfer a 1GB movie in 31.25 microseconds (0.03 milliseconds), or alternatively, the entire contents of your 1TB hard drive in about 31 milliseconds.", journalistAlvaro);
        printableNews.addReviewer(journalistPirata);
        printableNews.addPicture(pictureRed);
        printableNews.addPicture(pictureBlue);

        // Nuevo BusinessSystem

        BusinessSystem bs = new BusinessSystem();
        Date currentDate = new Date();
        bs.createNewspaper(currentDate);
        bs.addNewsToIssue(bs.getNewspaper(currentDate), printableNews);
        
        return bs;
    }
}
