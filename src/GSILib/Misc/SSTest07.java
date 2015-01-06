/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.Misc;

import GSILib.BModel.Newspaper;
import GSILib.BModel.Picture;
import GSILib.BModel.documents.Teletype;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.documents.visualNews.WebNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BModel.workers.Photographer;
import GSILib.BSystem.BusinessSystem;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * This is the test class SSTest07.
 * This class tests the correct save of BusinessSystem in a .ods file.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class SSTest07 {
    public static void main(String args[]){
     
        // Creamos la instancia de nuestro sistema de información.
        BusinessSystem bsystem = new BusinessSystem();  
        
        // Añadimos periodistas
        ArrayList<String> interest = new ArrayList<>();
        interest.add("swimming");
        interest.add("singing");
        Journalist jr = new Journalist("1111A", "Tom Anderson", "16/05/1995", interest);
        bsystem.addJournalist(jr);
        bsystem.addJournalist(new Journalist("5555R","Ken Jones","16/05/1953",new ArrayList<>()));
        bsystem.addJournalist(new Journalist("6666S","Tim Cook","23/04/1956",new ArrayList<>()));
        bsystem.addJournalist(new Journalist("7777T","Bill McDonald","08/07/1978",new ArrayList<>()));
        
        // Añadimos un fotógrafo y sus fotografías.
        Photographer pg = new Photographer("1234Z","Jonh Smith", "30/04/1953","C/ Falsa 123", "C/ Verdadera 567");
        bsystem.addPhotographer(pg);
        bsystem.addPhotographer(new Photographer("3333C","Tom Ulman","24/03/1984","C/Falsa 123","C/Verdadera 456"));
        Picture pic1 = new Picture("http://midominio.com/photos/pic1", pg);
        Picture pic2 = new Picture("http://midominio.com/photos/pic2", pg);
        Picture pic3 = new Picture("http://midominio.com/photos/pic3", pg);
        
        // Añadimos las fotos al sistema.
        bsystem.addPicture(pic1);
        bsystem.addPicture(pic2);
        bsystem.addPicture(pic3);
        
        // Y añadimos noticias de todo tipo para el primer periodista. 
        Teletype tt = new Teletype("title1","body1",jr);
        tt.addPrize("Super TT 14");
        bsystem.insertNews(tt);
        PrintableNews pn = new PrintableNews("title2","body2",jr);
        pn.addPrize("Patata prize 2014");
        pn.addPicture(pic1);
        pn.addPicture(pic2);
        bsystem.insertNews(pn);
        bsystem.addReviewer(pn, bsystem.findJournalist("7777T"));
        WebNews wn = new WebNews("title3","body3",jr,"http://midominio.com/noticias/id");
        wn.addPrize("Master Webnews 2013");
        wn.addPicture(pic3);
        wn.addKeyWord("keyword1");
        wn.addKeyWord("keyword2");
        wn.addKeyWord("keyword3");
        bsystem.insertNews(wn);
        
        // Por último, montamos un periodico.
        bsystem.createNewspaper(new Date(2014, 10, 28));
        Newspaper np = bsystem.getNewspaper(new Date(2014, 10, 28));
        np.addNews(pn);
        
        // Creamos el objeto fichero con el que manejaremos la hoja de calculo.
        final File file = new File("businessSystem.ods");
        
        // Importamos los teletipos.
        bsystem.saveToFile(file);
        
        // Devolvemos que todo ha ido bien.
        System.out.println("Todos los datos han sido guardados de forma adecuada en el fichero.");
    }
}

