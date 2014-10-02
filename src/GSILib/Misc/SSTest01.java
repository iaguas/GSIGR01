/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSILib.Misc;

import java.io.File;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 *
 * @author Alvaro
 */

public class SSTest01 {
    public static void main(String[] args) throws IOException{
        
        final Object[][] data = new Object[4][6];
        data[0] = new Object[] { 0, 1, 2, 3, 4, 5 };
        data[1] = new Object[] { 6, 7, 8, 9, 10, 11 };
        data[2] = new Object[] { 12, 13, 14, 15, 16, 17 };
        data[3] = new Object[] { 18, 19, 20, 21, 22, 23 };

        Object columnNames[] = { null, null, null, null, null, null };
        
        TableModel model = new DefaultTableModel(data, columnNames); 
        
        // Save the file
        
        final File file = new File("test01.ods");
        SpreadSheet.createEmpty(model).saveAs(file);

        OOUtils.open(file);
    }
}
