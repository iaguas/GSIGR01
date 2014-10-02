/* 
 * Práctica 02 - Grupo 01
 * Gestión de Sistemas de Información
 * Universidad Pública de Navarra
 */

package GSI.Misc;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

/**
 *
 * @author inigo.aguas
 */
public class SSTest03 {
    public static void main(String args[]){
        // Load the file.
        File file = new File("test02.ods");
        Sheet sheetTest02 = null;
        try {
            sheetTest02 = SpreadSheet.createFromFile(file).getSheet(0);
        } 
        catch (IOException ex) {
            // TODO: Revisar.
            Logger.getLogger(SSTest03.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //TableModel tm = sheetTest02.getTableModel();
    }
}
