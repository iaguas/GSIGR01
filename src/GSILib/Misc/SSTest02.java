/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */

package GSILib.Misc;
import static java.awt.Color.*;
import java.io.File;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.*;
import org.jopendocument.dom.spreadsheet.TableStyle.*;
//import org.jopendocument.dom.spreadsheet.SpreadSheet;
//import org.jopendocument.dom.   StyleProperties;

/**
 *
 * @author Iñaki
 */
public class SSTest02 {
     // Create the data to save.
    public static void main(String args[]){
        int[][] tabla = new int[4][6]; // Array bidimensional (matriz)
        int[][] tabla_test01 = new int[4][6];
        /*
        // Create the data to save.
        final Object[][] data = new Object[6][2];
        data[0] = new Object[] { "January", 1 };
        data[1] = new Object[] { "February", 3 };
        data[2] = new Object[] { "March", 8 };
        data[3] = new Object[] { "April", 10 };
        data[4] = new Object[] { "May", 15 };
        data[5] = new Object[] { "June", 18 };*/
        
        // Vaciar las primeras 3 filas y sus correspondientes 5 columnas
        // La tabla se invierte horizontal y verticalmente
        // De esta manera, el elemento [1,1] se encontrará en la [4,6]
        // Dependiendo del número, las celdas se colorearán de una forma u otra
        for(int i=0;i<4;i++){
            for(int j=0;i<6;j++){
                tabla[i][j]=tabla_test01[j][i];
            }
        }

        String[] columns = new String[] { "Month", "Temp" };

        TableModel model = new DefaultTableModel(data, columns);
        model.setBackgroundColor(cyan);

        // Save the data to an ODS file and open it.
        final File file = new File("test02.ods");   
}