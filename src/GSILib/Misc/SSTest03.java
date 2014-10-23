/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.Misc;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
/**
 *
 * @author inigo.aguas
 */
public class SSTest03 {
    public static void main(String args[]){
        // Creamos objeto archivo con nuestra hoja de cálculo.
        File file = new File("test02.ods");
        // Leemos y almacenamos los datos que hay en la hoja.
        Sheet sheetTest02 = null;
        try {
            sheetTest02 = SpreadSheet.createFromFile(file).getSheet(0);
        } 
        catch (IOException ex) {
            // TODO: Revisar.
            Logger.getLogger(SSTest03.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Preparamos el patrón del que leemos 
        String[] leters = {"F", "G", "H", "I", "J", "K"};
        String[] numbers = {"4", "5", "6", "7"};
        
        // Escribimos los datos recuperados en pantalla.
        System.out.println("Los datos que están en la hoja ");
        for(String n:numbers){
            for(String l:leters){
                System.out.print(sheetTest02.getCellAt(l+n).getValue()+ "\t");
            }
            System.out.println();
        }
    }
}