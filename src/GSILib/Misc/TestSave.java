/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.Misc;

import GSILib.BModel.documents.Teletype;
import GSILib.BModel.documents.visualNews.PrintableNews;
import GSILib.BModel.documents.visualNews.WebNews;
import GSILib.BModel.workers.Journalist;
import GSILib.BSystem.BusinessSystem;
import java.io.File;
import java.util.ArrayList;

/**
 * This is the test class TestSave.
 * This class tests the correct save of BusinessSystem in a .ods file.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class TestSave {
    public static void main(String args[]){
     
        // Creamos la instancia de nuestro sistema de información.
        BusinessSystem bsystem = new BusinessSystem();  
        
        // RANDOM DATA TODO: delete
        
        Journalist jr = new Journalist("1111A", "Tom Anderson", "16/05/1995", new ArrayList<>());
        bsystem.addJournalist(jr);
        // Y añadimos noticias de todo tipo para este periodista. 
        Teletype tt = new Teletype("title1","body1",jr);
        tt.addPrize("Super TT 14");
        bsystem.insertNews(tt);
        PrintableNews pn = new PrintableNews("title2","body2",jr);
        pn.addPrize("Patata prize 2014");
        bsystem.insertNews(pn);
        WebNews wn = new WebNews("title3","body3",jr,"http://midominio.com/noticias/id");
        wn.addPrize("Master Webnews 2013");
        bsystem.insertNews(wn);
        
        // RANDOM DATA END
        
        // Creamos el objeto fichero con el que manejaremos la hoja de calculo.
        final File file = new File("businessSystem.ods");
        
        // Importamos los teletipos.
        bsystem.saveToFile(file);
        
    }
}

