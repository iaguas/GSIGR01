/*
 * Esto es una prueba
 * Each line should be prefixed with  * 
 */
package GSILib.Misc;

import java.io.File;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 *
 * @author Alvaro
 */

public class SSTest01 {
    private int[][] subTabla;
    
    public void main(String[] args) throws IOException{
        this.subTabla = new int[4][6];

        TableModel model = new DefaultTableModel(this.subTabla);  

        // Save the data to an ODS file and open it.
        
        final File file = new File("P2E2.ods");
        SpreadSheet.createEmpty(model).saveAs(file);

        OOUtils.open(file);
    }
}
