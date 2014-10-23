/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.Misc;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 *
 * @author Alvaro
 */

public class SSTest01 {
    public static void main(String args[]){
        // Creamos la tabla de números que vamos a guardar.
        int[][] intArray = {{7,4,7,5,19,5}, {4,7,6,18,1,6}, {3,11,6,15,8,9}, {9,8,7,22,7,13}};
        
        // Creamos el objeto fichero con el que manejaremos la hoja de calculo.
        final File file = new File("test01.ods");
        // Creamos la hoja de cálculo
        SpreadSheet mySpreadSheet = SpreadSheet.create(1,100,100);
        // Rescatamos la hoja dentro de la hoja de cálculo
        final Sheet sheet = mySpreadSheet.getSheet(0);
        
        // Bucle para recorrer la tabla de números y guardarla en el archivo.
        for (int i=0; i<intArray.length; i++){
            for (int j=0; j<intArray[0].length; j++){
                // Introducimos el dato correspondiente.
                sheet.setValueAt((Object) intArray[i][j], j, i);
            }
        }
        
        // Guardamos la hoja de cálculo con todos los datos.
        try {
            OOUtils.open(sheet.getSpreadSheet().saveAs(file));
        } 
        catch (IOException ex) {
            Logger.getLogger(SSTest02.class.getName()).log(Level.SEVERE, null, ex); // TODO: Revisar.
        }
        
    }
}
