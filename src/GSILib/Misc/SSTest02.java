/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */

package GSILib.Misc;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Iñaki
 */
public class SSTest02 {
    int[][] tabla = new int[4][6]; // Array bidimensional (matriz)
    
     // Create the data to save.
    
    
    
    
    public static void main(String args[]){
        final Object[][] data = new Object[6][2];
        data[0] = new Object[] { "January", 1 };
        data[1] = new Object[] { "February", 3 };
        data[2] = new Object[] { "March", 8 };
        data[3] = new Object[] { "April", 10 };
        data[4] = new Object[] { "May", 15 };
        data[5] = new Object[] { "June", 18 };

        String[] columns = new String[] { "Month", "Temp" };

        TableModel model = new DefaultTableModel(data, columns);  

        // Save the data to an ODS file and open it.
        final File file = new File("temperature.ods");
        SpreadSheet.createEmpty(model).saveAs(file);

        OOUtils.open(file);   
    }
    
}
