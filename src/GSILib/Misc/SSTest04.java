/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */
 
package GSILib.Misc;

import GSILib.BModel.Document;
import GSILib.BModel.documents.Teletype;
import GSILib.BModel.documents.visualNews.*;
import GSILib.BModel.workers.Journalist;
import GSILib.BSystem.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;


/**
 *
 * @author inigo
 */


public class SSTest04 {
    public static void main(String args[]){
        // Variables para el control de bucle de escritura de datos.
        int numTeletype = 0;
        int numPrintableNews = 0;
        int numWebNews = 0;
        
        // Creamos la instancia de nuestro sistema de información.
        BusinessSystem bsystem = new BusinessSystem();
        // Dentro creamos el periodista al que le asignaremos las noticias.
        Journalist jr = new Journalist("1111A", "Tom Anderson", "16/05/1995", new ArrayList<>());
        bsystem.addJournalist(jr);
        // Y añadimos noticias de todo tipo para este periodista.       
        bsystem.insertNews(new Teletype("title1","body1",jr));
        bsystem.insertNews(new PrintableNews("title2","body2",jr));
        bsystem.insertNews(new WebNews("title3","body3",jr,"http://midominio.com/noticias/id"));
        //...
        
        // Creamos una nueva hoja de cálculo.
        // Creamos el objeto fichero con el que manejaremos la hoja de calculo.
        final File file = new File("documents.ods");
        // Creamos la hoja de cálculo
        SpreadSheet mySpreadSheet = SpreadSheet.create(3,1000,1000);
        // Rescatamos la hoja dentro de la hoja de cálculo
        final Sheet sheetTeletypes = mySpreadSheet.getSheet(0);
        sheetTeletypes.setName("Teletypes");
        final Sheet sheetPrintableNews = mySpreadSheet.getSheet(1);
        sheetPrintableNews.setName("PrintableNews");
        final Sheet sheetWebNews = mySpreadSheet.getSheet(2);
        sheetWebNews.setName("WebNews");
        
        // Recuperamos todas las noticias de nuestro periodista.
        Document[] documentList = bsystem.getDocuments(jr);
        // Y vamos analizándo y archivando en la hoja de cálculo según corresponda.
        for(Document d: documentList){
            // Guardo los datos de los teletipos.
            if (d.getClass().getName().equals("GSILib.BModel.documents.Teletype")){
                    sheetTeletypes.setValueAt(d.getId(), 0, numTeletype);
                    sheetTeletypes.setValueAt(d.getHeadline(), 1, numTeletype);
                    sheetTeletypes.setValueAt(d.getBody(), 2, numTeletype);
                    numTeletype++;
            }
            
            // Guardo los datos de las PrintableNews.
            if (d.getClass().getName().equals("GSILib.BModel.documents.visualNews.PrintableNews")){
                    PrintableNews pn = (PrintableNews) d;
                    sheetPrintableNews.setValueAt(d.getId(), 0, numPrintableNews);
                    sheetPrintableNews.setValueAt(d.getHeadline(), 1, numPrintableNews);
                    sheetPrintableNews.setValueAt(d.getBody(), 2, numPrintableNews);
                    if(!pn.getPictures().isEmpty()){
                        sheetWebNews.setValueAt(pn.getPictures().get(0), 4, numPrintableNews);
                    }
                    if(pn.getReviewers().length!=0){
                        sheetWebNews.setValueAt(pn.getReviewers()[0].getId(), 4, numPrintableNews);
                    }
                    numPrintableNews++;
            }
            
            // Guardo los datos de las WebNews.
            if (d.getClass().getName().equals("GSILib.BModel.documents.visualNews.WebNews")){
                    WebNews wn = (WebNews) d;
                    sheetWebNews.setValueAt(d.getAuthor().getId(), 0, numWebNews);
                    sheetWebNews.setValueAt(d.getHeadline(), 1, numWebNews);
                    sheetWebNews.setValueAt(d.getBody(), 2, numWebNews);
                    sheetWebNews.setValueAt(wn.getUrl(), 3, numWebNews);
                    if(!wn.getPictures().isEmpty()){
                        sheetWebNews.setValueAt(wn.getPictures().get(0), 4, numWebNews);
                    }
                    if(!wn.getKeyWords().isEmpty()){
                        sheetWebNews.setValueAt(wn.getKeyWords().get(0), 5, numWebNews);
                    }
                    numWebNews++;
            }
        }
        
        // Guardamos la hoja de cálculo con todos los datos.
        try {
            OOUtils.open(sheetTeletypes.getSpreadSheet().saveAs(file));
        } 
        catch (IOException ex) {
            Logger.getLogger(SSTest04.class.getName()).log(Level.SEVERE, null, ex); // TODO: Revisar.
        }
    }
}
