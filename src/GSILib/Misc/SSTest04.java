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
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * This is the test class SSTest04.
 * This exports Document instances from the system to an "documents.ods" file.
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
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
        Teletype tt = new Teletype("title1","body1",jr);
        tt.addPrize("Super TT 14");
        bsystem.insertNews(tt);
        PrintableNews pn = new PrintableNews("title2","body2",jr);
        pn.addPrize("Patata prize 2014");
        bsystem.insertNews(pn);
        WebNews wn = new WebNews("title3","body3",jr,"http://midominio.com/noticias/id");
        wn.addPrize("Master Webnews 2013");
        bsystem.insertNews(wn);
        
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
                sheetTeletypes.setValueAt(d.getAuthor().getId(), 0, numTeletype);
                sheetTeletypes.setValueAt(d.getHeadline(), 1, numTeletype);
                sheetTeletypes.setValueAt(d.getBody(), 2, numTeletype);
                String[] lPrizes;
                if (bsystem.listPrizes(d) != null){
                    lPrizes = bsystem.listPrizes(d);
                    for (int i=0; i<lPrizes.length; i++){
                        sheetTeletypes.setValueAt(lPrizes[i], i+3, numTeletype);
                    }
                }
                numTeletype++;
            }
            
            // Guardo los datos de las PrintableNews.
            if (d.getClass().getName().equals("GSILib.BModel.documents.visualNews.PrintableNews")){
                int colsPN=3;
                pn = (PrintableNews) d;
                sheetPrintableNews.setValueAt(d.getAuthor().getId(), 0, numPrintableNews);
                sheetPrintableNews.setValueAt(d.getHeadline(), 1, numPrintableNews);
                sheetPrintableNews.setValueAt(d.getBody(), 2, numPrintableNews);

                if(pn.getPictures() != null){
                    sheetWebNews.setValueAt(pn.getPictures()[0], 3, numPrintableNews);
                    colsPN++;
                }
                else{colsPN++;}
                if(pn.getReviewers() != null){
                    sheetWebNews.setValueAt(pn.getReviewers()[0].getId(), 4, numPrintableNews);
                    colsPN++;
                }
                else{colsPN++;}
                String[] lPrizes;
                if (bsystem.listPrizes(d) != null){
                    lPrizes = bsystem.listPrizes(d);
                    for (String lPrize : lPrizes) {
                        sheetPrintableNews.setValueAt(lPrize, colsPN, numPrintableNews);
                        colsPN++;
                    }
                }
                numPrintableNews++;
            }
            
            // Guardo los datos de las WebNews.
            if (d.getClass().getName().equals("GSILib.BModel.documents.visualNews.WebNews")){
                int colsWN = 4;
                wn = (WebNews) d;
                sheetWebNews.setValueAt(d.getAuthor().getId(), 0, numWebNews);
                sheetWebNews.setValueAt(d.getHeadline(), 1, numWebNews);
                sheetWebNews.setValueAt(d.getBody(), 2, numWebNews);
                sheetWebNews.setValueAt(wn.getUrl(), 3, numWebNews);
                if(wn.getPictures() != null){
                    sheetWebNews.setValueAt(wn.getPictures()[0], colsWN, numWebNews);
                    colsWN++;
                }                
                else{colsWN++;}
                if(! wn.getKeyWords().isEmpty()){
                    sheetWebNews.setValueAt(wn.getKeyWords().get(0), colsWN, numWebNews);
                    colsWN++;
                }
                else{colsWN++;}
                String[] lPrizes;
                if (bsystem.listPrizes(d) != null){
                    lPrizes = bsystem.listPrizes(d);
                    for (String lPrize : lPrizes) {
                        sheetWebNews.setValueAt(lPrize, colsWN, numWebNews);
                        colsWN++;
                    }
                }
                numWebNews++;
            }
        }
        
        // Guardamos la hoja de cálculo con todos los datos.
        try {
            OOUtils.open(mySpreadSheet.saveAs(file));
        } 
        catch (IOException ex) {
            System.err.printf("No se pudo guardar el archivo.\n");
        }
    }
}
