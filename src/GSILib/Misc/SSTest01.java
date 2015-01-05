/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra - curso 2014-15
 */

package GSILib.Misc;

import java.io.File;
import java.io.IOException;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 * This is the test class SSTest01.
 * This class creates a bidimensional array of 4 x 6 of integers, and stores it
 * in the first sheet of a spreadsheet file, "test01.ods".
 * @version 1.0
 * @author Iñigo Aguas, Iñaki Garcia y Alvaro Gil.
 */
public class SSTest01 {
    public static void main(String args[]){
        // Creamos la tabla de números que vamos a guardar.
        int[][] intArray = {{7,4,7,5,9,5}, {4,7,6,8,1,6}, {3,1,6,5,8,9}, {9,8,7,2,7,3}};
        
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
            System.err.printf("No se pudo guardar el archivo.\n");
        }
        
        // Información de usuario.
        System.out.println("El archivo test01.ods se ha escrito de manera correcta.");
        
    }
}
