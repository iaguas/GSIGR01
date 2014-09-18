/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.BModel.documents.visualNews;

import GSILib.BModel.documents.VisualNews;
import GSILib.BModel.workers.Journalist;

/**
 *
 * @author Alvaro
 */
public class PrintableNews extends VisualNews{

    public PrintableNews(String headline, String body, Journalist journalist) {
        super(headline, body, journalist);
    }
}
